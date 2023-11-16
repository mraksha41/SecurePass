package com.securepass.SecurePass.service;

import com.securepass.SecurePass.domain.StandardCredPreference;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class GenericPasswordGeneratorService implements PasswordGeneratorService{
    @Override
    public String algorithm(List<String> symbols, int length) throws NoSuchAlgorithmException {
        Random random = SecureRandom.getInstanceStrong();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int indexRandom = random.nextInt(symbols.size());
            sb.append(symbols.get(indexRandom));
        }
        String password = sb.toString();
        return password;
    }

    @Override
    public String checkAlgorithm(StandardCredPreference credPref) throws NoSuchAlgorithmException {
        int length = credPref.getLength();
        List<String> symbols = new ArrayList<String>(
                Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h",
                        "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));

        if (Boolean.TRUE.equals(credPref.getSymbols())) {
            List<String> newSymbols = new ArrayList<String>(Arrays.asList("@", "#", "$", "%"));
            symbols.addAll(newSymbols);
        }
        if (Boolean.TRUE.equals(credPref.getUppercase())) {
            List<String> newSymbols = new ArrayList<String>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I",
                    "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));
            symbols.addAll(newSymbols);
        }
        if (Boolean.TRUE.equals(credPref.getAmbiguousCharacters())) {
            List<String> newSymbols = new ArrayList<String>(Arrays.asList("{", "}", "[", "]", "(", ")", "/", "\\", "\'",
                    "\"", "'", "~", ",", ";", ":", ".", "<", ">"));
            symbols.addAll(newSymbols);
        }
        if (Boolean.TRUE.equals(credPref.getSimilarCharacters())) {
            List<String> newSymbols = new ArrayList<String>(Arrays.asList("i", "l", "1", "L", "o", "0", "O"));
            symbols.removeAll(newSymbols);
        }
        return algorithm(symbols, length);
    }
}
