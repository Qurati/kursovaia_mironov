package ru.qurati.gasproject.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "pressure_measurements")
public class PressureMeasurement {
    @Id
    @Column(name = "measurement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer measurementId;

    @Column(name = "segment_id")
    private Integer segmentId;

    @Column(name = "station_id")
    private Integer stationId;

    @Column(name = "measurement_time")
    private LocalDateTime measurementTime;

    @Column(name = "pressure")
    private Double pressure;

    @Column(name = "temperature")
    private Double temperature;

    public Integer getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(Integer measurementId) {
        this.measurementId = measurementId;
    }

    public Integer getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Integer segmentId) {
        this.segmentId = segmentId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public LocalDateTime getMeasurementTime() {
        return measurementTime;
    }

    public void setMeasurementTime(LocalDateTime measurementTime) {
        this.measurementTime = measurementTime;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(String pressureText) {
        if (pressureText != null && !pressureText.trim().isEmpty()) {
            try {
                double p = Double.parseDouble(pressureText);
                if (p >= 0) {
                    this.pressure = p;
                } else {
                    throw new IllegalArgumentException("Давление не может быть отрицательным!");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Давление должно быть числом!");
            }
        } else {
            throw new IllegalArgumentException("Давление не может быть пустым!");
        }
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(String tempText) {
        if (tempText != null && !tempText.trim().isEmpty()) {
            try {
                this.temperature = Double.parseDouble(tempText);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Температура должна быть числом!");
            }
        } else {
            throw new IllegalArgumentException("Температура не может быть пустой!");
        }
    }

    @Override
    public String toString() {
        return "Замер №" + measurementId;
    }
}