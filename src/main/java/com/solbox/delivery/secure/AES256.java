package com.solbox.delivery.secure;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

class AES256 {
	static String alg = "AES/CBC/PKCS5Padding";
    private final String ivString = "SOLBOX_ENC_URL"; //14 byte
    private byte[] iv = new byte[16]; 
    
    AES256() {
    	System.arraycopy( ivString.getBytes(), 0, iv, 0, ivString.getBytes().length );
    }
    
    String encrypt(String text, byte[] key) throws Exception {
		Cipher cipher = Cipher.getInstance(alg);
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		IvParameterSpec ivParamSpec = new IvParameterSpec(iv);
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);
		
		byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
		
		//base64 safe
		return Base64.getUrlEncoder().withoutPadding().encodeToString( encrypted ); 
    }
}
