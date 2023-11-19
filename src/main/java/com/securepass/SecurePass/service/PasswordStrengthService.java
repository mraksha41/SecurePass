package com.securepass.SecurePass.service;

import com.securepass.SecurePass.util.ReturnCode;


public interface PasswordStrengthService {

    ReturnCode checkOldPassword(String password);

    ReturnCode checkPasswordStrength(String password);
}
