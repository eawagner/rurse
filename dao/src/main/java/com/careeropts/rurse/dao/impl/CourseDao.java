package com.careeropts.rurse.dao.impl;


import com.careeropts.rurse.dao.ICourseDao;
import com.careeropts.rurse.dao.object.CourseEntity;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDao extends AbstractBaseDao<CourseEntity> implements ICourseDao {

    @Autowired
    public CourseDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<CourseEntity> getDOClass() {
        return CourseEntity.class;
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
        return "courseAnalyzer";
    }
}
