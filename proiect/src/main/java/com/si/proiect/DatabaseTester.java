package com.si.proiect;

import com.si.proiect.dao.AlgorithmDAO;
import com.si.proiect.dao.FrameworkDAO;
import com.si.proiect.entity.Algorithm;
import com.si.proiect.entity.Framework;

import java.util.List;

public class DatabaseTester {

    public static void ruleazaTesteCRUD() {
        AlgorithmDAO algorithmDAO = new AlgorithmDAO();
        FrameworkDAO frameworkDAO = new FrameworkDAO();

        if (algorithmDAO.findAll().isEmpty()) {
            System.out.println("Baza de date e goala. Adaugam date de test...");

            Algorithm alg1 = new Algorithm();
            alg1.setName("AES-256");
            alg1.setType("Simetric");
            algorithmDAO.save(alg1);

            Algorithm alg2 = new Algorithm();
            alg2.setName("RSA-2048");
            alg2.setType("Asimetric");
            algorithmDAO.save(alg2);

            Framework fw = new Framework();
            fw.setName("OpenSSL");
            frameworkDAO.save(fw);
        }

        System.out.println("\nAlgoritmi gasiti in DB:");
        List<Algorithm> algoritmi = algorithmDAO.findAll();
        for (Algorithm a : algoritmi) {
            System.out.println(" - ID: " + a.getId() + " | Nume: " + a.getName() + " | Tip: " + a.getType());
        }

        System.out.println("\nFramework-uri gasite in DB:");
        List<Framework> frameworkuri = frameworkDAO.findAll();
        for (Framework f : frameworkuri) {
            System.out.println(" - ID: " + f.getId() + " | Nume: " + f.getName());
        }

    }
}