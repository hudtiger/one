package com.gls.global;
/**   
*  
* @author yanghh  
* @date 2018年4月20日  
*/
public enum RtnResultCode {

	FAIL(0,"fail"),/* 失败状态码 */
    SUCCESS(1, "success"),/* 成功状态码 */
    
    /* 用户信息状态码 */
    USER_NOT_EXIST(1002,"not exist"),		
	CURRENT_ACCOUNT_ALREADY_EXISTS(1003,"current account already exist");	

    private Integer code;

    private String message;

    RtnResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (RtnResultCode item : RtnResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (RtnResultCode item : RtnResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
