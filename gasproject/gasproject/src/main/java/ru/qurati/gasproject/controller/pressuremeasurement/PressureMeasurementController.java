package ru.qurati.gasproject.controller.pressuremeasurement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.qurati.gasproject.GasPipelineApp;
import ru.qurati.gasproject.model.CompressorStation;
import ru.qurati.gasproject.model.PipeSegment;
import ru.qurati.gasproject.model.PressureMeasurement;
import ru.qurati.gasproject.service.CompressorStationService;
import ru.qurati.gasproject.service.PipeSegmentService;
import ru.qurati.gasproject.service.PressureMeasurementService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PressureMeasurementController {
    private List<PressureMeasurement> measurements;
    private ObservableList<PressureMeasurementTableItem> measurementsObservable;
    private Map<Integer, String> segmentMap;
    private Map<Integer, String> stationMap;

    @FXML
    private TableView<PressureMeasurementTableItem> measurementsTable;
    @FXML
    private TableColumn<PressureMeasurementTableItem, String> segmentNumberColumn;
    @FXML
    private TableColumn<PressureMeasurementTableItem, String> stationNameColumn;
    @FXML
    private TableColumn<PressureMeasurementTableItem, String> timeColumn;
    @FXML
    private TableColumn<PressureMeasurementTableItem, String> pressureColumn;
    @FXML
    private TableColumn<PressureMeasurementTableItem, String> temperatureColumn;

    @FXML
    void btnAddMeasurement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(GasPipelineApp.class.getResource("add-edit-pressure-measurement-dialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(GasPipelineApp.primaryStage);
            dialogStage.setMinWidth(450);
            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle("Добавить замер давления");
            AddEditPressureMeasurementDialog controller = loader.getController();
            controller.setAddDialogStage(dialogStage);
            dialogStage.showAndWait();
            updateList();
        } catch (IOException e) {
            System.out.println("Ошибка открытия окна: " + e.getMessage());
        }
    }

    @FXML
    void btnEditMeasurement(ActionEvent event) {
        PressureMeasurementTableItem currentItem = measurementsTable.getSelectionModel().getSelectedItem();
        if (currentItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader(GasPipelineApp.class.getResource("add-edit-pressure-measurement-dialog.fxml"));
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(GasPipelineApp.primaryStage);
                dialogStage.setMinWidth(450);
                dialogStage.setScene(new Scene(loader.load()));
                dialogStage.setTitle("Редактировать замер");
                AddEditPressureMeasurementDialog controller = loader.getController();
                controller.setEditDialogStage(dialogStage, currentItem.getMeasurement());
                dialogStage.showAndWait();
                updateList();
            } catch (IOException e) {
                System.out.println("Ошибка открытия окна: " + e.getMessage());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setContentText("Выберите запись в таблице для редактирования");
            alert.showAndWait();
        }
    }

    @FXML
    void btnDeleteMeasurement(ActionEvent event) {
        PressureMeasurementTableItem currentItem = measurementsTable.getSelectionModel().getSelectedItem();
        if (currentItem != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Удаление записи");
            alert.setContentText("Вы действительно хотите удалить замер от " + currentItem.getMeasurementTime() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new PressureMeasurementService().delete(currentItem.getMeasurement());
                measurementsTable.getItems().remove(currentItem);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Предупреждение");
            alert.setContentText("Выберите запись в таблице для удаления");
            alert.showAndWait();
        }
    }

    @FXML
    void btnUpdateMeasurements(ActionEvent event) {
        updateList();
    }

    @FXML
    void btnOffOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.close();
    }

    @FXML
    void btnPipeSegmentsOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.setScene(GasPipelineApp.pipeSegments);
    }

    @FXML
    void btnStationsOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.setScene(GasPipelineApp.compressorStations);
    }

    @FXML
    void btnPipeClassesOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.setScene(GasPipelineApp.pipeClasses);
    }

    private void updateList() {
        measurements = new PressureMeasurementService().findAll();
        segmentMap = new HashMap<>();
        stationMap = new HashMap<>();

        for (PipeSegment seg : new PipeSegmentService().findAll()) {
            segmentMap.put(seg.getSegmentId(), seg.getSegmentNumber());
        }
        for (CompressorStation cs : new CompressorStationService().findAll()) {
            stationMap.put(cs.getStationId(), cs.getName());
        }

        measurementsObservable = FXCollections.observableArrayList();
        for (PressureMeasurement pm : measurements) {
            String segmentNumber = segmentMap.getOrDefault(pm.getSegmentId(), "Неизвестно");
            String stationName = stationMap.getOrDefault(pm.getStationId(), "Неизвестно");
            measurementsObservable.add(new PressureMeasurementTableItem(pm, segmentNumber, stationName));
        }
        measurementsTable.setItems(measurementsObservable);
    }

    @FXML
    public void initialize() {
        segmentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("segmentNumber"));
        stationNameColumn.setCellValueFactory(new PropertyValueFactory<>("stationName"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("measurementTime"));
        pressureColumn.setCellValueFactory(new PropertyValueFactory<>("pressure"));
        temperatureColumn.setCellValueFactory(new PropertyValueFactory<>("temperature"));
        updateList();
    }
}