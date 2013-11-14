package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.object.JobEntity;
import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.web.exception.BadRequestException;
import com.careeropts.rurse.web.service.IJobService;
import com.careeropts.rurse.web.service.util.EntityTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Strings.isNullOrEmpty;

@Service
public class JobService extends AbstractSimpleService<Job, JobEntity> implements IJobService {

    @Autowired
    protected JobService(IJobDao dao) {
        super(dao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(Job model) {
        return model.getId();
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected JobEntity toEntity(Job model) {
        return EntityTransform.toEntity(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Job fromEntity(JobEntity entity) {
        return EntityTransform.fromEntity(entity);
    }
}
