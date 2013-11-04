package com.careeropts.rurse.dao.impl.search;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.ICourseDao;
import com.careeropts.rurse.dao.object.CourseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import static com.careeropts.rurse.dao.impl.CourseDaoTest.copyEntity;
import static com.careeropts.rurse.dao.impl.CourseDaoTest.genEntity;

public class CourseSearchTest extends AbstractSearchTest<CourseEntity> {

    @Autowired
    ICourseDao dao;

    @Override
    protected IBaseDao<CourseEntity> getDao() {
        return dao;
    }

    @Override
    protected CourseEntity genTestEntity(String searchText) {
        return genEntity(null, searchText);
    }

    @Override
    protected CourseEntity copy(CourseEntity entity) {
        return copyEntity(entity);
    }
}
