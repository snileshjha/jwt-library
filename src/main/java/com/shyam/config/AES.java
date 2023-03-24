package com.shyam.config;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class AES {
	/*
	 * @Value("${ENCRYPTION_DECRYPTION_KEY}") private String
	 * ENCRYPTION_DECRYPTION_KEY;
	 */
	
	/*
	 * public static void main(String[] args) throws Exception {
	 * 
	 * System.out.println((new
	 * EncryptionDB().toEncrypt("Hare Krishna","afx@arthmate")));
	 * System.out.println((new EncryptionDB().toDecrypt(
	 * "4dbc1bd1589b677f3cce3f51ab49e146.LOeENmY3M3MkAZmBxI8qLw==","afx@arthmate")))
	 * ; }
	 */
	
	public String encrypt(String strToEncrypt,String secret) {

		try {
			byte[] iv = new byte[16];
			new SecureRandom().nextBytes(iv);
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] encodedhash = digest.digest(secret.getBytes(StandardCharsets.UTF_8));

			BigInteger number = new BigInteger(1, encodedhash);
			StringBuilder hexString = new StringBuilder(number.toString(16));

			while (hexString.length() < 32) {
				hexString.insert(0, '0');
			}

//			System.out.println(hexString.toString().substring(0, 32));
			SecretKeySpec secretKey = new SecretKeySpec(hexString.toString().substring(0, 32).getBytes(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			String encryptedStr = Base64.getEncoder()
					.encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));

			String strIV = encodeHexString(iv);
			String finalEncryptedStr = encryptedStr.replaceAll("\\+", "\\-").replaceAll("\\/", "\\_");
//			System.out.println("Encrypted String : "+(strIV + "." + finalEncryptedStr));
			return strIV + "." + finalEncryptedStr;

		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public String decrypt(String strToDecrypt,String secret) {
		try {

			String strIV = strToDecrypt.substring(0, strToDecrypt.indexOf("."));
			String encTexts = strToDecrypt.substring(strToDecrypt.indexOf(".") + 1);
			String encText = encTexts.replaceAll("\\-", "\\+").replaceAll("\\_", "\\/");

			byte[] iv = decodeHexString(strIV);

			IvParameterSpec ivspec = new IvParameterSpec(iv);

			MessageDigest digest = MessageDigest.getInstance("SHA-256");

			byte[] encodedhash = digest.digest(secret.getBytes(StandardCharsets.UTF_8));
      
			BigInteger number = new BigInteger(1, encodedhash);
			StringBuilder hexString = new StringBuilder(number.toString(16));

			while (hexString.length() < 32) {
				hexString.insert(0, '0');
			}

//			System.out.println(hexString.toString().substring(0, 32));

			SecretKeySpec secretKey = new SecretKeySpec(hexString.toString().substring(0, 32).getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(encText)));

		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

	public String encodeHexString(byte[] byteArray) {
		StringBuffer hexStringBuffer = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			hexStringBuffer.append(byteToHex(byteArray[i]));
		}
		return hexStringBuffer.toString();
	}

	public byte[] decodeHexString(String hexString) {
		if (hexString.length() % 2 == 1) {
			throw new IllegalArgumentException("Invalid hexadecimal String supplied.");
		}

		byte[] bytes = new byte[hexString.length() / 2];
		for (int i = 0; i < hexString.length(); i += 2) {
			bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
		}
		return bytes;
	}

	public String byteToHex(byte num) {
		char[] hexDigits = new char[2];
		hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
		hexDigits[1] = Character.forDigit((num & 0xF), 16);
		return new String(hexDigits);
	}

	public byte hexToByte(String hexString) {
		int firstDigit = toDigit(hexString.charAt(0));
		int secondDigit = toDigit(hexString.charAt(1));
		return (byte) ((firstDigit << 4) + secondDigit);
	}

	private int toDigit(char hexChar) {
		int digit = Character.digit(hexChar, 16);
		if (digit == -1) {
			throw new IllegalArgumentException("Invalid Hexadecimal Character: " + hexChar);
		}
		return digit;
	}

}
