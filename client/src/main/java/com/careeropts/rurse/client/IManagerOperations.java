package com.careeropts.rurse.client;


import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.model.User;

import java.io.InputStream;
import java.util.List;

/**
 * Provides all the operations available to a manager in RURSE.
 */
public interface IManagerOperations extends IUserOperations {

    /**
     * Saves a new book to the system. The id will be ignored and the system will generate a new value.
     *
     * @param book Book to save
     */
    public Book saveBook(Book book);

    /**
     * Updates a book in the system with the id of the book provided.
     * @param book Book to update
     */
    public Book updateBook(Book book);

    /**
     * Deletes the book with the given id from the system.
     * @param id Id of the book to delete
     */
    public void deleteBook(long id);

    /**
     * Saves a new course to the system. The id will be ignored and the system will generate a new value.
     *
     * @param course Course to save
     */
    public Course saveCourse(Course course);

    /**
     * Updates a course in the system with the id of the course provided.
     * @param course Course to update
     */
    public Course updateCourse(Course course);

    /**
     * Deletes the course with the given id from the system.
     * @param id Id of the course to delete
     */
    public void deleteCourse(long id);

    /**
     * Saves a new job to the system. The id will be ignored and the system will generate a new value.
     *
     * @param job Job to save
     */
    public Job saveJob(Job job);

    /**
     * Updates a job in the system with the id of the job provided.
     *
     * @param job Job to update
     */
    public Job updateJob(Job job);

    /**
     * Deletes the job with the given id from the system.
     *
     * @param id Id of the job to delete
     */
    public void deleteJob(long id);

    /**
     * Retrieves a list of users with resumes that are recommended for the given job.  Results are sorted
     * so that best matches appear first in the list.
     *
     * @param jobId Id of job to find recommendations for
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<User> recommendedUsersForJob(long jobId, int page, int pageSize);

    /**
     * Retrieves a list of users from the system.
     *
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<User> getUsers(int page, int pageSize);

    /**
     * Searches for users in the system with resumes that match the given keywords.
     *
     * @param keyWords keywords to use for search
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<User> searchUsers(String keyWords, int page, int pageSize);

    /**
     * Retrieves a user with the given id from the system
     *
     * @param id Id of the user to retrieve information for.
     */
    public User getUserInfo(long id);

    /**
     * Retrieves the resume file for the user with the given id from the system
     *
     * @param id Id of the user to retrieve the resume for.
     */
    public InputStream getResume(long id);

    /**
     * Changes the authorizations of the user with the given id in the system.
     * @param id Id of the user to change the authorizations for.
     * @param makeManager If true the given user will have manager priviliges in the system.
     */
    public User changeAuthorization(long id, boolean makeManager);

    /**
     * Deletes the user with the given id from the system.
     *
     * @param id Id of the user to delete
     */
    public void deleteUser(long id);
}
