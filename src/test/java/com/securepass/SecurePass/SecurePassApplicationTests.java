package com.securepass.SecurePass;

import com.securepass.SecurePass.domain.StandardCredPreference;
import com.securepass.SecurePass.service.AESEncryptionService;
import com.securepass.SecurePass.service.PasswordGeneratorService;
import com.securepass.SecurePass.service.PasswordStrengthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@SpringBootTest
class SecurePassApplicationTests {

	@Autowired
	private AESEncryptionService aesEncryptionService;

	@Autowired
	private PasswordGeneratorService passwordGeneratorService;

	@Autowired
	private PasswordStrengthService passwordStrengthService;

	@Test
	void encrypt() {
		String input = "raksha";
		String cipherText = aesEncryptionService.getEncryptedPassword(input);
		System.out.println("cipherText: "+ cipherText);
	}

	@Test
	void decrypt() {
		String cipherText = "ixu3waJ58o16whw/e0Gwzw==!-=£!Ybhfdq3x5evkB4Twgq4/Aw==";
		String plainText = aesEncryptionService.getDecryptedPassword(cipherText);
		System.out.println("plainText: "+ plainText);

	}

	@Test
	void testKey() throws NoSuchAlgorithmException{
		System.out.println(aesEncryptionService.generateKey(256));
		//javax.crypto.spec.SecretKeySpec@16be3
	}

	@Test
	void testBase64(){
		String input = "Ue5PamP1cqc+GC4LafQgfw==";
		byte[] iv = Base64.getDecoder().decode(input);
		System.out.println("iv: "+ iv);
	}

	@Test
	void testMatch(){
		String password = "a";
		if(password.equals(aesEncryptionService.getDecryptedPassword("4sudzQUXUcXR/9Gsm1olLQ==!-=£!jOkiTP0I074xG5Y1IFIueA=="))){
			System.out.println("Matched an entry with old password");
		}else{
			System.out.println("No match found");
		}
		String input = "a";
		System.out.println(input.equals("a"));
	}

	@Test
	void generatePassword() throws NoSuchAlgorithmException {
		StandardCredPreference credPref = new StandardCredPreference();
		credPref.setLength(12);
		credPref.setSymbols(true);
		credPref.setUppercase(true);
		credPref.setAmbiguousCharacters(true);
		credPref.setSimilarCharacters(true);
		String password = passwordGeneratorService.checkAlgorithm(credPref);
		System.out.println(password);
	}

	@Test
	void checkPasswordStrength() {
		String password = "a";
		System.out.println("Success: " + passwordStrengthService.checkPasswordStrength(password).getSuccess());
		System.out.println("Message: " + passwordStrengthService.checkPasswordStrength(password).getMessage());
	}
}
