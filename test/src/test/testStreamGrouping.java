package test;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;

public class testStreamGrouping {

	public static void main(String[] args) {
		List<ExtendClass> list = Arrays.asList(
				new ExtendClass("A-B-1",23),
				new ExtendClass("A-D",44),
				new ExtendClass("1-C-3",33),
				new ExtendClass("1-C-4",44),
				new ExtendClass("A-D-3",3));
		
		Map<String, List<ExtendClass>> obj = list.stream().collect(Collectors.groupingBy(ExtendClass::getCaption, Collectors.toList()));
		
		Map<String, Map<String,List<ExtendClass>>> obj1 = list.stream().collect(
				Collectors.groupingBy(ExtendClass::getCaption, 
						Collectors.groupingBy(ExtendClass::getSubject,Collectors.toList())));
		
	
		System.out.println(JSONObject.toJSONString(obj1));
		
		Map<String, DoubleSummaryStatistics> obj2 = list.stream().collect(Collectors.groupingBy(ExtendClass::getCaption,Collectors.summarizingDouble(ExtendClass::getAge)));
		
		System.out.println(obj2.get("1").getSum());
	}
}

class ExtendClass{
	String caption;
	String subject;
	String lev;
	int age;
	public ExtendClass(String subject,int age) {
		String[] tmp = subject.split("-");
		if(tmp.length>2)
			this.lev = subject.split("-")[2];
		this.subject=subject.split("-")[1];
		this.caption = subject.split("-")[0];
		this.age = age;
	}
	
	public String getLevel() {
		return this.lev;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public String getSubject() {
		return this.subject;
	}
	
	public int getAge() {
		return this.age;
	}
}
