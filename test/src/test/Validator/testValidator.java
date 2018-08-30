package test.Validator;

import java.util.ArrayList;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class testValidator {

	public static void main(String[] args) {
//		JSONObject obj=JSON.parseObject("{\"esdate\":\"2015/02/0900:00:00.000\",\"dom\":\"深圳市前海深港合作区前湾一路1号A栋201室(入驻深圳市前海商务秘书有限公司)\",\"entName\":\"格罗斯产业链服务(深圳)有限公司\",\"idCard\":\"\",\"enttype\":\"有限责任公司\",\"dishonestyCount\":0,\"otherCount\":0,\"source\":1,\"opfrom\":\"\",\"creditCode\":\"914403003265314426\",\"goodCount\":0,\"solrStatus\":1,\"legalPerson\":\"刘海明\",\"cfCount\":0,\"regorg\":\"深圳市工商行政管理局\",\"rank\":0,\"id\":10142916620,\"revdate\":\"\",\"sysUpdateTime\":\"2018-07-12 04:20:49.0\",\"area\":\"\",\"regno\":\"440301112231740\",\"opscope\":\"\",\"opto\":\"\",\"taxCode\":\"\",\"badCount\":0,\"candate\":\"\",\"organizationCode\":\"\",\"regcap\":\"\",\"xkCount\":0,\"validTime\":\"20180705\",\"entstatus\":1,\"apprdate\":\"\",\"md5\":\"1f1e46d767fce494a4fcf630c686edaf\"}");
		JSONObject obj=JSON.parseObject("{\"data\":{\"esdate\":\"2015/02/0900:00:00.000\",\"dom\":\"深圳市前海深港合作区前湾一路1号A栋201室(入驻深圳市前海商务秘书有限公司)\",\"entName\":\"格罗斯产业链服务(深圳)有限公司\",\"idCard\":\"\",\"enttype\":\"有限责任公司\",\"dishonestyCount\":0,\"otherCount\":0,\"source\":1,\"opfrom\":\"\",\"creditCode\":914403003265314426,\"goodCount\":0,\"solrStatus\":1,\"legalPerson\":\"刘海明\",\"cfCount\":0,\"regorg\":\"深圳市工商行政管理局\",\"rank\":0,\"id\":10142916620,\"revdate\":\"\",\"sysUpdateTime\":\"2018-07-12 04:20:49.0\",\"area\":\"\",\"regno\":\"440301112231740\",\"opscope\":\"\",\"opto\":\"\",\"taxCode\":\"\",\"badCount\":0,\"candate\":\"\",\"organizationCode\":\"\",\"regcap\":\"\",\"xkCount\":0,\"validTime\":\"20180705\",\"entstatus\":1,\"apprdate\":\"\",\"md5\":\"1f1e46d767fce494a4fcf630c686edaf\"}}");
		JSONObject refObj =new JSONObject();
		refObj=refObj.fluentPut("name", "格罗斯产业链服务(深圳)有限公司").fluentPut("code", "914403003265314426");
		
		ArrayList<Validation> list = new ArrayList<>();
		list.add(new Validation("公司名称","entName","name"));
		list.add(new Validation("社会信用代码","creditCode","code"));
		list.add(new Validation("公司类型","enttype","codes"));
		
		AbstractValidator.getInstance(ValidateType.JSON).getValidations(obj, refObj, list);
		System.out.println(JSON.toJSONString(list));
		System.out.println(obj.toString());
	}
}
