package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.object.JobDO;
import org.springframework.stereotype.Repository;

@Repository
public class JobDao extends AbstractBaseDao<JobDO> implements IJobDao {

    @Override
    protected Class<JobDO> getDOClass() {
        return JobDO.class;
    }

}
