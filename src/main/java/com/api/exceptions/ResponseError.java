package com.api.exceptions;

/**
 * Key-value pair. Use to send response REST call especially in case of
 * Exception/Error
 * 
 * @author Rizwan Ishtiaq
 *
 */
public class ResponseError {

	private int code;
	private String message;

	public ResponseError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
