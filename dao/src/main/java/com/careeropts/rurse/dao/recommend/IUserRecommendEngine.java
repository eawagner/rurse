package com.careeropts.rurse.dao.recommend;

import com.careeropts.rurse.dao.object.BookEntity;
import com.careeropts.rurse.dao.object.CourseEntity;
import com.careeropts.rurse.dao.object.JobEntity;
import com.careeropts.rurse.dao.object.ResumeEntity;

import java.util.List;

public interface IUserRecommendEngine {

    public List<BookEntity> recommendBooks(ResumeEntity resume, int pageNum, int perPage);

    public List<CourseEntity> recommendCourses(ResumeEntity resume, int pageNum, int perPage);

    public List<JobEntity> recommendJobs(ResumeEntity resume, int pageNum, int perPage);
}
