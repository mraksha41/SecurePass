package com.securepass.SecurePass.service;

import com.securepass.SecurePass.domain.Credential;
import com.securepass.SecurePass.repositories.CredentialRepository;
import com.securepass.SecurePass.repositories.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CredentialServiceImp implements CredentialService {

    private CredentialService credentialService;
    private CredentialRepository credentialRepository;

    public void checkPasswordStrength(String password){

    }

    // Save operation
    @Override
    public Credential addCredential(Credential Credential)
    {
        return credentialRepository.save(Credential);
    }

    // Read operation
    @Override public List<Credential> fetchCredentialList()
    {
        return (List<Credential>) credentialRepository.findAll();
    }

    // Update operation
    @Override
    public Credential updateCredential(Credential cred, int id)
    {
        Credential oldCred = credentialRepository.findById(id).get();

        if(Objects.nonNull(cred.getName()) && !cred.getName().equalsIgnoreCase("")){
            oldCred.setName(cred.getName());
        }

        if(Objects.nonNull(cred.getUrl()) && !cred.getUrl().equalsIgnoreCase("")){
            oldCred.setUrl(cred.getUrl());
        }

        if(Objects.nonNull(cred.getUsername()) && !cred.getUsername().equalsIgnoreCase("")){
            oldCred.setUsername(cred.getUsername());
        }

        if(Objects.nonNull(cred.getPassword()) && !cred.getPassword().equalsIgnoreCase("")){
            oldCred.setPassword(cred.getPassword());
        }

        return credentialRepository.save(oldCred);
    }

    // Delete operation
    @Override
    public void deleteCredentialById(int id)
    {
        credentialRepository.deleteById(id);
    }

}
