package ru.qurati.gasproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "compressor_stations")
public class CompressorStation {
    @Id
    @Column(name = "station_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stationId;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "shift_supervisor")
    private String shiftSupervisor;

    @Column(name = "phone")
    private String phone;

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Название станции не может быть пустым!");
        }
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (location != null && !location.trim().isEmpty()) {
            this.location = location;
        } else {
            throw new IllegalArgumentException("Местоположение не может быть пустым!");
        }
    }

    public String getShiftSupervisor() {
        return shiftSupervisor;
    }

    public void setShiftSupervisor(String shiftSupervisor) {
        if (shiftSupervisor != null && !shiftSupervisor.trim().isEmpty()) {
            this.shiftSupervisor = shiftSupervisor;
        } else {
            throw new IllegalArgumentException("Начальник смены не может быть пустым!");
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone != null && !phone.trim().isEmpty()) {
            this.phone = phone;
        } else {
            throw new IllegalArgumentException("Телефон не может быть пустым!");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}