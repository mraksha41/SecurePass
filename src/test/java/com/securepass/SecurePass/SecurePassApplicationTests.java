package com.securepass.SecurePass;

import com.securepass.SecurePass.service.AESEncryptionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

@SpringBootTest
class SecurePassApplicationTests {

	private AESEncryptionService aesEncryptionService;


	@Test
	void contextLoads() {
	}

	@Test
	void givenString_whenEncrypt_thenSuccess()
			throws NoSuchAlgorithmException, IllegalBlockSizeException, InvalidKeyException,
			BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException {

		String input = "raksha";
		SecretKey key = aesEncryptionService.generateKey(256);
		IvParameterSpec ivParameterSpec = aesEncryptionService.generateIv();
		String algorithm = "AES/CBC/PKCS5Padding";
		String cipherText = aesEncryptionService.encrypt(algorithm, input, key, ivParameterSpec);
		System.out.println("cipherText: "+ cipherText);
		String plainText = aesEncryptionService.decrypt(algorithm, cipherText, key, ivParameterSpec);
		System.out.println("cipherText: "+ cipherText);
		System.out.println("plainText: "+ plainText);
		Assertions.assertEquals(input, plainText);
	}

}
