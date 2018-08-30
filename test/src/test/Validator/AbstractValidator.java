package test.Validator;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public abstract class AbstractValidator{
	/**
	 * @param val
	 * @param refVal
	 * @return boolean
	 */
	public Integer validate(String val,String refVal) {
		System.out.printf("%s\t%s\r\n",val,refVal);
		return val.equalsIgnoreCase(refVal)?1:0;
	}
	
	public abstract Validation getValidation(Object obj,JSONObject refObj,Validation vm);
	
	public List<Validation> getValidations(Object obj,JSONObject refObj,List<Validation> validateMapper) {
		validateMapper.forEach(vm->getValidation(obj,refObj,vm));
		return validateMapper;
	}
	
	public static AbstractValidator getInstance(ValidateType type) {
		AbstractValidator validator = null;
		switch(type) {
			case RAW:
				validator = new DefaultValidator();
				break;
			case JSON:
				validator = new JSONValidator();
				break;
			default:
				validator=new DefaultValidator();
		}
		return validator;
	}
}






