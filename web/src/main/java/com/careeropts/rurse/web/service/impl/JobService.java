package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.object.JobDO;
import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.web.exception.BadRequestException;
import com.careeropts.rurse.web.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Strings.isNullOrEmpty;

@Service
public class JobService extends AbstractSimpleService<Job, JobDO> implements IJobService {

    @Autowired
    protected JobService(IJobDao dao) {
        super(dao);
    }

    @Override
    protected void normalizeAndValidate(Job model) {
        if (isNullOrEmpty(model.getTitle())) {
            throw new BadRequestException("A job listing must have a title");
        }

        if (isNullOrEmpty(model.getDescription())) {
            throw new BadRequestException("A job listing must have a description");
        }

        //TODO validate city and state.
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
