package ru.qurati.gasproject.controller.pipesegment;

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
import ru.qurati.gasproject.model.PipeClass;
import ru.qurati.gasproject.model.PipeSegment;
import ru.qurati.gasproject.service.PipeClassService;
import ru.qurati.gasproject.service.PipeSegmentService;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PipeSegmentController {
    private List<PipeSegment> segments;
    private ObservableList<PipeSegmentTableItem> segmentsObservable;
    private Map<Integer, String> classMap;

    @FXML
    private TableView<PipeSegmentTableItem> segmentsTable;
    @FXML
    private TableColumn<PipeSegmentTableItem, String> classNameColumn;
    @FXML
    private TableColumn<PipeSegmentTableItem, String> segmentNumberColumn;
    @FXML
    private TableColumn<PipeSegmentTableItem, String> statusColumn;
    @FXML
    private TableColumn<PipeSegmentTableItem, String> inspectionDateColumn;
    @FXML
    private TableColumn<PipeSegmentTableItem, String> lengthColumn;

    @FXML
    void btnAddSegment(ActionEvent event) {
        try {
            URL fxmlLocation = GasPipelineApp.class.getResource("/ru/qurati/gasproject/add-edit-pipe-segment-dialog.fxml");
            if (fxmlLocation == null) {
                throw new RuntimeException("Не найден файл: add-edit-pipe-segment-dialog.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(GasPipelineApp.primaryStage);
            dialogStage.setMinWidth(500);
            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle("Добавить сегмент трубы");
            AddEditPipeSegmentDialog controller = loader.getController();
            controller.setAddDialogStage(dialogStage);
            dialogStage.showAndWait();
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Ошибка открытия окна: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void btnEditSegment(ActionEvent event) {
        PipeSegmentTableItem currentItem = segmentsTable.getSelectionModel().getSelectedItem();
        if (currentItem != null) {
            try {
                URL fxmlLocation = GasPipelineApp.class.getResource("/ru/qurati/gasproject/add-edit-pipe-segment-dialog.fxml");
                if (fxmlLocation == null) {
                    throw new RuntimeException("Не найден файл: add-edit-pipe-segment-dialog.fxml");
                }
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(GasPipelineApp.primaryStage);
                dialogStage.setMinWidth(500);
                dialogStage.setScene(new Scene(loader.load()));
                dialogStage.setTitle("Редактировать сегмент трубы");
                AddEditPipeSegmentDialog controller = loader.getController();
                controller.setEditDialogStage(dialogStage, currentItem.getSegment());
                dialogStage.showAndWait();
                updateList();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Ошибка", "Ошибка открытия окна: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Предупреждение", "Выберите запись в таблице для редактирования", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void btnDeleteSegment(ActionEvent event) {
        PipeSegmentTableItem currentItem = segmentsTable.getSelectionModel().getSelectedItem();
        if (currentItem != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Удаление записи");
            alert.setContentText("Вы действительно хотите удалить сегмент \"" + currentItem.getSegmentNumber() + "\"?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new PipeSegmentService().delete(currentItem.getSegment());
                segmentsTable.getItems().remove(currentItem);
            }
        } else {
            showAlert("Предупреждение", "Выберите запись в таблице для удаления", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void btnUpdateSegments(ActionEvent event) {
        updateList();
    }

    @FXML
    void btnOffOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.close();
    }

    @FXML
    void btnStationsOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.setScene(GasPipelineApp.compressorStations);
    }

    @FXML
    void btnPipeClassesOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.setScene(GasPipelineApp.pipeClasses);
    }

    @FXML
    void btnMeasurementsOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.setScene(GasPipelineApp.pressureMeasurements);
    }

    private void updateList() {
        segments = new PipeSegmentService().findAll();
        classMap = new HashMap<>();

        for (PipeClass pc : new PipeClassService().findAll()) {
            classMap.put(pc.getPipeClassId(), pc.getName());
        }

        segmentsObservable = FXCollections.observableArrayList();
        for (PipeSegment segment : segments) {
            String className = classMap.getOrDefault(segment.getPipeClassId(), "Неизвестно");
            segmentsObservable.add(new PipeSegmentTableItem(segment, className));
        }
        segmentsTable.setItems(segmentsObservable);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        segmentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("segmentNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        inspectionDateColumn.setCellValueFactory(new PropertyValueFactory<>("lastInspectionDate"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<>("lengthKm"));
        updateList();
    }
}