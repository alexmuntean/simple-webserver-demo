package com.epsresoh.demolunch.exception;

public class PageNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private String uri;

	public PageNotFoundException(String uri) {
		this.uri = uri;
	}

	@Override
	public String getMessage() {
		return "Page " + uri + " not found";
	}
}
