package com.careeropts.rurse.web.service.impl;

import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.dao.object.BookEntity;
import com.careeropts.rurse.dao.object.CourseEntity;
import com.careeropts.rurse.dao.object.JobEntity;
import com.careeropts.rurse.dao.object.UserEntity;
import com.careeropts.rurse.dao.recommend.IJobRecommendEngine;
import com.careeropts.rurse.dao.recommend.IUserRecommendEngine;
import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.model.User;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.NotFoundException;
import com.careeropts.rurse.web.service.IRecommendationService;
import com.google.common.base.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.careeropts.rurse.web.service.util.EntityTransform.fromEntity;
import static com.careeropts.rurse.web.service.util.SecurityUtils.getAuthenticatedUser;
import static com.google.common.collect.Lists.transform;
import static java.util.Collections.emptyList;

@Service
@Transactional(readOnly = true)
public class RecommendationService implements IRecommendationService {

    private final IUserDao userDao;

    private final IUserRecommendEngine userRecommendEngine;

    private final IJobDao jobDao;

    private final IJobRecommendEngine jobRecommendEngine;

    @Autowired
    public RecommendationService(
            IUserDao userDao,
            IUserRecommendEngine userRecommendEngine,
            IJobDao jobDao,
            IJobRecommendEngine jobRecommendEngine) {

        this.userDao = userDao;
        this.userRecommendEngine = userRecommendEngine;
        this.jobDao = jobDao;
        this.jobRecommendEngine = jobRecommendEngine;
    }

    private UserEntity getCurrentUser() {
        //retrieve the user email from the security context.
        String current = getAuthenticatedUser();
        if (current == null)
            throw new InternalServerError("Unable to find an authenticated user.");

        UserEntity user = userDao.getByEmail(current);

        if (user == null)
            throw new InternalServerError("Unable to find the authorized user.");

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> recommendBooksForCurrentUser(Integer pageNum, Integer perPage) {
        if (pageNum == null || pageNum < 0)
            pageNum = 0;

        if (perPage == null || perPage < 0)
            perPage = 50;

        UserEntity user = getCurrentUser();
        if (user.getResume() == null)
            return emptyList();

        return transform(userRecommendEngine.recommendBooks(user.getResume(), pageNum, perPage),
                new Function<BookEntity, Book>() {
                    @Override
                    public Book apply(BookEntity book) {
                        return fromEntity(book);
                    }
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Course> recommendCoursesForCurrentUser(Integer pageNum, Integer perPage) {
        if (pageNum == null || pageNum < 0)
            pageNum = 0;

        if (perPage == null || perPage < 0)
            perPage = 50;

        UserEntity user = getCurrentUser();
        if (user.getResume() == null)
            return emptyList();

        return transform(userRecommendEngine.recommendCourses(user.getResume(), pageNum, perPage),
                new Function<CourseEntity, Course>() {
                    @Override
                    public Course apply(CourseEntity book) {
                        return fromEntity(book);
                    }
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Job> recommendJobsForCurrentUser(Integer pageNum, Integer perPage) {
        if (pageNum == null || pageNum < 0)
            pageNum = 0;

        if (perPage == null || perPage < 0)
            perPage = 50;

        UserEntity user = getCurrentUser();
        if (user.getResume() == null)
            return emptyList();

        return transform(userRecommendEngine.recommendJobs(user.getResume(), pageNum, perPage),
                new Function<JobEntity, Job>() {
                    @Override
                    public Job apply(JobEntity book) {
                        return fromEntity(book);
                    }
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> recommendUsersForJob(Long jobId, Integer pageNum, Integer perPage) {
        if (jobId == null)
            throw new NotFoundException();

        if (pageNum == null || pageNum < 0)
            pageNum = 0;

        if (perPage == null || perPage < 0)
            perPage = 50;

        JobEntity job = jobDao.getSingle(jobId);

        if (job == null)
            throw new NotFoundException();

        return transform(jobRecommendEngine.recommendUsers(job, pageNum, perPage),
                new Function<UserEntity, User>() {
                    @Override
                    public User apply(UserEntity user) {
                        return fromEntity(user);
                    }
                });
    }
}
