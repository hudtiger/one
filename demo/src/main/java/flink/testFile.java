package flink;


import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.core.fs.FileSystem.WriteMode;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.TableException;
import org.apache.flink.table.api.java.BatchTableEnvironment;
import org.apache.flink.configuration.Configuration;

import java.math.BigDecimal;

import flink.pojo.Result;
import flink.pojo.TableInfo;
import flink.tools.LineSplitter;

public class testFile {

	private final static String SYSPATH = System.getProperty("user.dir");
	private final static String BASEPATH = SYSPATH.concat("/").concat("filesource");
	private final static String filePath = BASEPATH.concat("/").concat( "1.txt");
	private final static String csvfilePath =BASEPATH.concat("/").concat( "data.csv");
	private final static String csvoutfilePath =BASEPATH.concat("/").concat( "dataOut.csv");

	public static void main(String[] args) throws Exception {
		//testBatch();
		
//		testCSVBatch();
		
		testAsBatch();
	}
	
	private static void testBatch() throws Exception,TableException {
		final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		DataSet<String> text = env.readTextFile(filePath);
		DataSet<Tuple2<String, Integer>> counts = text.flatMap(new LineSplitter()).groupBy(0).sum(1);
		counts.print();
	}
	
	private static void testCSVBatch() throws Exception {
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        BatchTableEnvironment  tableEnv = TableEnvironment.getTableEnvironment(env);
        //read
        DataSet<TableInfo> csvInput = env.readCsvFile(csvfilePath)
        		.ignoreFirstLine()
        		.pojoType(TableInfo.class, "tableName","tableType","dataLength","maxDataLength","indexLength","freeData","createDate");
        
        
        
        //Transfer from
        Table tableInfo = tableEnv.fromDataSet(csvInput);
        tableInfo.printSchema();
        
        //register in table enveriment
        tableEnv.registerTable("mTable",tableInfo);
        
        //query from table enveriment
//       Table couterByName=tableEnv.sqlQuery("select tableName as description,tableType as subject,count(1) counter from mTable group by tableName,tableType,dataLength");
         Table couterByName=tableEnv.scan("mTable").groupBy("tableName,tableType,dataLength").select("tableName as description,tableType as subject,count(1) as counter");
         
         //explain
         System.out.println(tableEnv.explain(couterByName));
         
         
        //transfer into
        DataSet<Result> result = tableEnv.toDataSet(couterByName,Result.class);
        DataSet<Tuple2<String,Long>> resultTuple= result.map(new MapFunction<Result, Tuple2<String,Long>>() {
            @Override
            public Tuple2<String, Long> map(Result result) throws Exception {
                String tableType = result.getSubject();
                long total = result.getCounter();
                return Tuple2.of(tableType,total);
            }
        });
      //  resultTuple.print();
        
        //write
        resultTuple.writeAsCsv(csvoutfilePath,WriteMode.OVERWRITE);       
        env.execute();
	}
	
	private static void testAsBatch() throws Exception {
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
//		DataSet<Tuple7<String,String,Long,BigDecimal,Long,Long,String>> dd = env.readCsvFile(csvfilePath)
//				.ignoreFirstLine().types(String.class,String.class,Long.class,BigDecimal.class,Long.class,Long.class,String.class);
//        dd.print();
        
        Configuration cfg = new Configuration();
        cfg.setBoolean("recursive.file.enumeration",true); //配置递归读取
        DataSet<String> files = env.readTextFile(SYSPATH.concat("/").concat("src"),"utf8").withParameters(cfg);
        files.print();
	}
}
