package com.securepass.SecurePass.service;

import com.securepass.SecurePass.util.IvWithSpec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface AESEncryptionService {
    String getEncryptedPassword(String password);

    String getDecryptedPassword(String cipherText);

    SecretKey generateKey(int n) throws NoSuchAlgorithmException;

    SecretKey getKey() throws NoSuchAlgorithmException, InvalidKeySpecException;
    IvWithSpec generateIv();

    String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException ;

    String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException ;

}
