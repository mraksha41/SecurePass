package com.securepass.SecurePass.service;

import com.securepass.SecurePass.domain.Credential;
import com.securepass.SecurePass.repositories.CredentialRepository;
import com.securepass.SecurePass.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordStrengthServiceImp implements PasswordStrengthService{
    @Autowired
    private CredentialRepository credentialRepository;

    @Autowired
    private AESEncryptionService aesEncryptionService;

    public ReturnCode checkOldPassword(String password){
        Iterable<Credential> credentials = credentialRepository.findAll();
        for(Credential credential: credentials){
            if(password.equals(aesEncryptionService.getDecryptedPassword(credential.getPassword()))){
                return new ReturnCode(false, "Warning: Password already exists in the database.");
            }
        }
        return  new ReturnCode(true, "");
    }

    public ReturnCode checkPasswordStrength(String password){
        String message = "";
        boolean success = false;
        if(password.matches("(\\s)*")){
            message = "EMPTY SPACES";
            success = false;
        }
        else if(password.matches("(.)*(\\s)+(.)*")){
            message = "Whitespace not allowed";
            success = false;
        }
        else if(password.length()<8){
            message = "WEAK! Add more characters.";
            success = false;
        }
        else if(password.length()>30){
            message = "Password too long. Reduce the characters to 30";
            success = false;
        }
        else{
            boolean hasNumber = password.matches("(.)*(\\d)(.)*");
            if(!hasNumber){
                message = "WEAK! Add any digit";
                success = false;
            }
            else{
                boolean hasSymbol = password.matches("(.)*[\\*\\!\\@\\#\\$\\%\\^\\&\\_\\-\\+\\=\\.\\'\\~\\,\\(\\)\\:\\;\\<\\>\\[\\]\\|\\}\\{\\/]+(.)*");
                if(hasSymbol){
                    message = "";
                    success = true;
                }
                else{
                    message = "WEAK! Add any symbol";
                    success = false;
                }
            }
        }
        return new ReturnCode(success, message);
    }
}
