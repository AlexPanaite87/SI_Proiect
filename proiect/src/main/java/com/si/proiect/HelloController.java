package com.si.proiect;

import com.si.proiect.dao.*;
import com.si.proiect.entity.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;

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
            Result r = new Result();
            r.setAlgorithm(algComboBox.getValue());
            r.setExecutionTime(Double.parseDouble(execTimeField.getText()));
            r.setTestDate(LocalDateTime.now().toString());

            r.setFramework(fwDAO.findAll().get(0));
            r.setFile(fileDAO.findAll().get(0));

            resDAO.save(r);
            statusLabel.setText("Rezultat salvat!");
        } catch (Exception e) {
            statusLabel.setText("Eroare! Verifica dacă ai date in baza de date.");
        }
    }
}