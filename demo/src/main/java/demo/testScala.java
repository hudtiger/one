package demo;

import java.util.Arrays;

import scala.Function1;
import scala.collection.immutable.Seq;
import scala.runtime.AbstractFunction1;
import scala.collection.immutable.List;
import ts.demo.*;


public class testScala {
	
	public static void main(String[] args) {
		String[] data = new String[] {"Runoob", "Baidu", "Google"};
		System.out.println(App.foo(data));
		
		Responstory.say("hello23");
		
		Seq<String> datas =ConverterUtil.toSeq(Arrays.asList("Runoob", "Baidu", "Google"));
		Responstory.say(datas);
		
		System.out.println(Responstory.sayCallback().apply(datas.toList()));
		
		System.out.println(Responstory.say(datas.toList(),Responstory.sayCallback()));
		
		//函数传递
		Function1<String,String> msgFunc = new AbstractFunction1<String,String>(){
			public String apply(String msg) {
				return "foo"+msg;
			}};
		System.out.println(msgFunc.apply("Micheal"));
		System.out.println(Responstory.say("Micheal",msgFunc));
		
		List<String> dataList =ConverterUtil.toList(Arrays.asList("Runoob", "Baidu", "Google"));
		System.out.println(Responstory.say(dataList,new AbstractFunction1<List<String>,String>(){
			public String apply(List<String> data) {
				return "foo";
			}}));
		
		Responstory.say("Micheal",new IExecutor() {
			@Override
			public String execute(String msg) {
				System.out.println(msg);
				return "hello ".concat(msg);
			}});
		
		Responstory.say("John",new Res() {
			@Override
			public String exec(String msg) {
				System.out.println(msg);
				return "hello ".concat(msg);
			}
		});
	}

}
