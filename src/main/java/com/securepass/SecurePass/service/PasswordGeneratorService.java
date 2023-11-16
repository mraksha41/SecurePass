package com.securepass.SecurePass.service;

import com.securepass.SecurePass.domain.StandardCredPreference;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public interface PasswordGeneratorService {
    String algorithm(List<String> symbols, int length) throws NoSuchAlgorithmException;

    //new
    String checkAlgorithm(StandardCredPreference standardCredPreference) throws NoSuchAlgorithmException;


}
