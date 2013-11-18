package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.object.JobEntity;
import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.web.service.ISimpleService;
import com.careeropts.rurse.web.service.util.EntityTransform;

import static org.mockito.Mockito.mock;

public class JobServiceTest extends AbstractSimpleServiceTest<Job, JobEntity> {

    @Override
    protected IJobDao mockDao() {
        return mock(IJobDao.class);
    }

    @Override
    protected ISimpleService<Job> getService(IBaseDao<JobEntity> dao) {
        return new JobService((IJobDao) dao);
    }

    @Override
    protected Job genModel(Long id, String title, String description) {
        return new Job(id, title, description, null, null, null, true);
    }

    @Override
    protected JobEntity toEntity(Job model) {
        return EntityTransform.toEntity(model);
    }
}
