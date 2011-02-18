package com.panic.beans;

public class Consumer {

	String httpMethod;
	String consumerToken;
	String consumerSecret;
	String callbackURL;
	OAuthToken token = new OAuthToken();
	
	public Consumer(String httpMethod, String consumerToken, String consumerSecret, String callbackURL) {
		super();
		this.httpMethod = httpMethod;
		this.consumerToken = consumerToken;
		this.consumerSecret = consumerSecret;
		this.callbackURL = callbackURL;
	}
	public String getConsumerToken() {
		return consumerToken;
	}
	public String getConsumerSecret() {
		return consumerSecret;
	}
	public String getCallbackURL() {
		return callbackURL;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	
	public void setOAuthToken(OAuthToken token){
		this.token=token;
	}
	
	public OAuthToken getOAuthToken(){
		return token;
	}
	
}
