package com.careeropts.rurse.dao.impl.search;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.object.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;

import static com.careeropts.rurse.dao.impl.JobDaoTest.copyEntity;
import static com.careeropts.rurse.dao.impl.JobDaoTest.genEntity;

public class JobSearchTest extends AbstractSearchTest<JobEntity> {

    @Autowired
    IJobDao dao;

    @Override
    protected IBaseDao<JobEntity> getDao() {
        return dao;
    }

    @Override
    protected JobEntity genTestEntity(String searchText) {
        return genEntity(null, searchText);
    }

    @Override
    protected JobEntity copy(JobEntity entity) {
        return copyEntity(entity);
    }
}
