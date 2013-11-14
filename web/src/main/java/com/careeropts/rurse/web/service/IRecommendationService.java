package com.careeropts.rurse.web.service;

import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.model.User;

import java.util.List;

public interface IRecommendationService {

    public List<Book> recommendBooksForCurrentUser(Integer pageNum, Integer perPage);

    public List<Course> recommendCoursesForCurrentUser(Integer pageNum, Integer perPage);

    public List<Job> recommendJobsForCurrentUser(Integer pageNum, Integer perPage);

    public List<User> recommendUsersForJob(Long jobId, Integer pageNum, Integer perPage);
}
