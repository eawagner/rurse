package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.object.JobEntity;
import org.springframework.stereotype.Repository;

@Repository
public class JobDao extends AbstractBaseDao<JobEntity> implements IJobDao {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<JobEntity> getDOClass() {
        return JobEntity.class;
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"title", "description"};
    }

    @Override
    protected String getAnalyzer() {
        return "jobAnalyzer";
    }
}
