package com.securepass.SecurePass.service;

import com.securepass.SecurePass.domain.Credential;
import com.securepass.SecurePass.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CredentialServiceImp implements CredentialService {

    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private AESEncryptionServiceImp aesEncryptionService;

    // Save operation
    @Override
    public Credential addCredential(Credential credential)
    {
        credential.setPassword(aesEncryptionService.getEncryptedPassword(credential.getPassword()));
        return credentialRepository.save(credential);
    }

    // Read operation
    @Override public List<Credential> fetchCredentialList()
    {
        Iterable<Credential> credentialList = credentialRepository.findAll();
        for (Credential credential : credentialList) {
            credential.setPassword(aesEncryptionService.getDecryptedPassword(credential.getPassword()));
        }
        return (List<Credential>) credentialRepository.findAll();
    }

    @Override public Credential fetchCredentialById(int id){
        Credential credential = credentialRepository.findCredentialById(id);
        credential.setPassword(aesEncryptionService.getDecryptedPassword(credential.getPassword()));
        return credential;
    }

    // Update operation
    @Override
    public Credential updateCredential(Credential cred)
    {
        Credential oldCred = credentialRepository.findById(cred.getId()).get();

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
            oldCred.setPassword(aesEncryptionService.getEncryptedPassword(cred.getPassword()));
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
