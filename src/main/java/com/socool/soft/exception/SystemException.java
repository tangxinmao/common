package com.socool.soft.exception;

import com.socool.soft.vo.Result;

public class SystemException extends RuntimeException {

	private static final long serialVersionUID = -6437262362672569551L;
	
	private ErrorCode errorCode;

	public SystemException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public SystemException(ErrorCode errorCode, Throwable t) {
		super(errorCode.getMessage(), t);
		this.errorCode = errorCode;
	}
	
	public SystemException(ErrorCode errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	public SystemException(ErrorCode errorCode, String msg, Throwable t) {
		super(msg, t);
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}

    /**
     * 获取Result, 异常转Result对象
     * @return
     */
	public Result<Object> getResult() {
	    if(this.errorCode == null) {
	        return null;
        }
        return new Result<Object>(this.getErrorCode().getCode() + "", this.getMessage());
    }
}
