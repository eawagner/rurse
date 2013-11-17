package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.object.JobEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class JobDao extends AbstractBaseDao<JobEntity> implements IJobDao {

    @Autowired
    public JobDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<JobEntity> getDOClass() {
        return JobEntity.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getSearchFields() {
        return new String[]{"title", "description"};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAnalyzer() {
        return "jobAnalyzer";
    }
}
