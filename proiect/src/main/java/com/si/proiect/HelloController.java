package com.si.proiect;

import com.si.proiect.dao.*;
import com.si.proiect.entity.*;
import com.si.proiect.service.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static java.nio.file.Files.writeString;

public class HelloController {
    @FXML private TextField algNameField, algTypeField, execTimeField;
    @FXML private ComboBox<Algorithm> algComboBox;
    @FXML private Label statusLabel;

    private AlgorithmDAO algDAO = new AlgorithmDAO();
    private ResultDAO resDAO = new ResultDAO();
    private FrameworkDAO fwDAO = new FrameworkDAO();
    private FileEntityDAO fileDAO = new FileEntityDAO();
    private EncryptionKeyDAO keyDAO = new EncryptionKeyDAO();

    @FXML
    public void initialize() {
        algComboBox.setItems(FXCollections.observableArrayList(algDAO.findAll()));
        algComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Algorithm a, boolean bln) {
                super.updateItem(a, bln);
                setText(a != null ? a.getName() : null);
            }
        });
        algComboBox.setButtonCell(algComboBox.getCellFactory().call(null));
    }

    @FXML
    protected void onAddAlgorithmClick() {
        Algorithm a = new Algorithm();
        a.setName(algNameField.getText());
        a.setType(algTypeField.getText());

        algDAO.save(a);
        statusLabel.setText("Algoritm salvat!");
        initialize();
    }

    @FXML
    protected void onAddResultClick() {
        try {
            double timp = Double.parseDouble(execTimeField.getText());
            Algorithm selectedAlgorithm = algComboBox.getSelectionModel().getSelectedItem();

            if (selectedAlgorithm == null) {
                statusLabel.setText("Eroare! Te rog selecteaza un algoritm.");
                return;
            }

            String todayDate = LocalDateTime.now().toString();
            List<Framework> frameworks = fwDAO.findAll();

            if (frameworks.isEmpty()) {
                statusLabel.setText("Eroare! Nu ai niciun Framework in DB!");
                return;
            }

            Framework frameworkDummy = frameworks.get(0);
            List<FileEntity> files = fileDAO.findAll();
            FileEntity dummyFile;

            if (files.isEmpty()) {
                EncryptionKey dummyKey = new EncryptionKey();
                dummyKey.setAlgorithm(selectedAlgorithm);
                dummyKey.setKeyValue("cheie-test-123");
                dummyKey.setCreationDate(todayDate);
                keyDAO.save(dummyKey);

                dummyFile = new FileEntity();
                dummyFile.setName("fisier_test_dummy.txt");
                dummyFile.setFilePath("C:/dummy/test.txt");
                dummyFile.setState("TEORETIC");
                dummyFile.setKey(dummyKey);
                fileDAO.save(dummyFile);
            } else {
                dummyFile = files.get(0);
            }

            Result newResult = new Result();
            newResult.setExecutionTime(timp);
            newResult.setAlgorithm(selectedAlgorithm);
            newResult.setTestDate(todayDate);
            newResult.setFramework(frameworkDummy);
            newResult.setFile(dummyFile);

            resDAO.save(newResult);
            statusLabel.setText("Succes! Rezultatul a fost salvat in DB.");

        } catch (Exception e) {
            statusLabel.setText("Eroare! Verifica consola!");
            e.printStackTrace();
        }
    }

    @FXML
    protected void onEncryptButtonClick() {
        try {
            Algorithm selectedAlgorithm = algComboBox.getSelectionModel().getSelectedItem();
            if (selectedAlgorithm == null) {
                statusLabel.setText("Selectează un algoritm pentru criptare!");
                return;
            }

            String inputFile = "test_input.txt";
            String outputFile = "test_output.enc";
            String databaseKey = KeyGeneratorService.generateSecureKey();

            writeString(Paths.get(inputFile), "The quick brown fox jumps over the lazy dog.");

            long execTimeOpenSSL = OpenSSLService.encrypt(inputFile, outputFile, databaseKey, selectedAlgorithm.getName());
            String hashFile = HashUtils.calculateSHA256(outputFile);

            String outputfileJCA = "test_output_jca.enc";
            long execTimeJCA = JavaCryptoService.encrypt(inputFile, outputfileJCA, databaseKey, selectedAlgorithm.getName());

            String todayDate = LocalDateTime.now().toString();

            EncryptionKey key = new EncryptionKey();
            key.setAlgorithm(selectedAlgorithm);
            key.setKeyValue(databaseKey);
            key.setCreationDate(todayDate);
            keyDAO.save(key);

            FileEntity file = new FileEntity();
            file.setName(outputFile);
            file.setFilePath(Paths.get(outputFile).toAbsolutePath().toString());
            file.setState("CRIPTAT");
            file.setKey(key);
            file.setFileHash(hashFile);
            fileDAO.save(file);

            List<Framework> fwList = fwDAO.findAll();
            if (!fwList.isEmpty()) {
                Framework openSSLFramework = fwList.get(0);
                Result result = new Result();
                result.setExecutionTime((double) execTimeOpenSSL);
                result.setAlgorithm(selectedAlgorithm);
                result.setTestDate(todayDate);
                result.setFramework(openSSLFramework);
                result.setFile(file);
                resDAO.save(result);
            }

            execTimeField.setText(String.valueOf(execTimeOpenSSL));
            statusLabel.setText("Criptat si Salvat in DB! O-SSL: " + execTimeOpenSSL + "ms | Hash: " + hashFile.substring(0, 10) + "...");

        } catch (Exception e) {
            e.printStackTrace();
            statusLabel.setText("Eroare la criptare: " + e.getMessage());
        }
    }

    @FXML
    protected void onDebugKeysClick() {
        List<EncryptionKey> keys = keyDAO.findAll();
        StringBuilder sb = new StringBuilder();

        for (EncryptionKey k : keys) {
            sb.append("ID: ").append(k.getId())
                    .append(" | Algoritm: ").append(k.getAlgorithm().getName())
                    .append(" | Cheie: ").append(k.getKeyValue())
                    .append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mod Debug");
        alert.setHeaderText("Cheile salvate in Baza de Date:");
        alert.setContentText(sb.toString().isEmpty() ? "Nu exista chei!" : sb.toString());
        alert.showAndWait();
    }
}