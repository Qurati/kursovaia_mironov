package ru.qurati.gasproject.controller.pipeclass;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.qurati.gasproject.model.PipeClass;
import ru.qurati.gasproject.service.PipeClassService;

public class AddEditPipeClassDialog {
    @FXML
    private TextField nameField;
    @FXML
    private TextField diameterField;
    @FXML
    private TextField pressureField;
    @FXML
    private TextField materialField;
    @FXML
    private TextField yearField;
    @FXML
    private Button okButton;
    @FXML
    private Label errorLabel;

    private Stage dialogStage;
    private PipeClass pipeClass;

    private void add() {
        try {
            PipeClass newClass = new PipeClass();
            newClass.setName(nameField.getText());
            newClass.setDiameter(diameterField.getText());
            newClass.setWorkingPressure(pressureField.getText());
            newClass.setMaterial(materialField.getText());
            newClass.setLayingYear(yearField.getText());
            new PipeClassService().save(newClass);
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void edit() {
        try {
            pipeClass.setName(nameField.getText());
            pipeClass.setDiameter(diameterField.getText());
            pipeClass.setWorkingPressure(pressureField.getText());
            pipeClass.setMaterial(materialField.getText());
            pipeClass.setLayingYear(yearField.getText());
            new PipeClassService().update(pipeClass);
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public void setAddDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        okButton.setOnAction(e -> add());
    }

    public void setEditDialogStage(Stage dialogStage, PipeClass pipeClass) {
        this.pipeClass = pipeClass;
        this.dialogStage = dialogStage;
        nameField.setText(pipeClass.getName());
        diameterField.setText(pipeClass.getDiameter() != null ? pipeClass.getDiameter().toString() : "");
        pressureField.setText(pipeClass.getWorkingPressure() != null ? pipeClass.getWorkingPressure().toString() : "");
        materialField.setText(pipeClass.getMaterial());
        yearField.setText(pipeClass.getLayingYear() != null ? pipeClass.getLayingYear().toString() : "");
        okButton.setOnAction(e -> edit());
    }
}