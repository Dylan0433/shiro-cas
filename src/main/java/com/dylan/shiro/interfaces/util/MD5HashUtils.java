package com.dylan.shiro.interfaces.util;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * 
 * @author loudyn
 * 
 */
public final class MD5HashUtils {
	/**
	 * 
	 * @param source
	 * @param salt
	 * @return
	 */
	public static String asMD5(Object source, Object salt) {
		return new Md5Hash(source, salt).toBase64();
	}

	private MD5HashUtils() {
	}
}
