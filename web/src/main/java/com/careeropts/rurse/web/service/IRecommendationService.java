package com.careeropts.rurse.web.service;

import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.model.User;

import java.util.List;

/**
 * Interface responsible for all business logic for providing recommendations to link various types of data in the system.
 */
public interface IRecommendationService {

    /**
     * Queries the system for recommended books for the current authenticated user.
     *
     * @param pageNum The page number of the results to be retrieved.
     * @param perPage The number of results per page.
     */
    public List<Book> recommendBooksForCurrentUser(Integer pageNum, Integer perPage);

    /**
     * Queries the system for recommended courses for the current authenticated user.
     *
     * @param pageNum The page number of the results to be retrieved.
     * @param perPage The number of results per page.
     */
    public List<Course> recommendCoursesForCurrentUser(Integer pageNum, Integer perPage);

    /**
     * Queries the system for recommended jobs for the current authenticated user.
     *
     * @param pageNum The page number of the results to be retrieved.
     * @param perPage The number of results per page.
     */
    public List<Job> recommendJobsForCurrentUser(Integer pageNum, Integer perPage);

    /**
     * Queries the system for all recommended users for the specified job.
     *
     * @param pageNum The page number of the results to be retrieved.
     * @param perPage The number of results per page.
     */
    public List<User> recommendUsersForJob(Long jobId, Integer pageNum, Integer perPage);
}
