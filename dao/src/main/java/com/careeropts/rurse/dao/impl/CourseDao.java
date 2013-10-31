package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.ICourseDao;
import com.careeropts.rurse.dao.object.CourseDO;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDao extends AbstractBaseDao<CourseDO> implements ICourseDao {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<CourseDO> getDOClass() {
        return CourseDO.class;
    }

}
