package com.panic.beans;

import java.util.HashMap;
import java.util.Map;

public class OAuthToken {

	final static String TOKENNAME = "oauth_token";
	final static String SECRETNAME = "oauth_token_secret";
	
	String value="";
	String secret="";	
	
	public OAuthToken(){}
	
	public OAuthToken(String params) {
		Map<String, String> qm = getQueryMap(params);
		this.value = (qm.get(TOKENNAME)==null)?"":qm.get(TOKENNAME);
		this.secret = (qm.get(SECRETNAME)==null)?"":qm.get(SECRETNAME);
	}
	
	public String getValue() {
		return value;
	}
	public String getSecret() {
		return secret;
	}
	
	public static Map<String, String> getQueryMap(String query)  
	{  
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();  
	    for (String param : params)  
	    {  
	        String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }  
	    return map;  
	}  


}
