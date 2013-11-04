package com.careeropts.rurse.dao.impl;

import com.careeropts.rurse.dao.IBaseDao;
import com.careeropts.rurse.dao.ICourseDao;
import com.careeropts.rurse.dao.object.CourseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static java.util.UUID.randomUUID;

public class CourseDaoTest extends AbstractBaseDaoTest<CourseEntity> {

    @Autowired
    ICourseDao dao;

    private static Random random = new Random();

    public static CourseEntity genEntity(Long id, String title) {
        return new CourseEntity(
                id,
                title,
                randomUUID().toString(),
                random.nextDouble(),
                randomUUID().toString()
        );
    }

    public static CourseEntity copyEntity(CourseEntity entity) {
        return new CourseEntity(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCost(),
                entity.getDuration()
        );
    }

    @Override
    protected IBaseDao<CourseEntity> getDao() {
        return dao;
    }

    @Override
    protected CourseEntity genTestEntity() {
        return genEntity(null, randomUUID().toString());
    }

    @Override
    protected CourseEntity genUpdateEntity(CourseEntity entity) {
        return genEntity(entity.getId(), entity.getTitle());
    }

    @Override
    protected CourseEntity copy(CourseEntity entity) {
        return copyEntity(entity);
    }

    @Override
    protected Long getId(CourseEntity entity) {
        return entity.getId();
    }
}
