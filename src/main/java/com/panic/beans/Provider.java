package com.panic.beans;

public class Provider {

	String requestURL;
	String accessURL;
	String authURL;
	String method;
	String version;
	
	public Provider(String requestURL, String accessURL, String authURL, String method, String version) {
		this.requestURL = requestURL;
		this.accessURL = accessURL;
		this.authURL = authURL;
		this.method = method;
		this.version = version;
	}

	public String getRequestURL() {
		return requestURL;
	}

	public String getAccessURL() {
		return accessURL;
	}

	public String getAuthURL() {
		return authURL;
	}

	public String getMethod() {
		return method;
	}

	public String getVersion() {
		return version;
	}
	
	
	
}
