package test;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.sql.DataFrameReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

public class testSpark {
	
	public static void main(String[] args) {
		SparkSql.simpleExec("user");
	}
}
class Path{
	public static String Combile(final String basePath,final String... names) {
		return FileUtils.getFile(basePath,FileUtils.getFile(names).getPath()).getPath();
	}
}

class SparkSql{
	private static Logger logger  = LogManager.getLogger(SparkSql.class); 
	private final static String SYSPATH =  System.getProperty("user.dir") ;
	private final static String BASEPATH = Path.Combile(SYSPATH,"spark");
	private final static String DATAPATH = Path.Combile(SYSPATH,"spark","data");
	private final static String JSONPATH = Path.Combile(SYSPATH,"spark","json");
	private final static String CSVPATH = Path.Combile(SYSPATH,"spark","csv");
	private final static String CONNMYSQL = "jdbc:mysql://192.168.56.102:3306/testDB?user=root&password=123456&useSSL=false";
	private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static SQLContext sqlContext=null;
	static {
		System.setProperty("hadoop.home.dir", BASEPATH);
		SparkSession spark = SparkSession
			      .builder()
			      .appName("Java Spark SQL data sources example")
			      .config("spark.some.config.option", "some-value")
			      .config("spark.sql.warehouse.dir",DATAPATH) //配置文件保存位置
			      .master("local")
			      .getOrCreate();
		sqlContext = spark.sqlContext();
	}
	
	private static DataFrameReader getMysqlReader(String tableName) {
		return sqlContext.read().format("jdbc").option("driver", DRIVER).option("url", CONNMYSQL).option("dbtable", tableName);
	}
	
	public static void simpleExec(String tableName) {
		Dataset<Row> df = getMysqlReader(tableName).load();
		df.printSchema();
		Dataset<Row> countsByAge = df.groupBy("content").count();
		countsByAge.show();
		countsByAge.write().saveAsTable("grpByContent"); //默认保存到spark-warehouse目录
//		countsByAge.write().mode(SaveMode.Overwrite).format("json").save("grpByContent.json");
		countsByAge.write().mode(SaveMode.Overwrite).json(Path.Combile(JSONPATH,"grpByContent.json"));
	}
}
