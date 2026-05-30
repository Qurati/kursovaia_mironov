package ru.qurati.gasproject.controller.compressorstation;

import javafx.beans.property.SimpleStringProperty;
import ru.qurati.gasproject.model.CompressorStation;

public class CompressorStationTableItem {
    private SimpleStringProperty name;
    private SimpleStringProperty location;
    private SimpleStringProperty shiftSupervisor;
    private SimpleStringProperty phone;
    private CompressorStation station;

    public CompressorStationTableItem(CompressorStation station) {
        this.name = new SimpleStringProperty(station.getName());
        this.location = new SimpleStringProperty(station.getLocation());
        this.shiftSupervisor = new SimpleStringProperty(station.getShiftSupervisor());
        this.phone = new SimpleStringProperty(station.getPhone());
        this.station = station;
    }

    public String getName() { return name.get(); }
    public SimpleStringProperty nameProperty() { return name; }
    public void setName(String name) { this.name.set(name); }

    public String getLocation() { return location.get(); }
    public SimpleStringProperty locationProperty() { return location; }
    public void setLocation(String location) { this.location.set(location); }

    public String getShiftSupervisor() { return shiftSupervisor.get(); }
    public SimpleStringProperty shiftSupervisorProperty() { return shiftSupervisor; }
    public void setShiftSupervisor(String shiftSupervisor) { this.shiftSupervisor.set(shiftSupervisor); }

    public String getPhone() { return phone.get(); }
    public SimpleStringProperty phoneProperty() { return phone; }
    public void setPhone(String phone) { this.phone.set(phone); }

    public CompressorStation getStation() { return station; }
    public void setStation(CompressorStation station) { this.station = station; }
}