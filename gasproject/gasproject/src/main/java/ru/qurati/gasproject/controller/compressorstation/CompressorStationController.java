package ru.qurati.gasproject.controller.compressorstation;

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
import ru.qurati.gasproject.service.CompressorStationService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CompressorStationController {
    private List<CompressorStation> stations;
    private ObservableList<CompressorStationTableItem> stationsObservable;

    @FXML
    private TableView<CompressorStationTableItem> stationsTable;
    @FXML
    private TableColumn<CompressorStationTableItem, String> nameColumn;
    @FXML
    private TableColumn<CompressorStationTableItem, String> locationColumn;
    @FXML
    private TableColumn<CompressorStationTableItem, String> supervisorColumn;
    @FXML
    private TableColumn<CompressorStationTableItem, String> phoneColumn;

    @FXML
    void btnAddStation(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(GasPipelineApp.class.getResource("add-edit-compressor-station-dialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(GasPipelineApp.primaryStage);
            dialogStage.setMinWidth(450);
            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle("Добавить компрессорную станцию");
            AddEditCompressorStationDialog controller = loader.getController();
            controller.setAddDialogStage(dialogStage);
            dialogStage.showAndWait();
            updateList();
        } catch (IOException e) {
            showAlert("Ошибка", "Ошибка открытия окна: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void btnEditStation(ActionEvent event) {
        CompressorStationTableItem currentItem = stationsTable.getSelectionModel().getSelectedItem();
        if (currentItem != null) {
            try {
                FXMLLoader loader = new FXMLLoader(GasPipelineApp.class.getResource("add-edit-compressor-station-dialog.fxml"));
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(GasPipelineApp.primaryStage);
                dialogStage.setMinWidth(450);
                dialogStage.setScene(new Scene(loader.load()));
                dialogStage.setTitle("Редактировать станцию");
                AddEditCompressorStationDialog controller = loader.getController();
                controller.setEditDialogStage(dialogStage, currentItem.getStation());
                dialogStage.showAndWait();
                updateList();
            } catch (IOException e) {
                showAlert("Ошибка", "Ошибка открытия окна: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Предупреждение", "Выберите запись в таблице для редактирования", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void btnDeleteStation(ActionEvent event) {
        CompressorStationTableItem currentItem = stationsTable.getSelectionModel().getSelectedItem();
        if (currentItem != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Удаление записи");
            alert.setContentText("Вы действительно хотите удалить станцию \"" + currentItem.getName() + "\"?\n\nВНИМАНИЕ: Будут удалены все связанные сегменты и замеры!");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new CompressorStationService().delete(currentItem.getStation());
                stationsTable.getItems().remove(currentItem);
                showAlert("Информация", "Запись успешно удалена", Alert.AlertType.INFORMATION);
            }
        } else {
            showAlert("Предупреждение", "Выберите запись в таблице для удаления", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void btnUpdateStations(ActionEvent event) {
        updateList();
        showAlert("Информация", "Данные обновлены", Alert.AlertType.INFORMATION);
    }

    @FXML
    void btnOffOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.close();
    }

    @FXML
    void btnPipeSegmentsOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.setScene(GasPipelineApp.pipeSegments);
        GasPipelineApp.primaryStage.setTitle("Газопроводные магистрали - Сегменты труб");
    }

    @FXML
    void btnPipeClassesOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.setScene(GasPipelineApp.pipeClasses);
        GasPipelineApp.primaryStage.setTitle("Газопроводные магистрали - Классы сегментов");
    }

    @FXML
    void btnMeasurementsOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.setScene(GasPipelineApp.pressureMeasurements);
        GasPipelineApp.primaryStage.setTitle("Газопроводные магистрали - Замеры давления");
    }

    private void updateList() {
        stations = new CompressorStationService().findAll();
        stationsObservable = FXCollections.observableArrayList();
        for (CompressorStation station : stations) {
            stationsObservable.add(new CompressorStationTableItem(station));
        }
        stationsTable.setItems(stationsObservable);
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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        supervisorColumn.setCellValueFactory(new PropertyValueFactory<>("shiftSupervisor"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        updateList();
    }
}