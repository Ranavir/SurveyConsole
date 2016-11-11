package com.stl.surveyconsole.utils;

import java.security.MessageDigest;

public class MD5 {
	public static String getEncriptedPassword(String password) {
		String generatedKey=null;
		try{
			MessageDigest algorithm = MessageDigest.getInstance("MD5"); 
			algorithm.update(password.getBytes());
	      	byte[] output = algorithm.digest();	      	
			generatedKey = bytesToHex(output).toLowerCase();
		}catch(Exception e){
			e.printStackTrace();
		}		
		return generatedKey;
	}
	
	public static String bytesToHex(byte[] b) {
		char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
	    StringBuffer buf = new StringBuffer();
	    for (int j=0; j<b.length; j++) {
	    	buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
	        buf.append(hexDigit[b[j] & 0x0f]);
	    }
	    return buf.toString();
	}
}