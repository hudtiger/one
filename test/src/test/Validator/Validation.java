package test.Validator;

public class Validation{
	public String Description;
	public String Key;
	public String RefKey;
	public Integer ResultCode;
	public String Result;
	
	public Validation(String desc,String key,String refKey) {
		this.Description = desc;
		this.Key = key;
		this.RefKey = refKey;
		this.ResultCode=0;
		this.Result="";
	}
}