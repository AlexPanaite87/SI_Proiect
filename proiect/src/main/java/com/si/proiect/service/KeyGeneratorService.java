package com.si.proiect.service;

import java.security.SecureRandom;
import java.util.HexFormat;

public class KeyGeneratorService {

    public static String generateSecureKey() {
        SecureRandom random = new SecureRandom();

        byte[] keyBytes = new byte[16];
        random.nextBytes(keyBytes);
        return HexFormat.of().formatHex(keyBytes);
    }
}