package ru.qurati.gasproject.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "pipe_segments")
public class PipeSegment {
    @Id
    @Column(name = "segment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer segmentId;

    @Column(name = "pipe_class_id")
    private Integer pipeClassId;

    @Column(name = "segment_number")
    private String segmentNumber;

    @Column(name = "status")
    private String status;

    @Column(name = "last_inspection_date")
    private LocalDate lastInspectionDate;

    @Column(name = "length_km")
    private Double lengthKm;

    // Конструкторы
    public PipeSegment() {}

    // Геттеры и сеттеры
    public Integer getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(Integer segmentId) {
        this.segmentId = segmentId;
    }

    public Integer getPipeClassId() {
        return pipeClassId;
    }

    public void setPipeClassId(Integer pipeClassId) {
        this.pipeClassId = pipeClassId;
    }

    public String getSegmentNumber() {
        return segmentNumber;
    }

    public void setSegmentNumber(String segmentNumber) {
        if (segmentNumber != null && !segmentNumber.trim().isEmpty()) {
            this.segmentNumber = segmentNumber;
        } else {
            throw new IllegalArgumentException("Номер участка не может быть пустым!");
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status != null && !status.trim().isEmpty()) {
            this.status = status;
        } else {
            throw new IllegalArgumentException("Статус не может быть пустым!");
        }
    }

    public LocalDate getLastInspectionDate() {
        return lastInspectionDate;
    }

    public void setLastInspectionDate(String dateText) {
        if (dateText != null && !dateText.trim().isEmpty()) {
            try {
                this.lastInspectionDate = LocalDate.parse(dateText);
            } catch (Exception e) {
                throw new IllegalArgumentException("Дата должна быть в формате ГГГГ-ММ-ДД!");
            }
        } else {
            throw new IllegalArgumentException("Дата проверки не может быть пустой!");
        }
    }

    public Double getLengthKm() {
        return lengthKm;
    }

    public void setLengthKm(String lengthText) {
        if (lengthText != null && !lengthText.trim().isEmpty()) {
            try {
                double l = Double.parseDouble(lengthText);
                if (l > 0) {
                    this.lengthKm = l;
                } else {
                    throw new IllegalArgumentException("Длина должна быть положительным числом!");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Длина должна быть числом!");
            }
        } else {
            throw new IllegalArgumentException("Длина не может быть пустой!");
        }
    }

    public void setLastInspectionDate(LocalDate lastInspectionDate) {
        this.lastInspectionDate = lastInspectionDate;
    }

    public void setLengthKm(Double lengthKm) {
        this.lengthKm = lengthKm;
    }

    @Override
    public String toString() {
        return segmentNumber;
    }
}