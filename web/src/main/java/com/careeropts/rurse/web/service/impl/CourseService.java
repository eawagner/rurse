package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.ICourseDao;
import com.careeropts.rurse.dao.object.CourseDO;
import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.web.exception.BadRequestException;
import com.careeropts.rurse.web.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.Math.round;

@Service
public class CourseService extends AbstractSimpleService<Course, CourseDO> implements ICourseService {

    @Autowired
    protected CourseService(ICourseDao dao) {
        super(dao);
    }

    @Override
    protected void normalizeAndValidate(Course model) {

        if (isNullOrEmpty(model.getTitle())) {
            throw new BadRequestException("A course must have a title");
        }

        if (isNullOrEmpty(model.getDescription())) {
            throw new BadRequestException("A course must have a description");
        }

        //normalize price to a two digit value
        if (model.getCost() != null)
            model.setCost(round(model.getCost() * 100.0) / 100.0);
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
