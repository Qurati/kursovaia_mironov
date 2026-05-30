package ru.qurati.gasproject.service;

import ru.qurati.gasproject.model.PipeClass;
import ru.qurati.gasproject.repository.PipeClassDao;

import java.util.List;

public class PipeClassService {
    private PipeClassDao pipeClassDao = new PipeClassDao();

    public List<PipeClass> findAll() {
        return pipeClassDao.findAll();
    }

    public PipeClass findOne(final long id) {
        return pipeClassDao.findOne(id);
    }

    public void save(final PipeClass entity) {
        if (entity == null) return;
        pipeClassDao.save(entity);
    }

    public void update(final PipeClass entity) {
        if (entity == null) return;
        pipeClassDao.update(entity);
    }

    public void delete(final PipeClass entity) {
        if (entity == null) return;
        pipeClassDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null) return;
        pipeClassDao.deleteById(id);
    }
}