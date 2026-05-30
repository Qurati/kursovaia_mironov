package ru.qurati.gasproject.controller.pipeclass;

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
import ru.qurati.gasproject.service.PipeClassService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;

public class PipeClassController {
    private List<PipeClass> pipeClasses;
    private ObservableList<PipeClassTableItem> classesObservable;

    @FXML
    private TableView<PipeClassTableItem> pipeClassesTable;
    @FXML
    private TableColumn<PipeClassTableItem, String> nameColumn;
    @FXML
    private TableColumn<PipeClassTableItem, String> diameterColumn;
    @FXML
    private TableColumn<PipeClassTableItem, String> pressureColumn;
    @FXML
    private TableColumn<PipeClassTableItem, String> materialColumn;
    @FXML
    private TableColumn<PipeClassTableItem, String> yearColumn;

    @FXML
    void btnAddPipeClass(ActionEvent event) {
        try {
            URL fxmlLocation = GasPipelineApp.class.getResource("/ru/qurati/gasproject/add-edit-pipe-class-dialog.fxml");
            if (fxmlLocation == null) {
                throw new RuntimeException("Не найден файл: add-edit-pipe-class-dialog.fxml");
            }
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(GasPipelineApp.primaryStage);
            dialogStage.setMinWidth(450);
            dialogStage.setScene(new Scene(loader.load()));
            dialogStage.setTitle("Добавить класс сегмента");
            AddEditPipeClassDialog controller = loader.getController();
            controller.setAddDialogStage(dialogStage);
            dialogStage.showAndWait();
            updateList();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Ошибка", "Ошибка открытия окна: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void btnEditPipeClass(ActionEvent event) {
        PipeClassTableItem currentItem = pipeClassesTable.getSelectionModel().getSelectedItem();
        if (currentItem != null) {
            try {
                URL fxmlLocation = GasPipelineApp.class.getResource("/ru/qurati/gasproject/add-edit-pipe-class-dialog.fxml");
                if (fxmlLocation == null) {
                    throw new RuntimeException("Не найден файл: add-edit-pipe-class-dialog.fxml");
                }
                FXMLLoader loader = new FXMLLoader(fxmlLocation);
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(GasPipelineApp.primaryStage);
                dialogStage.setMinWidth(450);
                dialogStage.setScene(new Scene(loader.load()));
                dialogStage.setTitle("Редактировать класс сегмента");
                AddEditPipeClassDialog controller = loader.getController();
                controller.setEditDialogStage(dialogStage, currentItem.getPipeClass());
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
    void btnDeletePipeClass(ActionEvent event) {
        PipeClassTableItem currentItem = pipeClassesTable.getSelectionModel().getSelectedItem();
        if (currentItem != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Подтверждение удаления");
            alert.setHeaderText("Удаление записи");
            alert.setContentText("Вы действительно хотите удалить класс \"" + currentItem.getName() + "\"?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new PipeClassService().delete(currentItem.getPipeClass());
                pipeClassesTable.getItems().remove(currentItem);
            }
        } else {
            showAlert("Предупреждение", "Выберите запись в таблице для удаления", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void btnUpdatePipeClasses(ActionEvent event) {
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
    void btnMeasurementsOnAction(ActionEvent event) {
        GasPipelineApp.primaryStage.setScene(GasPipelineApp.pressureMeasurements);
    }

    private void updateList() {
        pipeClasses = new PipeClassService().findAll();
        classesObservable = FXCollections.observableArrayList();
        for (PipeClass pc : pipeClasses) {
            classesObservable.add(new PipeClassTableItem(pc));
        }
        pipeClassesTable.setItems(classesObservable);
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
        diameterColumn.setCellValueFactory(new PropertyValueFactory<>("diameter"));
        pressureColumn.setCellValueFactory(new PropertyValueFactory<>("workingPressure"));
        materialColumn.setCellValueFactory(new PropertyValueFactory<>("material"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("layingYear"));
        updateList();
    }
}