package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.ICourseDao;
import com.careeropts.rurse.dao.object.CourseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDao extends AbstractBaseDao<CourseEntity> implements ICourseDao {

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<CourseEntity> getDOClass() {
        return CourseEntity.class;
    }

    @Override
    protected String[] getSearchFields() {
        return new String[]{"title", "description"};
    }

    @Override
    protected String getAnalyzer() {
        return "courseAnalyzer";
    }
}
