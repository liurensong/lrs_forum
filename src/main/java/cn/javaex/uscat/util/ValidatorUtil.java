package cn.javaex.uscat.util;

import java.util.regex.Pattern;

/**
 * 属性验证工具
 */
public class ValidatorUtil {

	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";

	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
	
	/**
	 * 正则表达式：验证正整数
	 */
	public static final String REGEX_POSITIVE_INTEGER = "^[0-9]*[1-9][0-9]*$";
	
	/**
	 * 校验手机号
	 * @param str
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String str) {
		return Pattern.matches(REGEX_MOBILE, str);
	}

	/**
	 * 校验邮箱
	 * @param str
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String str) {
		return Pattern.matches(REGEX_EMAIL, str);
	}
	
	/**
	 * 校验身份证
	 * @param str
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String str) {
		return Pattern.matches(REGEX_ID_CARD, str);
	}
	
	/**
	 * 校验正整数
	 * @param str
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPositiveInteger(String str) {
		return Pattern.matches(REGEX_POSITIVE_INTEGER, str);
	}
	
	public static void main(String[] args) {
		String str = "15852731771";
		boolean flag = isMobile(str);
		System.out.println(flag);
	}

}
