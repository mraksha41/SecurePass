package com.securepass.SecurePass.service;

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

import com.securepass.SecurePass.util.IvWithSpec;
import org.springframework.stereotype.Service;

@Service
public class AESEncryptionServiceImp implements AESEncryptionService{

    @Override
    public String getEncryptedPassword(String password) {
        String cipherText = "";
        try{
            SecretKey key = getKey();
            IvWithSpec ivWithSpec = generateIv();
            String algorithm = "AES/CBC/PKCS5Padding";
            String ivBase64 = Base64.getEncoder().encodeToString(ivWithSpec.getIv());
            cipherText = ivBase64 +"!-=£!" + encrypt(algorithm, password, key, ivWithSpec.getIvParameterSpec());
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

    @Override
    public String getDecryptedPassword(String cipherText) {
        String plainText = "";
        try{
            SecretKey key = getKey();
            String[] cipherTextParts = cipherText.split("!-=£!");
            String ivBase64 = cipherTextParts[0];
            byte[] iv = Base64.getDecoder().decode(ivBase64);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            String algorithm = "AES/CBC/PKCS5Padding";
            plainText = decrypt(algorithm, cipherTextParts[1], key, ivParameterSpec);
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

    @Override
    public SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        return keyGenerator.generateKey();
    }

    @Override
    public SecretKey getKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = "feacbc02a3a697b0";
        String password = "root";
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    @Override
    public IvWithSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvWithSpec( new IvParameterSpec(iv), iv);
    }

    @Override
    public String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    @Override
    public String decrypt(String algorithm, String cipherText, SecretKey key,
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
