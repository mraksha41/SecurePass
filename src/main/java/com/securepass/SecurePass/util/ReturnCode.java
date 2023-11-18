package com.securepass.SecurePass.util;

public class ReturnCode {
    private boolean success;
    private String message;

    public ReturnCode(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess()
    {
        return this.success;
    }

    public void setSuccess()
    {
        this.success = success;
    }

    public String getMessage()
    {
        return this.message;
    }

    public void setMessage()
    {
        this.message = message;
    }
}