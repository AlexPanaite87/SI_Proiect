package com.si.proiect.service;

import java.security.SecureRandom;

public class KeyGeneratorService {

    public static String generateSecureKey() {
        SecureRandom random = new SecureRandom();

        byte[] keyBytes = new byte[16];
        random.nextBytes(keyBytes);

        StringBuilder hexString = new StringBuilder();
        for (byte b : keyBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }
}