package com.wg.dabms.util.password;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

	private static final SecureRandom random = new SecureRandom();
	private static final String ALGORITHM = "SHA-256";

	public static String generateSalt() {
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public static String hashPassword(String password, String salt) {
		try {
			MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
			digest.update(Base64.getDecoder().decode(salt));
			byte[] hashedPassword = digest.digest(password.getBytes());
			return Base64.getEncoder().encodeToString(hashedPassword);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Hashing algorithm not found", e);
		}
	}

	public static boolean verifyPassword(String password, String salt, String hashedPassword) {
		String newHash = hashPassword(password, salt);
		return newHash.equals(hashedPassword);
	}
	
}