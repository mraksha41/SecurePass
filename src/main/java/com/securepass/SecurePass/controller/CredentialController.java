package com.securepass.SecurePass.controller;

import com.securepass.SecurePass.domain.Credential;
import com.securepass.SecurePass.repositories.CredentialRepository;
import com.securepass.SecurePass.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CredentialController {

    @Autowired
    private CredentialRepository credentialRepository;
    private final CredentialService credentialService;

    @Autowired
    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @PostMapping("/credential/add")
    public void addCredential() {

    }

    @PostMapping("/credential/update")
    public void updateCredential() {
    }

    @PostMapping("/credential/save")
    public String saveCredential(@RequestParam String title, @RequestParam String url, @RequestParam String username, @RequestParam String password) {
        Credential credential = new Credential();
        credential.setTitle(title);
        credential.setUrl(url);
        credential.setUsername(username);
        credential.setPassword(password);
        credentialRepository.save(credential);
        return "Added new credential to repo!";
    }

    @PostMapping("/credential/delete")
    public void deleteCredential(Integer id) {
        credentialRepository.deleteById(id);
        System.out.println("Credential " + id + " deleted.");
    }

    @GetMapping("/credential/list")
    public Iterable<Credential> getCredentials() {
        return credentialRepository.findAll();
    }

    @GetMapping("/credential/find/{id}")
    public Credential findCredentialById(@PathVariable Integer id) {
        return credentialRepository.findCredentialById(id);
    }

    @PostMapping("/credential/evaluate")
    public String checkPasswordStrength(@RequestParam String password) {
        credentialService.checkPasswordStrength(password);
        return "Checked Password";
    }

}
