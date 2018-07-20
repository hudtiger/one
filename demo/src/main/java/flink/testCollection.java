package flink;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;

import flink.tools.LineSplitter;

public class testCollection {
	public static void main(String[] args) throws Exception {

	    final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
	    env.setParallelism(2); //设置并行度
	    DataSet<String> text = env.fromElements(
	        "To be, or not to be,--that is the question:--",
	        "Whether 'tis nobler in the mind to suffer",
	        "The slings and arrows of outrageous fortune",
	        "Or to take arms against a sea of troubles,"
	        );

	    DataSet<Tuple2<String, Integer>> counts =
	        text.flatMap(new LineSplitter())
	        .groupBy(0)
	        .sum(1);

	    // execute and print result
	    counts.print();
	  }
}
