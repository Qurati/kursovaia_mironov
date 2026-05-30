package ru.qurati.gasproject.controller.pressuremeasurement;

import javafx.beans.property.SimpleStringProperty;
import ru.qurati.gasproject.model.PressureMeasurement;
import java.time.format.DateTimeFormatter;

public class PressureMeasurementTableItem {
    private SimpleStringProperty segmentNumber;
    private SimpleStringProperty stationName;
    private SimpleStringProperty measurementTime;
    private SimpleStringProperty pressure;
    private SimpleStringProperty temperature;
    private PressureMeasurement measurement;

    public PressureMeasurementTableItem(PressureMeasurement measurement, String segmentNumber, String stationName) {
        this.segmentNumber = new SimpleStringProperty(segmentNumber);
        this.stationName = new SimpleStringProperty(stationName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.measurementTime = new SimpleStringProperty(
                measurement.getMeasurementTime() != null ? measurement.getMeasurementTime().format(formatter) : ""
        );
        this.pressure = new SimpleStringProperty(measurement.getPressure() != null ? measurement.getPressure().toString() : "");
        this.temperature = new SimpleStringProperty(measurement.getTemperature() != null ? measurement.getTemperature().toString() : "");
        this.measurement = measurement;
    }

    public String getSegmentNumber() { return segmentNumber.get(); }
    public SimpleStringProperty segmentNumberProperty() { return segmentNumber; }
    public String getStationName() { return stationName.get(); }
    public SimpleStringProperty stationNameProperty() { return stationName; }
    public String getMeasurementTime() { return measurementTime.get(); }
    public SimpleStringProperty measurementTimeProperty() { return measurementTime; }
    public String getPressure() { return pressure.get(); }
    public SimpleStringProperty pressureProperty() { return pressure; }
    public String getTemperature() { return temperature.get(); }
    public SimpleStringProperty temperatureProperty() { return temperature; }
    public PressureMeasurement getMeasurement() { return measurement; }
}