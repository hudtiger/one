package demo;

import scala.collection.JavaConverters;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;

public class ConverterUtil {
	public static <T> Seq<T> toSeq(Iterable<T> datas){
		return (Seq<T>) JavaConverters.asScalaIteratorConverter(datas.iterator()).asScala().toSeq();
	}
	
	public static <T> List<T> toList(Iterable<T> datas){
		return (List<T>) JavaConverters.asScalaIteratorConverter(datas.iterator()).asScala().toList();
	}
}
