package test.Spark;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;

import utils.Path;

public class Sql {
	private static Logger logger = LogManager.getLogger(Sql.class);
	private final static String SYSPATH = System.getProperty("user.dir");
	private final static String BASEPATH = Path.Combile(SYSPATH, "spark");
	private final static String DATAPATH = Path.Combile(SYSPATH, "spark", "data");
	private final static String JSONPATH = Path.Combile(SYSPATH, "spark", "json");
	private final static String CSVPATH = Path.Combile(SYSPATH, "spark", "csv");
	private final static String CONNMYSQL = "jdbc:mysql://192.168.56.102:3306/testDB?user=root&password=123456&useSSL=false";
	private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private SparkSession spark = null;
	private SQLContext sqlContext = null;
	private static Sql sql = null;
	static {
		System.setProperty("hadoop.home.dir", BASEPATH);   // 默认保存到spark-warehouse目录
	}

	public static Sql getInstance() {
		if(sql ==null) {
			sql = new Sql();
		    sql.spark = SparkSession.builder().appName("Java Spark SQL data sources example")
					.config("spark.some.config.option", "some-value").config("spark.sql.warehouse.dir", DATAPATH) // 配置文件保存位置
					.master("local").getOrCreate();
		    sql.spark.sparkContext().setLogLevel("WARN"); // 配置日志显示级别 ALL, DEBUG, ERROR, FATAL, INFO, OFF, TRACE, WARN
		    sql.sqlContext = sql.spark.sqlContext();
		    sql.register("grpConcat", new GroupConcat());//注册自定义函数
		}
		return sql;
	}
	private DataFrameReader getMysqlReader(String tableName) {
		return sqlContext.read().format("jdbc").option("driver", DRIVER).option("url", CONNMYSQL).option("dbtable",
				tableName);
	}
	
	public Sql register(String functionName,UserDefinedAggregateFunction function) {
		spark.udf().register(functionName, function);
		return this;
	}

	public Dataset<Row> execTable(String tableName) {
		Dataset<Row> df = getMysqlReader(tableName).load();
		df.printSchema();
		return df;
	}

	private void save(Dataset<?> ds, String name, UDataType dataType) {
		switch (dataType) {
		case RAW:
			ds.write().saveAsTable(name);
			break;
		case JSON:
			ds.write().mode(SaveMode.Overwrite).json(Path.Combile(JSONPATH, name));
			break;
		case CSV:
			ds.write().mode(SaveMode.Overwrite).format("csv").save(Path.Combile(CSVPATH,name));
			break;
		default:
			ds.write().saveAsTable(name);
			break;
		}
	}

	//synchronize data from mysql to fileSystem
	public void syncTable2File(String tableName,String fileName,UDataType dataType,ISetMap mapper) {
		Dataset<Row> df = getMysqlReader(tableName).load();
		df.printSchema();
	    if(mapper!=null)
	    	df = mapper.map(df);
		this.save(df, fileName, dataType);
	}
	
	// adapter to sql-programming
	public Dataset<Row> execQuery(Dataset<?> df, String viewName, String sqlText) {
		df.createOrReplaceTempView(viewName);
		return spark.sql(sqlText);
	}

	private Dataset<Row> simpleRead(String fileName, UDataType dataType) {
		Dataset<Row> df = null;
		switch (dataType) {
		case RAW:
			df = sqlContext.read().load(Path.Combile(DATAPATH, fileName));
			break;
		case JSON:
			df = sqlContext.read().json(Path.Combile(JSONPATH, fileName));
			break;
		case CSV:
			df = sqlContext.read().csv(Path.Combile(CSVPATH, fileName));
			break;
		default:
			df = sqlContext.read().load(Path.Combile(DATAPATH, fileName));
			break;
		}
		df.printSchema();
		df.show();
		return df;
	}

	public Dataset<Row> simpleRead(String fileName) {
		return simpleRead(fileName, UDataType.RAW);
	}

	public Dataset<Row> simpleReadJSON(String fileName) {
		return simpleRead(fileName, UDataType.JSON);
	}

	public Dataset<Row> simpleReadCSV(String fileName) {
		return simpleRead(fileName, UDataType.CSV);
	}
}
