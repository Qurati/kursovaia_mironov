package ru.qurati.gasproject.service;

import ru.qurati.gasproject.model.PipeSegment;
import ru.qurati.gasproject.repository.PipeSegmentDao;

import java.util.List;

public class PipeSegmentService {
    private PipeSegmentDao pipeSegmentDao = new PipeSegmentDao();

    public List<PipeSegment> findAll() {
        return pipeSegmentDao.findAll();
    }

    public PipeSegment findOne(final long id) {
        return pipeSegmentDao.findOne(id);
    }

    public void save(final PipeSegment entity) {
        if (entity == null) return;
        pipeSegmentDao.save(entity);
    }

    public void update(final PipeSegment entity) {
        if (entity == null) return;
        pipeSegmentDao.update(entity);
    }

    public void delete(final PipeSegment entity) {
        if (entity == null) return;
        pipeSegmentDao.delete(entity);
    }

    public void deleteById(final Long id) {
        if (id == null) return;
        pipeSegmentDao.deleteById(id);
    }
}