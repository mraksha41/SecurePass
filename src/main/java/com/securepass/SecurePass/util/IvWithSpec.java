package com.securepass.SecurePass.util;

import javax.crypto.spec.IvParameterSpec;

public class IvWithSpec {
    private final IvParameterSpec ivParameterSpec;
    private final byte[] iv;

    public IvWithSpec(IvParameterSpec ivParameterSpec, byte[] iv) {
        this.ivParameterSpec = ivParameterSpec;
        this.iv = iv;
    }

    public IvParameterSpec getIvParameterSpec() {
        return ivParameterSpec;
    }

    public byte[] getIv() {
        return iv;
    }
}