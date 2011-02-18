package com.panic.method;

public interface SignatureMethod {

	public abstract String getSignature(String data, String consumerSecret, String oAuthSecret) throws java.security.SignatureException;
	public abstract String getAlgoritm();

}
