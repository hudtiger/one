package flink;

import java.util.stream.Collectors;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.jdbc.JDBCInputFormat;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.RowTypeInfo;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.types.Row;
import org.apache.flink.util.Collector;

public class testDB {

	private final static String CONNMYSQL = "jdbc:mysql://192.168.56.102:3306/testDB?user=root&password=123456&useSSL=false";
	private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	public static void main(String[] args) throws Exception {
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		JDBCInputFormat inputFmt =JDBCInputFormat.buildJDBCInputFormat()
	        .setDrivername(DRIVER)
	        .setDBUrl(CONNMYSQL)
	        .setUsername("root")
	        .setPassword("123456")
	        .setQuery("select FName,FManager from CTE")
	        .setRowTypeInfo(new RowTypeInfo(BasicTypeInfo.STRING_TYPE_INFO, BasicTypeInfo.INT_TYPE_INFO))
	        .finish();
	
//		DataSet<Row> dbData = env.createInput(inputFmt);

//		DataSet<Tuple2<String, Integer>> dbData = env.createInput(inputFmt).map(new MapFunction<Row, Tuple2<String, Integer>>(){
//			@Override
//			public Tuple2<String, Integer> map(Row value) throws Exception {
//				return new Tuple2<String,Integer>(value.getField(0).toString(),Integer.parseInt(value.getField(1).toString()));
//			}	
//		});
		DataSet<Tuple2<String, Integer>> dbData=env.createInput(inputFmt).flatMap(new FlatMapFunction<Row,Tuple2<String,Integer>>(){
			@Override
			public void flatMap(Row value, Collector<Tuple2<String, Integer>> out) throws Exception {
				out.collect(new Tuple2<String,Integer>(value.getField(0).toString(),1));
			}
		}).groupBy(0).sum(1);
		dbData.print();
		
	}

}

//public abstract class JDBCInputFormat extends InputFormat<Tuple2<String, Integer>, ?> 
