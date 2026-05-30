package ru.qurati.gasproject.controller.pipesegment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.qurati.gasproject.model.PipeClass;
import ru.qurati.gasproject.model.PipeSegment;
import ru.qurati.gasproject.service.PipeClassService;
import ru.qurati.gasproject.service.PipeSegmentService;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddEditPipeSegmentDialog implements Initializable {
    @FXML
    private ComboBox<String> classComboBox;
    @FXML
    private ComboBox<String> statusComboBox;
    @FXML
    private TextField segmentNumberField;
    @FXML
    private TextField inspectionDateField;
    @FXML
    private TextField lengthField;
    @FXML
    private Button okButton;
    @FXML
    private Label errorLabel;

    private Stage dialogStage;
    private PipeSegment segment;
    private ObservableList<String> classes;
    private Map<String, Integer> classIdMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        classes = FXCollections.observableArrayList();
        classIdMap = new HashMap<>();

        for (PipeClass pc : new PipeClassService().findAll()) {
            classes.add(pc.getName());
            classIdMap.put(pc.getName(), pc.getPipeClassId());
        }

        classComboBox.setItems(classes);
        statusComboBox.setItems(FXCollections.observableArrayList("Рабочий", "Ремонт", "Резерв"));
    }

    private void add() {
        try {
            String selectedClass = classComboBox.getSelectionModel().getSelectedItem();
            String status = statusComboBox.getSelectionModel().getSelectedItem();

            if (selectedClass == null) throw new IllegalArgumentException("Выберите класс сегмента!");
            if (status == null) throw new IllegalArgumentException("Выберите статус!");

            PipeSegment newSegment = new PipeSegment();
            newSegment.setPipeClassId(classIdMap.get(selectedClass));
            newSegment.setSegmentNumber(segmentNumberField.getText());
            newSegment.setStatus(status);
            newSegment.setLastInspectionDate(inspectionDateField.getText());
            newSegment.setLengthKm(lengthField.getText());

            new PipeSegmentService().save(newSegment);
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void edit() {
        try {
            String selectedClass = classComboBox.getSelectionModel().getSelectedItem();
            String status = statusComboBox.getSelectionModel().getSelectedItem();

            if (selectedClass == null) throw new IllegalArgumentException("Выберите класс сегмента!");
            if (status == null) throw new IllegalArgumentException("Выберите статус!");

            segment.setPipeClassId(classIdMap.get(selectedClass));
            segment.setSegmentNumber(segmentNumberField.getText());
            segment.setStatus(status);
            segment.setLastInspectionDate(inspectionDateField.getText());
            segment.setLengthKm(lengthField.getText());

            new PipeSegmentService().update(segment);
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public void setAddDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        okButton.setOnAction(e -> add());
    }

    public void setEditDialogStage(Stage dialogStage, PipeSegment segment) {
        this.segment = segment;
        this.dialogStage = dialogStage;

        for (Map.Entry<String, Integer> entry : classIdMap.entrySet()) {
            if (entry.getValue().equals(segment.getPipeClassId())) {
                classComboBox.getSelectionModel().select(entry.getKey());
                break;
            }
        }
        statusComboBox.getSelectionModel().select(segment.getStatus());
        segmentNumberField.setText(segment.getSegmentNumber());
        inspectionDateField.setText(segment.getLastInspectionDate() != null ? segment.getLastInspectionDate().toString() : "");
        lengthField.setText(segment.getLengthKm() != null ? segment.getLengthKm().toString() : "");

        okButton.setOnAction(e -> edit());
    }
}