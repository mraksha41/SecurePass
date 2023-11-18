package com.securepass.SecurePass.repositories;

import com.securepass.SecurePass.domain.Credential;

import java.util.List;

public interface CredentialService {

    // Save operation
    Credential addCredential(Credential cred);

    // Read operation
    List<Credential> fetchCredentialList();

    // Update operation
    Credential updateCredential(Credential cred, int id);

    // Delete operation
    void deleteCredentialById(int id);

    public void checkPasswordStrength(String password);
}
