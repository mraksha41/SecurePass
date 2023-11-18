package com.securepass.SecurePass.service;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class AESEncryptionService {

    public static String getEncryptedPassword(String password) {
        String cipherText = "";
        try{
            SecretKey key = generateKey(256);
            IvParameterSpec ivParameterSpec = generateIv();
            String algorithm = "AES/CBC/PKCS5Padding";
            cipherText = encrypt(algorithm, password, key, ivParameterSpec);
            System.out.println("cipherText: "+ cipherText);
            return cipherText;
        }catch (NoSuchPaddingException noSuchPaddingException){
            System.out.println("NoSuchPaddingException Exception: " + noSuchPaddingException);
        }catch (NoSuchAlgorithmException noSuchAlgorithmException){
            System.out.println("NoSuchAlgorithmException Exception: " + noSuchAlgorithmException);
        }catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException){
            System.out.println("InvalidAlgorithmParameterException Exception: " + invalidAlgorithmParameterException);
        }catch (InvalidKeyException invalidKeyException){
            System.out.println("InvalidKeyException Exception: " + invalidKeyException);
        }catch (BadPaddingException badPaddingException){
            System.out.println("BadPaddingException Exception: " + badPaddingException);
        }catch (IllegalBlockSizeException illegalBlockSizeException){
            System.out.println("IllegalBlockSizeException Exception: " + illegalBlockSizeException);
        }catch (Exception ex){
            System.out.println("Exception: " + ex);
        }finally {
            return cipherText;
        }
    }

    public static String getDecryptedPassword(String cipherText) {
        String plainText = "";
        try{
            SecretKey key = generateKey(256);
            IvParameterSpec ivParameterSpec = generateIv();
            String algorithm = "AES/CBC/PKCS5Padding";
            plainText = decrypt(algorithm, cipherText, key, ivParameterSpec);
            System.out.println("plainText: "+ plainText);
            return plainText;
        }catch (NoSuchPaddingException noSuchPaddingException){
            System.out.println("NoSuchPaddingException Exception: " + noSuchPaddingException);
        }catch (NoSuchAlgorithmException noSuchAlgorithmException){
            System.out.println("NoSuchAlgorithmException Exception: " + noSuchAlgorithmException);
        }catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException){
            System.out.println("InvalidAlgorithmParameterException Exception: " + invalidAlgorithmParameterException);
        }catch (InvalidKeyException invalidKeyException){
            System.out.println("InvalidKeyException Exception: " + invalidKeyException);
        }catch (BadPaddingException badPaddingException){
            System.out.println("BadPaddingException Exception: " + badPaddingException);
        }catch (IllegalBlockSizeException illegalBlockSizeException){
            System.out.println("IllegalBlockSizeException Exception: " + illegalBlockSizeException);
        }catch (Exception ex){
            System.out.println("Exception: " + ex);
        }finally {
            return plainText;
        }
    }

    public static SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

}
