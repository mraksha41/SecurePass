package com.securepass.SecurePass.service;

import com.securepass.SecurePass.domain.Credential;

import java.util.List;

public interface CredentialService {

    // Save operation
    Credential addCredential(Credential cred);

    // Read operation
    List<Credential> fetchCredentialList();
    Credential fetchCredentialById(int id);

    // Update operation
    Credential updateCredential(Credential cred);

    // Delete operation
    void deleteCredentialById(int id);

}
