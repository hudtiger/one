package test.Validator;


import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;

public class DefaultValidator extends AbstractValidator{

	@Override
	public Validation getValidation(Object obj, JSONObject refObj, Validation vm) {
		String refString = vm.RefKey.concat(refObj.getString(vm.RefKey));
		vm.ResultCode=Pattern.matches(refString, obj.toString())?1:0;
		return vm;
	}
}