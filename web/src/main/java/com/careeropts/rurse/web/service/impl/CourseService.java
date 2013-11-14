package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.ICourseDao;
import com.careeropts.rurse.dao.object.CourseEntity;
import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.web.exception.BadRequestException;
import com.careeropts.rurse.web.service.ICourseService;
import com.careeropts.rurse.web.service.util.EntityTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.Math.round;

@Service
public class CourseService extends AbstractSimpleService<Course, CourseEntity> implements ICourseService {

    @Autowired
    protected CourseService(ICourseDao dao) {
        super(dao);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Long getId(Course model) {
        return model.getId();
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    protected CourseEntity toEntity(Course model) {
        return EntityTransform.toEntity(model);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Course fromEntity(CourseEntity entity) {
        return EntityTransform.fromEntity(entity);
    }
}
