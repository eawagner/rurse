package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.ICourseDao;
import com.careeropts.rurse.dao.object.CourseEntity;
import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.web.service.ISimpleService;
import com.careeropts.rurse.web.service.util.EntityTransform;

import static org.mockito.Mockito.mock;

public class CourseServiceTest extends AbstractSimpleServiceTest<Course, CourseEntity> {

    @Override
    protected ICourseDao mockDao() {
        return mock(ICourseDao.class);
    }

    @Override
    protected ISimpleService<Course> getService(IBaseDao<CourseEntity> dao) {
        return new CourseService((ICourseDao) dao);
    }

    @Override
    protected Course genModel(Long id, String title, String description) {
        return new Course(id, title, description, null, null);
    }

    @Override
    protected CourseEntity toEntity(Course model) {
        return EntityTransform.toEntity(model);
    }
}
