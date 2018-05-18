package com.gls.global;

import lombok.Data;

/**
 * 
 * @author yhh
 *
 */
public class RtnResult {
	
	private Integer code;//状态
	private Object data;//返回对象
	private String msg;//消息
	
	public Integer getCode() {
		return code;
	}
	public Object getData() {
		return data;
	}
	public String getMsg() {
		return msg;
	}
	public RtnResult(){}
	public RtnResult(Integer code){
		this.code=code;
	}	
	public RtnResult(Integer code,String msg){
		this.code=code;
		this.msg=msg;
	}
	public RtnResult(Integer code,String msg,Object data){
		this.code=code;
		this.msg=msg;
		this.data=data;
	}
	
	public RtnResult setResultCode(RtnResultCode code) {
        this.code = code.code();
        this.msg = code.message();
        return this;
    }
	public RtnResult setData(Object data) {
        this.data = data;
        return this;
    }
	
	public static RtnResult Default() {
		return new RtnResult();
	}
	
	public static RtnResult Success() {
	    return	RtnResult.Default().setResultCode(RtnResultCode.SUCCESS);
	}
	
	public static RtnResult Success(Object data) {
		return RtnResult.Success().setData(data);
    }
	
	public static RtnResult Fail() {
		 return	RtnResult.Default().setResultCode(RtnResultCode.FAIL);
	}
	
	public static RtnResult Fail(Object data) {
		 return	RtnResult.Fail().setData(data);
	}
}
