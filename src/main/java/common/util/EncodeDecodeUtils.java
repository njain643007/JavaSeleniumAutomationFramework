package common.util;

import java.util.Base64;

public class EncodeDecodeUtils {

	public static String encodePassword(String password) {
		return Base64.getEncoder().encodeToString(password.getBytes());
	}

	public static String decodePassword(String password) {
		return new String(Base64.getDecoder().decode(password));
	}
}
