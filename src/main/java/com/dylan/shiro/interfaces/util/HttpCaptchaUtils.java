package com.dylan.shiro.interfaces.util;

import javax.servlet.http.HttpSession;

import nl.captcha.Captcha;

import org.apache.commons.lang.StringUtils;

import com.dylan.shiro.interfaces.exception.InvalidCaptchaException;
import com.youboy.util.AssertUtils;

/**
 * 
 * @author loudyn
 * 
 */
public final class HttpCaptchaUtils {

	private static final String CAPTCHA_ATTR = "com.mimo.cms.captcha";

	/**
	 * 
	 * @param captchaString
	 * @param session
	 */
	public static void checkCaptcha(String captchaString, HttpSession session) {

		// captcha.isCorrect maybe cause NullPointException,so must check the captchaString first.
		if (StringUtils.isBlank(captchaString)) {
			throw new InvalidCaptchaException("Blank captcha!");
		}

		AssertUtils.notNull(session, new InvalidCaptchaException("None session!"));
		Captcha captcha = (Captcha) session.getAttribute(CAPTCHA_ATTR);
		if (null == captcha || !captcha.isCorrect(captchaString)) {
			throw new InvalidCaptchaException("Wrong captcha!");
		}
	}

	/**
	 * 
	 * @param captcha
	 * @param session
	 */
	public static void storeCaptcha(Captcha captcha, HttpSession session) {
		session.setAttribute(CAPTCHA_ATTR, captcha);
	}

	private HttpCaptchaUtils() {
	}
}
