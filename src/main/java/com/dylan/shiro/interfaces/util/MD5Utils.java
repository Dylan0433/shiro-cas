package com.dylan.shiro.interfaces.util;

import java.security.MessageDigest;

public class MD5Utils {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/***
	 * 
	 * @param bytes
	 * @return
	 */
	private static String byteArrayToHexString(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			sb.append(byteToHexString(bytes[i]));
		}
		return sb.toString();
	}

	/***
	 * 
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/***
	 * 
	 * @param origin
	 * @return
	 */
	public static String encode(String origin) {
		try {
			
			String resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			return byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
