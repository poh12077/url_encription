package com.solbox.delivery.secure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

 class SHA256 {
	   byte[] encrypt(String text) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(text.getBytes());
	        return md.digest();
	    }
}
