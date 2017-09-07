package com.socool.soft.vo;

public class Result<T> {
	private String code="1";
	private String result = "success!";
	private T data;
	private String sessionId;

    public Result() {
    }
    public Result(String code, String result) {
        this.code = code;
        this.result = result;
    }

    public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

    /**
     * 成功
     * @return
     */
	public static Result<Object> success() {
        return new Result<Object>();
    }
    public static Result<Object> success(Object data) {
	    Result result = success();
	    result.setData(data);
        return result;
    }

}
	
