package gls.global;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public DefaultException defaultExceptionHandler(Exception e) {
       
        Map<String,Object> map = new HashMap<String,Object>();
        return new DefaultException(500,e.getMessage());
    }
}

class DefaultException{
	int code;
	String msg;
	
	static DefaultException authException=null;
	public static DefaultException Default() {
		if(authException==null)
			authException = new DefaultException(999,"Not Allowed");
		return authException;
	}
	
	public DefaultException(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
}