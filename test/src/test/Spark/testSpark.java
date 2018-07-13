package test.Spark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.TypedColumn;
import org.apache.spark.sql.functions;

import test.model.Counter;
public class testSpark {

	public static void main(String[] args) {
//		testSql();
//		testMR();
		testUDF();
	}
	
	private static void syncDB2File() {
		Sql.getInstance().syncTable2File("user", "grpContent", UDataType.RAW,df->df.groupBy("content").count());
		Sql.getInstance().syncTable2File("user", "grpContent.csv", UDataType.CSV,df->df.groupBy("content").count());
		Sql.getInstance().syncTable2File("user", "grpContent.json", UDataType.JSON,df->df.groupBy("content").count());
	}
	
	private static void readFile() {
		 Sql.getInstance().simpleRead("grpByContent");
		 Sql.getInstance().simpleReadJSON("grpByContent.JSON");
		 Sql.getInstance().simpleReadCSV("grpByContent.CSV");
	}
	
	private static void testSql() {
//		Dataset<Row> df = Sql.getInstance().simpleReadCSV("grpByContent.CSV");
//		Sql.getInstance().execQuery(df,"tmpView","select * from tmpView where _c1>1").show();
//		Sql.getInstance().execQuery(df,"tmpView","select * from tmpView").filter("_c1>1").show();

		Dataset<Row> df1 = Sql.getInstance().execTable("user");
//		Sql.getInstance().execQuery(df1, "tmpView", "select name,count(1) qty from tmpView group by name").show();
//		Sql.getInstance().execQuery(df1, "tmpView", "select name,count(1) qty from tmpView group by name")
//			.map(row->new Counter(row.getAs("name"),row.getAs("counter")), Encoders.bean(Counter.class)).show();
		Sql.getInstance().execQuery(df1, "tmpView", "select distinct name,count(1) over() total,count(1) over(partition by name) qty,first(id) over(partition by name) first from tmpView ").show();
	}
	
	private static void testMR() {
		Dataset<Row> df = Sql.getInstance().execTable("user");
		Dataset<Counter> dft = df.selectExpr("name as subject","1 as counter").as(Encoders.bean(Counter.class));
		dft.printSchema();
		Counter c = dft.reduce((c1,c2)->{
			c1.setCounter(c1.getCounter()+c2.getCounter());
			return c1;});
		System.out.println(c);
	}
	
	//ref:http://spark.apache.org/docs/2.3.0/api/scala/index.html#org.apache.spark.sql.functions$
	private static void testUDF() {
		Dataset<Row> df = Sql.getInstance().execTable("user");
//		Sql.getInstance().execQuery(df, "tmpView", "select name,grpConcat(id) ids,count(1) qty from tmpView group by name").printSchema();
//		Sql.getInstance().execQuery(df, "tmpView", "select grpConcat(id) ids,count(1) qty from tmpView").printSchema();
		TypedColumn<Row, String> grpConcat = new TypedGroupConcat().toColumn().name("grp_concat");
		df.select(functions.format_string("%s：%s", df.apply("name"),df.col("content"))).show();
		df.agg(functions.max("name"),grpConcat).show();
		df.groupBy("name").agg(grpConcat).printSchema();
		df.select(df.col("name"),df.col("content")).show();

	}
}

