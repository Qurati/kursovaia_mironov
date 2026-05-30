package ru.qurati.gasproject.controller.pressuremeasurement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.qurati.gasproject.model.CompressorStation;
import ru.qurati.gasproject.model.PipeSegment;
import ru.qurati.gasproject.model.PressureMeasurement;
import ru.qurati.gasproject.service.CompressorStationService;
import ru.qurati.gasproject.service.PipeSegmentService;
import ru.qurati.gasproject.service.PressureMeasurementService;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AddEditPressureMeasurementDialog implements Initializable {
    @FXML
    private ComboBox<String> segmentComboBox;
    @FXML
    private ComboBox<String> stationComboBox;
    @FXML
    private TextField pressureField;
    @FXML
    private TextField temperatureField;
    @FXML
    private Button okButton;
    @FXML
    private Label errorLabel;

    private Stage dialogStage;
    private PressureMeasurement measurement;
    private ObservableList<String> segments;
    private ObservableList<String> stations;
    private Map<String, Integer> segmentIdMap;
    private Map<String, Integer> stationIdMap;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        segments = FXCollections.observableArrayList();
        stations = FXCollections.observableArrayList();
        segmentIdMap = new HashMap<>();
        stationIdMap = new HashMap<>();

        for (PipeSegment seg : new PipeSegmentService().findAll()) {
            String display = seg.getSegmentNumber() + " (ID:" + seg.getSegmentId() + ")";
            segments.add(display);
            segmentIdMap.put(display, seg.getSegmentId());
        }
        for (CompressorStation cs : new CompressorStationService().findAll()) {
            stations.add(cs.getName());
            stationIdMap.put(cs.getName(), cs.getStationId());
        }

        segmentComboBox.setItems(segments);
        stationComboBox.setItems(stations);
    }

    private void add() {
        try {
            String selectedSegment = segmentComboBox.getSelectionModel().getSelectedItem();
            String selectedStation = stationComboBox.getSelectionModel().getSelectedItem();

            if (selectedSegment == null) throw new IllegalArgumentException("Выберите сегмент трубы!");
            if (selectedStation == null) throw new IllegalArgumentException("Выберите станцию!");

            PressureMeasurement newMeasurement = new PressureMeasurement();
            newMeasurement.setSegmentId(segmentIdMap.get(selectedSegment));
            newMeasurement.setStationId(stationIdMap.get(selectedStation));
            newMeasurement.setPressure(pressureField.getText());
            newMeasurement.setTemperature(temperatureField.getText());
            newMeasurement.setMeasurementTime(LocalDateTime.now());

            new PressureMeasurementService().save(newMeasurement);
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    private void edit() {
        try {
            String selectedSegment = segmentComboBox.getSelectionModel().getSelectedItem();
            String selectedStation = stationComboBox.getSelectionModel().getSelectedItem();

            if (selectedSegment == null) throw new IllegalArgumentException("Выберите сегмент трубы!");
            if (selectedStation == null) throw new IllegalArgumentException("Выберите станцию!");

            measurement.setSegmentId(segmentIdMap.get(selectedSegment));
            measurement.setStationId(stationIdMap.get(selectedStation));
            measurement.setPressure(pressureField.getText());
            measurement.setTemperature(temperatureField.getText());

            new PressureMeasurementService().update(measurement);
            dialogStage.close();
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public void setAddDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        okButton.setOnAction(e -> add());
    }

    public void setEditDialogStage(Stage dialogStage, PressureMeasurement measurement) {
        this.measurement = measurement;
        this.dialogStage = dialogStage;

        for (Map.Entry<String, Integer> entry : segmentIdMap.entrySet()) {
            if (entry.getValue().equals(measurement.getSegmentId())) {
                segmentComboBox.getSelectionModel().select(entry.getKey());
                break;
            }
        }
        for (Map.Entry<String, Integer> entry : stationIdMap.entrySet()) {
            if (entry.getValue().equals(measurement.getStationId())) {
                stationComboBox.getSelectionModel().select(entry.getKey());
                break;
            }
        }
        pressureField.setText(measurement.getPressure() != null ? measurement.getPressure().toString() : "");
        temperatureField.setText(measurement.getTemperature() != null ? measurement.getTemperature().toString() : "");

        okButton.setOnAction(e -> edit());
    }
}