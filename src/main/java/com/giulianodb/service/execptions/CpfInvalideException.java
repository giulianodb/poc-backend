package com.giulianodb.service.execptions;

public class CpfInvalideException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CpfInvalideException(String msg) {
		super(msg);
	}
}
