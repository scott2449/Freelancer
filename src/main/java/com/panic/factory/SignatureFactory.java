package com.panic.factory;

import com.panic.method.Hmacsha1;
import com.panic.method.SignatureMethod;

public class SignatureFactory {
	
	private SignatureFactory(){}
	
	public static SignatureMethod getProvider(String method){
		if("HMAC-SHA1".equals(method)){
			return new Hmacsha1();
		}
		return null;
	}
}
