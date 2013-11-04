package com.careeropts.rurse.dao.impl;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.object.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.UUID.randomUUID;

public class JobDaoTest extends AbstractBaseDaoTest<JobEntity> {

    @Autowired
    IJobDao dao;

    public static JobEntity genEntity(Long id, String title) {
        return new JobEntity(
                id,
                title,
                randomUUID().toString(),
                randomUUID().toString(),
                randomUUID().toString(),
                randomUUID().toString(),
                true
        );
    }

    public static JobEntity copyEntity(JobEntity entity) {
        return new JobEntity(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getLocation(),
                entity.getCity(),
                entity.getState(),
                entity.isActive()
        );
    }

    @Override
    protected IBaseDao<JobEntity> getDao() {
        return dao;
    }

    @Override
    protected JobEntity genTestEntity() {
        return genEntity(null, randomUUID().toString());
    }

    @Override
    protected JobEntity genUpdateEntity(JobEntity entity) {
        return genEntity(entity.getId(), entity.getTitle());
    }

    @Override
    protected JobEntity copy(JobEntity entity) {
        return copyEntity(entity);
    }

    @Override
    protected Long getId(JobEntity entity) {
        return entity.getId();
    }
}
