package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.object.JobDO;

public class JobDao extends AbstractBaseDao<JobDO>{

    @Override
    protected Class<JobDO> getDOClass() {
        return JobDO.class;
    }

}