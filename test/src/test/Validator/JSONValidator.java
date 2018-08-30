package test.Validator;

import com.alibaba.fastjson.JSONObject;

public class JSONValidator  extends AbstractValidator{
	@Override
	public Validation getValidation(Object obj,JSONObject refObj,Validation vm) {
		vm.ResultCode=validate(((JSONObject)obj).getJSONObject("data").getString(vm.Key),refObj.getString(vm.RefKey));
		return vm;
	}
}
