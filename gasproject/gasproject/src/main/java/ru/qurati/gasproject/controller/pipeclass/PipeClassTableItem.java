package ru.qurati.gasproject.controller.pipeclass;

import javafx.beans.property.SimpleStringProperty;
import ru.qurati.gasproject.model.PipeClass;

public class PipeClassTableItem {
    private SimpleStringProperty name;
    private SimpleStringProperty diameter;
    private SimpleStringProperty workingPressure;
    private SimpleStringProperty material;
    private SimpleStringProperty layingYear;
    private PipeClass pipeClass;

    public PipeClassTableItem(PipeClass pipeClass) {
        this.name = new SimpleStringProperty(pipeClass.getName());
        this.diameter = new SimpleStringProperty(pipeClass.getDiameter() != null ? pipeClass.getDiameter().toString() : "");
        this.workingPressure = new SimpleStringProperty(pipeClass.getWorkingPressure() != null ? pipeClass.getWorkingPressure().toString() : "");
        this.material = new SimpleStringProperty(pipeClass.getMaterial());
        this.layingYear = new SimpleStringProperty(pipeClass.getLayingYear() != null ? pipeClass.getLayingYear().toString() : "");
        this.pipeClass = pipeClass;
    }

    public String getName() { return name.get(); }
    public SimpleStringProperty nameProperty() { return name; }
    public String getDiameter() { return diameter.get(); }
    public SimpleStringProperty diameterProperty() { return diameter; }
    public String getWorkingPressure() { return workingPressure.get(); }
    public SimpleStringProperty workingPressureProperty() { return workingPressure; }
    public String getMaterial() { return material.get(); }
    public SimpleStringProperty materialProperty() { return material; }
    public String getLayingYear() { return layingYear.get(); }
    public SimpleStringProperty layingYearProperty() { return layingYear; }
    public PipeClass getPipeClass() { return pipeClass; }
}