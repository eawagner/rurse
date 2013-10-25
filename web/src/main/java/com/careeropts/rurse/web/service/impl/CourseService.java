package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.object.CourseDO;
import com.careeropts.rurse.model.Course;

public class CourseService extends AbstractSimpleService<Course, CourseDO> {

    @Override
    protected void normalizeAndValidate(Course model) {
        //TODO
    }

    @Override
    protected CourseDO toDatabaseObject(Course model) {
        if (model == null)
            return null;

        return new CourseDO(
                model.getId(),
                model.getTitle(),
                model.getDescription(),
                model.getCost(),
                model.getDuration()
        );
    }

    @Override
    protected Course fromDatabaseObject(CourseDO dataObject) {
        if (dataObject == null)
            return null;

        return new Course(
                dataObject.getId(),
                dataObject.getTitle(),
                dataObject.getDescription(),
                dataObject.getCost(),
                dataObject.getDuration()
        );
    }
}
