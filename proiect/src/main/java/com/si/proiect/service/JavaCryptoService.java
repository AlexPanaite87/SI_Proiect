package com.si.proiect.service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class JavaCryptoService {
    public static long[] encrypt(String inputFile, String outputFile, String keyString, String algorithmName) throws Exception {
        if (algorithmName.toUpperCase().contains("RSA")) {
            throw new IllegalArgumentException("Nu exista implementare pentru RSA in framework ul JCA.");
        }
        System.gc();
        long startTime = System.currentTimeMillis();
        long memBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        String upperName = algorithmName.toUpperCase();

        String jcaAlgo = "AES";
        String transform = "AES/ECB/PKCS5Padding";
        int keyLen = 32;

        if (upperName.contains("DES3")) { jcaAlgo = "DESede"; transform = "DESede/ECB/PKCS5Padding"; keyLen = 24; }
        else if (upperName.contains("DES")) { jcaAlgo = "DES"; transform = "DES/ECB/PKCS5Padding"; keyLen = 8; }
        else if (upperName.contains("BLOWFISH")) { jcaAlgo = "Blowfish"; transform = "Blowfish/ECB/PKCS5Padding"; keyLen = 16; }
        else if (upperName.contains("CHACHA")) { jcaAlgo = "ChaCha20"; transform = "ChaCha20"; keyLen = 32; }

        byte[] keyBytes = Arrays.copyOf(keyString.getBytes("UTF-8"), keyLen);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, jcaAlgo);
        Cipher cipher = Cipher.getInstance(transform);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] inputBytes = Files.readAllBytes(Paths.get(inputFile));
        Files.write(Paths.get(outputFile), cipher.doFinal(inputBytes));

        long endTime = System.currentTimeMillis();
        long memAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        return new long[] { (endTime - startTime), (memAfter - memBefore) };
    }
}