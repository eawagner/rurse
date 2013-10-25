package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.object.JobDO;
import com.careeropts.rurse.model.Job;

public class JobService extends AbstractSimpleService<Job, JobDO> {

    @Override
    protected void normalizeAndValidate(Job model) {
        //TODO
    }

    @Override
    protected JobDO toDatabaseObject(Job model) {
        if (model == null)
            return null;

        return new JobDO(
                model.getId(),
                model.getTitle(),
                model.getDescription(),
                model.getLocation(),
                model.getCity(),
                model.getState(),
                model.isActive()
        );
    }

    @Override
    protected Job fromDatabaseObject(JobDO dataObject) {
        if (dataObject == null)
            return null;

        return new Job(
                dataObject.getId(),
                dataObject.getTitle(),
                dataObject.getDescription(),
                dataObject.getLocation(),
                dataObject.getCity(),
                dataObject.getState(),
                dataObject.isActive()
        );
    }
}
