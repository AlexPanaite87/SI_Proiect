package com.si.proiect.service;

import java.util.ArrayList;
import java.util.List;

public class OpenSSLService {
    public static long[] encrypt(String inputFile, String outputFile, String key, String algorithmName) throws Exception {
        System.gc();
        long memBefore = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.currentTimeMillis();
        List<String> command = new ArrayList<>();
        String upperName = algorithmName.toUpperCase();

        if (upperName.equals("RSA")) {
            command.add("openssl"); command.add("pkeyutl");
            command.add("-encrypt");
            command.add("-pubin"); command.add("-inkey"); command.add("public.pem");
            command.add("-in"); command.add(inputFile);
            command.add("-out"); command.add(outputFile);
        } else {
            command.add("openssl"); command.add("enc");
            if (upperName.contains("AES")) command.add("-aes-256-cbc");
            else if (upperName.contains("DES3")) command.add("-des3");
            else if (upperName.contains("DES")) command.add("-des");
            else if (upperName.contains("BLOWFISH")) command.add("-bf-cbc");
            else if (upperName.contains("CAST")) command.add("-cast5-cbc");
            else if (upperName.contains("CHACHA")) command.add("-chacha20");
            else throw new UnsupportedOperationException("Algoritm nesuportat: " + algorithmName);

            command.add("-in"); command.add(inputFile);
            command.add("-out"); command.add(outputFile);
            command.add("-pass"); command.add("pass:" + key);
        }

        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();
        process.waitFor();
        long endTime = System.currentTimeMillis();
        long memAfter = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return new long[] { (endTime - startTime), (memAfter - memBefore) };
    }
}