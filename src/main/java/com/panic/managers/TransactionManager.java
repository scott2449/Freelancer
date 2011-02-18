package com.panic.managers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.panic.beans.Consumer;
import com.panic.beans.Provider;
import com.panic.factory.SignatureFactory;

public class TransactionManager {

	private Consumer con;
	private Provider pro;
	
	private static final String CALLBACKNAME = "oauth_callback=";
	private static final String TOKENNAME = "&oauth_token=";
	private static final String SIGNATURENAME = "&oauth_signature=";
	private static final String NONCENAME = "&oauth_nonce=";
	private static final String TIMESTAMPNAME = "&oauth_timestamp=";
	private static final String METHODNAME = "&oauth_signature_method=";
	private static final String CONSUMERNAME = "&oauth_consumer_key=";
	private static final String VERSIONNAME = "&oauth_version=";
	private static final String ENC = "UTF-8";
	
	Logger logger = Logger.getLogger(TransactionManager.class);
	
	private static final String nonceSalt = "salt";
	private String timestamp;
	private String nonce;
	
	public TransactionManager(Consumer con, Provider pro) {
		super();
		this.con = con;
		this.pro = pro;
		timestamp = String.valueOf((new Date().getTime())/1000);
		try {
			nonce = URLEncoder.encode(new String(Base64.encodeBase64((timestamp+nonceSalt).getBytes())),ENC);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String signURL(String url) throws UnsupportedEncodingException, SignatureException{
		url += (url.indexOf('?')>-1)?baseQuery():'?'+baseQuery().substring(1);
		String sigBase = getSignatureBase(con.getHttpMethod(),url);
		logger.debug("Signature Base: "+sigBase);
		String sig = URLEncoder.encode(SignatureFactory.getProvider(pro.getMethod()).getSignature(sigBase, con.getConsumerSecret(), con.getOAuthToken().getSecret()),ENC);
		logger.debug("Signed URL: "+url + SIGNATURENAME+sig);
		return url + SIGNATURENAME+sig;
	}
	
	public String getRequestURL() throws UnsupportedEncodingException, SignatureException{
		return signURL(pro.getRequestURL()+"?"
			+CALLBACKNAME+URLEncoder.encode(con.getCallbackURL(),ENC));
	}
	
	public String getAccessURL(String accessParams){
		return pro.getAccessURL()+"?"
			+accessParams
			+baseQuery();
	}
	
	public String getAuthURL(){
		return pro.getAuthURL()+"?"+TOKENNAME+con.getOAuthToken().getValue();
	}
	
	private String baseQuery(){
		return CONSUMERNAME+con.getConsumerToken()
			+NONCENAME+nonce
			+METHODNAME+pro.getMethod()
			+TIMESTAMPNAME+timestamp
			+((!con.getOAuthToken().getValue().equals(""))?TOKENNAME+con.getOAuthToken().getValue():"")
			+VERSIONNAME+pro.getVersion();
	}
	
	private String getSignatureBase(String method, String url) throws UnsupportedEncodingException{
		String params = "";
		if(url.indexOf('?')>-1){
			params = url.substring(url.indexOf('?')+1);
			url = url.substring(0,url.indexOf('?'));
		}
		return method+"&"+URLEncoder.encode(url,ENC)+"&"
			+URLEncoder.encode(params,ENC);
	}
	
}
