package com.panic.method;

import java.net.URLEncoder;
import java.security.SignatureException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class Hmacsha1 implements SignatureMethod {
	static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	@Override
	public String getSignature(String data, String consumerSecret, String oAuthSecret) throws java.security.SignatureException {
		String result;
		try {
			Logger logger = Logger.getLogger(Hmacsha1.class);
			String key = URLEncoder.encode(consumerSecret, "UTF-8") + "&" + URLEncoder.encode(oAuthSecret, "UTF-8");
			logger.debug("Signature Key: "+key);
			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

			// get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);

			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes());

			// base64-encode the hmac
			
			result = new String(Base64.encodeBase64(rawHmac));

		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}

	@Override
	public String getAlgoritm() {
		return HMAC_SHA1_ALGORITHM;
	}

}
