package com.securepass.SecurePass.service;

import com.securepass.SecurePass.repositories.CredentialRepository;
import com.securepass.SecurePass.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PasswordStrengthService {

    @Autowired
    private static CredentialRepository credentialRepository;

    public static ReturnCode checkOldPassword(String password){
        ReturnCode returnCode;
        if (credentialRepository.existsByPassword(password)) {
            returnCode = new ReturnCode(false, "Warning: Password already exists in the database.");
        }else{
            returnCode = new ReturnCode(true, "");
        }
        return returnCode;
    }

    public static ReturnCode checkPasswordStrength(String password){
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
                    message = "success";
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
