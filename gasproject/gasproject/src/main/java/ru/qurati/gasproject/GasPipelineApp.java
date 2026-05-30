package ru.qurati.gasproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.qurati.gasproject.util.HibernateSessionFactoryUtil;

import java.io.IOException;
import java.net.URL;

public class GasPipelineApp extends Application {
    public static Stage primaryStage;
    public static Scene compressorStations;
    public static Scene pipeClasses;
    public static Scene pipeSegments;
    public static Scene pressureMeasurements;

    @Override
    public void start(Stage stage) throws IOException {
        // Проверка Hibernate
        try {
            var factory = HibernateSessionFactoryUtil.getSessionFactory();
            if (factory == null) {
                throw new RuntimeException("SessionFactory is null");
            }
            System.out.println("Hibernate initialized successfully");
        } catch (Exception e) {
            System.err.println("Hibernate init error: " + e.getMessage());
            e.printStackTrace();
        }

        primaryStage = stage;

        compressorStations = createScene("/ru/qurati/gasproject/compressor-station-view.fxml");
        pipeSegments = createScene("/ru/qurati/gasproject/pipe-segment-view.fxml");
        pipeClasses = createScene("/ru/qurati/gasproject/pipe-class-view.fxml");
        pressureMeasurements = createScene("/ru/qurati/gasproject/pressure-measurement-view.fxml");

        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(675);
        primaryStage.setTitle("Газопроводные магистрали - Система мониторинга давления");

        compressorStations.getStylesheets().add("/base-styles.css");
        pipeClasses.getStylesheets().add("/base-styles.css");
        pipeSegments.getStylesheets().add("/base-styles.css");
        pressureMeasurements.getStylesheets().add("/base-styles.css");

        primaryStage.setScene(compressorStations);
        primaryStage.show();
    }

    private Scene createScene(String fxmlPath) throws IOException {
        URL resource = GasPipelineApp.class.getResource(fxmlPath);
        if (resource == null) {
            throw new RuntimeException("FXML файл не найден: " + fxmlPath);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        return new Scene(fxmlLoader.load());
    }

    public static void main(String[] args) {
        launch();
    }
}