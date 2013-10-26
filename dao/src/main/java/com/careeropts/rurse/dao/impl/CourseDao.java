package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.object.CourseDO;

public class CourseDao extends AbstractBaseDao<CourseDO>{

    @Override
    protected Class<CourseDO> getDOClass() {
        return CourseDO.class;
    }

}
