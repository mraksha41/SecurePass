package com.securepass.SecurePass.repositories;

import com.securepass.SecurePass.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findUserById(Integer id);
}
