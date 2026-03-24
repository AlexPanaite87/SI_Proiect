package com.si.proiect;

import com.si.proiect.dao.*;
import com.si.proiect.entity.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;
import java.util.List;

public class HelloController {
    @FXML private TextField algNameField, algTypeField, execTimeField;
    @FXML private ComboBox<Algorithm> algComboBox;
    @FXML private Label statusLabel;

    private AlgorithmDAO algDAO = new AlgorithmDAO();
    private ResultDAO resDAO = new ResultDAO();
    private FrameworkDAO fwDAO = new FrameworkDAO();
    private FileEntityDAO fileDAO = new FileEntityDAO();

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
                Algorithm algoritmSelectat = (Algorithm) algComboBox.getSelectionModel().getSelectedItem();

                if (algoritmSelectat == null) {
                    statusLabel.setText("Eroare! Te rog selecteaza un algoritm.");
                    return;
                }
                String dataAzi = java.time.LocalDateTime.now().toString();

                FrameworkDAO fwDao = new FrameworkDAO();
                List<Framework> frameworkuri = fwDao.findAll();
                if (frameworkuri.isEmpty()) {
                    statusLabel.setText("Eroare! Nu ai niciun Framework in DB!");
                    return;
                }
                Framework frameworkDummy = frameworkuri.get(0);

                FileEntityDAO fileDao = new FileEntityDAO();
                List<FileEntity> fisiere = fileDao.findAll();
                FileEntity fisierDummy;

                if (fisiere.isEmpty()) {
                    EncryptionKeyDAO keyDao = new EncryptionKeyDAO();
                    EncryptionKey cheieDummy = new EncryptionKey();
                    cheieDummy.setAlgorithm(algoritmSelectat);
                    cheieDummy.setKeyValue("cheie-test-123");
                    cheieDummy.setCreationDate(dataAzi);
                    keyDao.save(cheieDummy);

                    fisierDummy = new FileEntity();
                    fisierDummy.setName("fisier_test_dummy.txt");
                    fisierDummy.setFilePath("C:/dummy/test.txt");
                    fisierDummy.setState("TEORETIC");
                    fisierDummy.setKey(cheieDummy);
                    fileDao.save(fisierDummy);
                } else {
                    fisierDummy = fisiere.get(0);
                }

                Result rezultatNou = new Result();
                rezultatNou.setExecutionTime(timp);
                rezultatNou.setAlgorithm(algoritmSelectat);
                rezultatNou.setTestDate(dataAzi);
                rezultatNou.setFramework(frameworkDummy);
                rezultatNou.setFile(fisierDummy);

                ResultDAO resultDao = new ResultDAO();
                resultDao.save(rezultatNou);

                statusLabel.setText("Succes! Rezultatul a fost salvat in DB.");

            } catch (Exception e) {
                statusLabel.setText("Eroare! Verifica consola (System.err).");
                e.printStackTrace();
            }
        }
}