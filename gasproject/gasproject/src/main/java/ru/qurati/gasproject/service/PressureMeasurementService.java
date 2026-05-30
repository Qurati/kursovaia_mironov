package ru.qurati.gasproject.service;

import ru.qurati.gasproject.model.PressureMeasurement;
import ru.qurati.gasproject.repository.PressureMeasurementDao;

import java.util.List;

public class PressureMeasurementService {
    private PressureMeasurementDao pressureMeasurementDao = new PressureMeasurementDao();

    public List<PressureMeasurement> findAll() {
        return pressureMeasurementDao.findAll();
    }

    public PressureMeasurement findOne(final long id) {
        return pressureMeasurementDao.findOne(id);
    }

    public void save(final PressureMeasurement entity) {
        if (entity == null) return;
        pressureMeasurementDao.save(entity);
    }

    public void update(final PressureMeasurement entity) {
        if (entity == null) return;
        pressureMeasurementDao.update(entity);
    }

    public void delete(final PressureMeasurement entity) {
        if (entity == null) return;
        pressureMeasurementDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null) return;
        pressureMeasurementDao.deleteById(id);
    }
}