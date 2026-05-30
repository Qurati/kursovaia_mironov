package ru.qurati.gasproject.service;

import ru.qurati.gasproject.model.CompressorStation;
import ru.qurati.gasproject.repository.CompressorStationDao;

import java.util.List;

public class CompressorStationService {
    private CompressorStationDao compressorStationDao = new CompressorStationDao();

    public List<CompressorStation> findAll() {
        return compressorStationDao.findAll();
    }

    public CompressorStation findOne(final long id) {
        return compressorStationDao.findOne(id);
    }

    public void save(final CompressorStation entity) {
        if (entity == null) return;
        compressorStationDao.save(entity);
    }

    public void update(final CompressorStation entity) {
        if (entity == null) return;
        compressorStationDao.update(entity);
    }

    public void delete(final CompressorStation entity) {
        if (entity == null) return;
        compressorStationDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null) return;
        compressorStationDao.deleteById(id);
    }
}