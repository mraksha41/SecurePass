package com.securepass.SecurePass.repositories;

import com.securepass.SecurePass.domain.Credential;
import org.springframework.data.repository.CrudRepository;

public interface CredentialRepository extends CrudRepository<Credential, Integer> {
    Credential findCredentialById(Integer id);

    boolean existsByPassword(String password);
}
