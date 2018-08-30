package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;

public class testRecusiveRegex {

	private static String subPattern = "(\\[|\\{)([^\\[\\]]*)(\\]|\\})";
	private static String arrayPattern ="\\[([^\\[\\]]*)\\]"; 
	private static String objPattern = "\\{([^\\[\\]]*?)\\}";
	
	private static String jsonStr = "[{\"Description\":\"公司名称\",\"data\":[{\"item\":[{\"detail\":\"current\"}]},{\"item\":2}],\"Key\":\"entName\",\"RefKey\":\"name\",\"Result\":\"111\",\"ResultCode\":1},{\"Description\":\"社会信用代码\",\"Key\":\"creditCode\",\"RefKey\":\"code\",\"Result\":\"12\",\"ResultCode\":1},{\"Description\":\"公司类型\",\"Key\":\"enttype\",\"RefKey\":\"codes\",\"Result\":\"121\",\"ResultCode\":0}]";
			
	private static int i = 0;
	private static void trace(String inStr) {
		System.out.println(inStr);
		Pattern pattern = Pattern.compile(arrayPattern);
		Matcher matcher = pattern.matcher(inStr);
		MatchResult result = null;
		String match = "";
		String ref = "";
		while(matcher.find()) {
			result = matcher.toMatchResult();
			System.out.println(result);
			ref = "$".concat(String.valueOf(i++));
			match =result.group(0); 
			trace(inStr.replace(match,ref));
			fillMap(ref,match);
		};
	}
	static Map<String,List<String>> dataMap = null;
	static {
		if(dataMap==null)
			dataMap = new HashMap<>();
	}
	private static void fillMap(String key,String arrayString) {
		Pattern pattern = Pattern.compile(objPattern);
		Matcher matcher = pattern.matcher(arrayString);
		List<String> tmpList = new ArrayList<>();
		while(matcher.find()) {
			tmpList.add(matcher.toMatchResult().group(0));
		}
		dataMap.put(key, tmpList);
	}
	
	static String pipeString = ",@ID::Customer No/MNEMONIC::Mnemonic/SHORT.NAME::Name/ACCOUNT.OFFICER::Account Officer/NATIONALITY::Nationality/RESIDENCE::Residence/SECTOR::Sector/INDUSTRY::Industry,\"     1\"	\"C1131193  \"	\"OGCAB                              \"	\"JOHN K SHEN         \"	\"Un\"	\"Un\"	\"CORP\"	\"COMP\"";
	public static void main(String[] args) {
//		trace(jsonStr);
//		System.out.println(JSON.toJSONString(dataMap));
		
		String[] tmp = pipeString.split("[/,\\t]");
		Arrays.asList(tmp).forEach(p->System.out.println(p));
		//递归
//		\{(([^\[\]]*|(?R)))\} //对象
//		\[([^\[\]]*|(?R))\] //数组
	}

}
