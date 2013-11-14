package com.careeropts.rurse.web.service.util;

import com.careeropts.rurse.dao.object.*;
import com.careeropts.rurse.model.*;

import static com.careeropts.rurse.model.Resume.DocType.fromMimeType;

public class EntityTransform {

    private EntityTransform() {/* private constructor */}

    public static BookEntity toEntity(Book model) {
        if (model == null)
            return null;

        return new BookEntity(
                model.getId(),
                model.getTitle(),
                model.getDescription(),
                model.getPublisher(),
                model.getPublishDate(),
                model.getPrice(),
                model.getIsbn()
        );
    }

    public static CourseEntity toEntity(Course model) {
        if (model == null)
            return null;

        return new CourseEntity(
                model.getId(),
                model.getTitle(),
                model.getDescription(),
                model.getCost(),
                model.getDuration()
        );
    }

    public static JobEntity toEntity(Job model) {
        if (model == null)
            return null;

        return new JobEntity(
                model.getId(),
                model.getTitle(),
                model.getDescription(),
                model.getLocation(),
                model.getCity(),
                model.getState(),
                model.isActive()
        );
    }

    public static Book fromEntity(BookEntity dataObject) {
        if (dataObject == null)
            return null;

        return new Book(
                dataObject.getId(),
                dataObject.getTitle(),
                dataObject.getDescription(),
                dataObject.getPublisher(),
                dataObject.getPublishDate(),
                dataObject.getPrice(),
                dataObject.getIsbn()
        );
    }

    public static Course fromEntity(CourseEntity dataObject) {
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

    public static Job fromEntity(JobEntity dataObject) {
        if (dataObject == null)
            return null;

        return new Job(
                dataObject.getId(),
                dataObject.getTitle(),
                dataObject.getDescription(),
                dataObject.getLocation(),
                dataObject.getCity(),
                dataObject.getState(),
                dataObject.isActive()
        );
    }

    public static Resume fromEntity(ResumeEntity resume) {
        if (resume == null)
            return null;

        return new Resume(
                resume.getFileName(),
                fromMimeType(resume.getFileType())
        );
    }

    public static User fromEntity(UserEntity dataObject) {
        if (dataObject == null)
            return null;

        return new User(
                dataObject.getId(),
                dataObject.getEmail(),
                dataObject.isManager(),
                fromEntity(dataObject.getResume())
        );
    }
}
