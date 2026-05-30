package ru.qurati.gasproject.repository;

import ru.qurati.gasproject.model.PressureMeasurement;

public class PressureMeasurementDao extends BaseDao<PressureMeasurement> {
    public PressureMeasurementDao() {
        super(PressureMeasurement.class);
    }
}