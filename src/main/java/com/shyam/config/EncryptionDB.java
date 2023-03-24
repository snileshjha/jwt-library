package com.shyam.config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shyam.model.ConfigData;
import com.shyam.model.JwtConfig;
import com.shyam.repository.JwtRepository;

@Component
public class EncryptionDB {
	    private static SecretKeySpec secretKey;
	    private static byte[] key;
	    private static final String ALGORITHM = "AES";
	  

//	    public static void main(String[] args) throws Exception {
	   //   System.out.println((new EncryptionDB().toEncrypt("Admin#321")));
//	   	 System.out.println((new EncryptionDB().toDecrypt("F/bBqA7hnbK2w6zFsCOVOQ==")));
//	   	  System.out.println((new EncryptionDB().toEncrypt("root"))); }
	   	 
	    
	    
	    public void prepareSecreteKey(String myKey) {
	        MessageDigest sha = null;
	        try {
	            key = myKey.getBytes(StandardCharsets.UTF_8);
	            sha = MessageDigest.getInstance("SHA-1");
	            key = sha.digest(key);
	            key = Arrays.copyOf(key, 16);
	            secretKey = new SecretKeySpec(key, ALGORITHM);
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	        }
	    }

	    public String toEncrypt(String strToEncrypt) {
	        try {
	        	prepareSecreteKey(ConfigData.secret);
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	        } catch (Exception e) {
	            System.out.println("Error while encrypting: " + e.toString());
	        }
	        return null;
	    }

	    public String toDecrypt(String strToDecrypt) {
	        try {
	        	prepareSecreteKey(ConfigData.secret);
	            Cipher cipher = Cipher.getInstance(ALGORITHM);
	            cipher.init(Cipher.DECRYPT_MODE, secretKey);
	            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	        } catch (Exception e) {
	            System.out.println("Error while decrypting: " + e.toString());
	        }
	        return null;
	    }

}
