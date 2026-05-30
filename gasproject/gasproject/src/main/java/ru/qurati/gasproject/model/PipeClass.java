package ru.qurati.gasproject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pipe_classes")
public class PipeClass {
    @Id
    @Column(name = "pipe_class_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pipeClassId;

    @Column(name = "name")
    private String name;

    @Column(name = "diameter")
    private Integer diameter;

    @Column(name = "working_pressure")
    private Integer workingPressure;

    @Column(name = "material")
    private String material;

    @Column(name = "laying_year")
    private Integer layingYear;

    public Integer getPipeClassId() {
        return pipeClassId;
    }

    public void setPipeClassId(Integer pipeClassId) {
        this.pipeClassId = pipeClassId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Наименование класса не может быть пустым!");
        }
    }

    public Integer getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameterText) {
        if (diameterText != null && !diameterText.trim().isEmpty()) {
            try {
                int d = Integer.parseInt(diameterText);
                if (d > 0) {
                    this.diameter = d;
                } else {
                    throw new IllegalArgumentException("Диаметр должен быть положительным числом!");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Диаметр должен быть целым числом!");
            }
        } else {
            throw new IllegalArgumentException("Диаметр не может быть пустым!");
        }
    }

    public Integer getWorkingPressure() {
        return workingPressure;
    }

    public void setWorkingPressure(String pressureText) {
        if (pressureText != null && !pressureText.trim().isEmpty()) {
            try {
                int p = Integer.parseInt(pressureText);
                if (p > 0) {
                    this.workingPressure = p;
                } else {
                    throw new IllegalArgumentException("Рабочее давление должно быть положительным числом!");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Рабочее давление должно быть целым числом!");
            }
        } else {
            throw new IllegalArgumentException("Рабочее давление не может быть пустым!");
        }
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        if (material != null && !material.trim().isEmpty()) {
            this.material = material;
        } else {
            throw new IllegalArgumentException("Материал не может быть пустым!");
        }
    }

    public Integer getLayingYear() {
        return layingYear;
    }

    public void setLayingYear(String yearText) {
        if (yearText != null && !yearText.trim().isEmpty()) {
            try {
                int year = Integer.parseInt(yearText);
                if (year >= 1950 && year <= 2030) {
                    this.layingYear = year;
                } else {
                    throw new IllegalArgumentException("Год укладки должен быть между 1950 и 2030!");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Год должен быть целым числом!");
            }
        } else {
            throw new IllegalArgumentException("Год укладки не может быть пустым!");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}