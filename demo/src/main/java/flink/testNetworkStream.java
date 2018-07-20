/*****************************************************************************
 * apt install netcat 
 * nc -lp 9999 #listen localhost:9999
 * type some words like:a cat bet cat is betcat
 *****************************************************************************/
package flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class testNetworkStream {
	public static void main(String[] args) throws Exception {
		runStream("192.168.56.102", 9999);
	}
	
	private static void runStream(String host,Integer port)  throws Exception{
		StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Tuple2<String, Integer>> dataStream = env
                .socketTextStream(host, port)
                .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
					@Override
					public void flatMap(String in, Collector<Tuple2<String, Integer>> out) throws Exception {
						for (String word: in.split(" ")) {
			                out.collect(new Tuple2<String, Integer>(word, 1));
			            }
					}
				})
                .keyBy(0)
                .timeWindow(Time.seconds(5))
                .sum(1);

        dataStream.print();

        env.execute("Window WordCount");
	}
}
