package ru.qurati.gasproject.controller.compressorstation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.qurati.gasproject.model.CompressorStation;
import ru.qurati.gasproject.service.CompressorStationService;

public class AddEditCompressorStationDialog {
    @FXML
    private TextField nameField;
    @FXML
    private TextField locationField;
    @FXML
    private TextField supervisorField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button okButton;
    @FXML
    private Label errorLabel;

    private Stage dialogStage;
    private CompressorStation station;

    private void add() {
        try {
            CompressorStation newStation = new CompressorStation();
            newStation.setName(nameField.getText());
            newStation.setLocation(locationField.getText());
            newStation.setShiftSupervisor(supervisorField.getText());
            newStation.setPhone(phoneField.getText());
            new CompressorStationService().save(newStation);
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void edit() {
        try {
            station.setName(nameField.getText());
            station.setLocation(locationField.getText());
            station.setShiftSupervisor(supervisorField.getText());
            station.setPhone(phoneField.getText());
            new CompressorStationService().update(station);
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public void setAddDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        okButton.setOnAction(e -> add());
    }

    public void setEditDialogStage(Stage dialogStage, CompressorStation station) {
        this.station = station;
        this.dialogStage = dialogStage;
        nameField.setText(station.getName());
        locationField.setText(station.getLocation());
        supervisorField.setText(station.getShiftSupervisor());
        phoneField.setText(station.getPhone());
        okButton.setOnAction(e -> edit());
    }
}