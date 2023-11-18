package com.securepass.SecurePass.controller;

import com.securepass.SecurePass.domain.Credential;
import com.securepass.SecurePass.repositories.CredentialRepository;
import com.securepass.SecurePass.service.AESEncryptionService;
import com.securepass.SecurePass.service.CredentialServiceImp;
import com.securepass.SecurePass.service.PasswordStrengthService;
import com.securepass.SecurePass.util.ReturnCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
public class CredentialController {

    @Autowired
    private CredentialRepository credentialRepository;
    private CredentialServiceImp credentialService;

    private AESEncryptionService aesEncryptionService;
    private PasswordStrengthService passwordStrengthService;

    @GetMapping("/credential/list")
    public Iterable<Credential> getCredentials() {
        return credentialRepository.findAll();
    }

    @PostMapping("/credential/save")
    public Credential saveCredential( @RequestBody Credential credential) {
        credential.setPassword(aesEncryptionService.getEncryptedPassword(credential.getPassword()));
        if(credential.getId() !=  null){
            Credential oldCred = credentialRepository.findById(credential.getId()).get();

            if(Objects.nonNull(credential.getName()) && !credential.getName().equalsIgnoreCase("")){
                oldCred.setName(credential.getName());
            }

            if(Objects.nonNull(credential.getUrl()) && !credential.getUrl().equalsIgnoreCase("")){
                oldCred.setUrl(credential.getUrl());
            }

            if(Objects.nonNull(credential.getUsername()) && !credential.getUsername().equalsIgnoreCase("")){
                oldCred.setUsername(credential.getUsername());
            }

            if(Objects.nonNull(credential.getPassword()) && !credential.getPassword().equalsIgnoreCase("")){
                oldCred.setPassword(credential.getPassword());
            }

            return credentialRepository.save(oldCred);
        }
        return credentialRepository.save(credential);
    }

    @PostMapping("/credential/delete")
    public int deleteCredential(@RequestBody Integer id) {
        credentialRepository.deleteById(id);
        return 1;
    }

    @PostMapping("/credential/view")
    public String viewCredential(@PathVariable Integer id) {
        Credential cred = findCredentialById(id);
        String decryptedPassword = aesEncryptionService.getDecryptedPassword(cred.getPassword());
        if(decryptedPassword != ""){
            System.out.println("Password: " + decryptedPassword);
        }else{
            System.out.println("Error Occurred while decrypting the password");
        }
        return decryptedPassword;
    }

    @GetMapping("/credential/find/{id}")
    public Credential findCredentialById(@PathVariable Integer id) {
        return credentialRepository.findCredentialById(id);
    }

    @PostMapping("/credential/checkOldPassword")
    public ReturnCode checkOldPassword(@RequestBody String password) {
        System.out.println("checkOldPassword" + password);
        ReturnCode result = passwordStrengthService.checkOldPassword(password);
        System.out.println(result);
        return result;
    }

    @PostMapping("/credential/evaluate")
    public ReturnCode checkPasswordStrength(@RequestBody String password) {
        System.out.println("password" + password);
        ReturnCode result = passwordStrengthService.checkPasswordStrength(password);
        System.out.println(result);
        return result;
    }

}
