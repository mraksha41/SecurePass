package com.securepass.SecurePass.controller;

import com.securepass.SecurePass.domain.Credential;
import com.securepass.SecurePass.domain.StandardCredPreference;
import com.securepass.SecurePass.service.*;
import com.securepass.SecurePass.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
public class CredentialController {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private AESEncryptionService aesEncryptionService;
    @Autowired
    private PasswordStrengthService passwordStrengthService;
    @Autowired
    private PasswordGeneratorService passwordGeneratorService;

    @GetMapping("/credential/list")
    public Iterable<Credential> getCredentials() {
        return credentialService.fetchCredentialList();
    }

    @PostMapping("/credential/save")
    public Credential saveCredential( @RequestBody Credential credential) {
        if(credential.getId() !=  null){
            return credentialService.updateCredential(credential);
        }
        return credentialService.addCredential(credential);
    }

    @PostMapping("/credential/delete")
    public int deleteCredential(@RequestBody Integer id) {
        credentialService.deleteCredentialById(id);
        return 1;
    }

    @GetMapping("/credential/view")
    public Credential viewCredential(@PathVariable Integer id) {
        return credentialService.fetchCredentialById(id);
    }

    @PostMapping("/credential/checkOldPassword")
    public ReturnCode checkOldPassword(@RequestBody String password) {
        return passwordStrengthService.checkOldPassword(password);
    }

    @PostMapping("/credential/validate")
    public ReturnCode checkPasswordStrength(@RequestBody String password) {
        return passwordStrengthService.checkPasswordStrength(password);
    }

    @RequestMapping("credential/generatePassword")
    public String generatePassword(StandardCredPreference credPref) throws NoSuchAlgorithmException {
        credPref.setLength(12);
        credPref.setSymbols(true);
        credPref.setUppercase(true);
        credPref.setAmbiguousCharacters(true);
        credPref.setSimilarCharacters(true);
        return passwordGeneratorService.checkAlgorithm(credPref);
    }

}
