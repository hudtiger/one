/**
select a.condition_code,b.name as condition_name,a.key,c.interface_name,a.interface_code,c.interface_url,
		d.file_name,a.file_code,a.request_param,a.response_check_param,a.file_check_field
	from financing_risk_interface_file_relation a 
		inner join financing_risk_condition_sub b on a.condition_code = b.condition_code and a.key = b.key and  b.is_selected=1 and b.is_deleted=0
        inner join financing_interface c on a.interface_code = c.interface_code
        inner join financing_file_code d on a.file_code = d.file_code
	where a.condition_code='CreditCheckCN';
*/
package test.Validator;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class testCheckThirdInterface {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	InvoiceRepostry invoiceRepostry=null;
	InterfaceService interfaceService = null;
	public List<InterfaceValidation> validate(String conditionCode,String invoiceNo) {
		List<InterfaceValidation> res = new ArrayList<>();
		Map<String, List<InterfaceConfig>> grpConfigs = interfaceService.getGroupedInterfaces(conditionCode); 
		InterfaceValidation tmpValidation = null;
		for(String key:grpConfigs.keySet()) {
			tmpValidation = new InterfaceValidation(key);
			for(InterfaceConfig cfg:grpConfigs.get(key)) {
				tmpValidation.setContitionName(cfg.getContitionName()).addItems(validte(cfg,invoiceNo));
			}
			res.add(tmpValidation);
		}
		return res;
	}
	private InterfaceValidationItem validte(InterfaceConfig cfg,String invoiceNo) {
		InterfaceValidationItem tmpValidationItem = new InterfaceValidationItem(cfg.getInterfaceName(),cfg.getInterfaceCode());
	    
		//循环
		JSONObject content = JSON.parseObject(invoiceRepostry.getContent(invoiceNo, cfg.getFileCode()));
	    
	    String url = MessageFormat.format(cfg.getInterfaceUrl(),Arrays.asList(cfg.getRequestParam().split(",")).stream().map(key->content.get(key).toString()));
	    //请求String body = HttpUtils.get(url);
	    //响应后续逻辑
		return tmpValidationItem;
	}
}

class InterfaceValidation{
	String contitionName;
	String key;
	List<InterfaceValidationItem> items=null;
	public InterfaceValidation(String key) {
		this.key = key;
		this.items = new ArrayList();
	}
	public InterfaceValidation addItems(InterfaceValidationItem item) {
		this.items.add(item);
		return this;
	}
	public String getContitionName() {
		return contitionName;
	}
	public InterfaceValidation setContitionName(String contitionName) {
		this.contitionName = contitionName;
		return this;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<InterfaceValidationItem> getItems() {
		return items;
	}
	
}

class InterfaceValidationItem{
	String interfaceName;
	String interfaceCode;
	String response; //json返回
	
	public InterfaceValidationItem(String interfaceName,String interfaceCode) {
		this.interfaceName = interfaceName;
		this.interfaceCode = interfaceCode;
	}
	
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
}

interface InvoiceRepostry{
	String getContent(String invoiceNo,String fileCode);
}

interface InterfaceService{
	List<InterfaceConfig> getAllInterfacesByCondition(String conditionCode);
	
	//接口按校验类型分组
	default Map<String, List<InterfaceConfig>> getGroupedInterfaces(String conditionCode) {
		return this.getAllInterfacesByCondition(conditionCode).stream().collect(Collectors.groupingBy(InterfaceConfig::getKey, Collectors.toList()));
	}
}

class InterfaceConfig{
	String conditionCode; //验证分组：授信、签约、提款
	String contitionName; //验证类型：工商信息、法律诉讼、黑名单
	String key;			  //验证类型key
	String interfaceName; //接口名称
	String interfaceCode; //接口code
	String interfaceUrl;  //接口
	String fileName;      //关联文件名称
	String fileCode;	  //关联文件码
	String requestParam;  //接口请求参数对应文件中的映射
	String responseCheckParam; 	//接口返回结果中需校验的字段
	String fileCheckField; 		//接口返回结果校验字段对应文件中的映射
	public String getConditionCode() {
		return conditionCode;
	}
	public void setConditionCode(String conditionCode) {
		this.conditionCode = conditionCode;
	}
	public String getContitionName() {
		return contitionName;
	}
	public void setContitionName(String contitionName) {
		this.contitionName = contitionName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	public String getInterfaceUrl() {
		return interfaceUrl;
	}
	public void setInterfaceUrl(String interfaceUrl) {
		this.interfaceUrl = interfaceUrl;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public String getRequestParam() {
		return requestParam;
	}
	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}
	public String getResponseCheckParam() {
		return responseCheckParam;
	}
	public void setResponseCheckParam(String responseCheckParam) {
		this.responseCheckParam = responseCheckParam;
	}
	public String getFileCheckField() {
		return fileCheckField;
	}
	public void setFileCheckField(String fileCheckField) {
		this.fileCheckField = fileCheckField;
	}
}


