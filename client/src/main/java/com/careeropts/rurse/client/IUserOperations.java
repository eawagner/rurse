package com.careeropts.rurse.client;


import com.careeropts.rurse.model.*;

import java.io.InputStream;
import java.util.List;

/**
 * Provides all the operations available to a basic user in RURSE.
 */
public interface IUserOperations {

    /**
     * Retrieves a list of books from the system.
     *
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<Book> getBooks(int page, int pageSize);

    /**
     * Searches for books in the system that match the given keywords.
     *
     * @param keyWords keywords to use for search
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<Book> searchBooks(String keyWords, int page, int pageSize);

    /**
     * Retrieves a book with the given id from the system
     *
     * @param id Id of the book to retrieve information for.
     */
    public Book getBook(long id);

    /**
     * Retrieves a list of courses from the system.
     *
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<Course> getCourses(int page, int pageSize);

    /**
     * Searches for courses in the system that match the given keywords.
     *
     * @param keyWords keywords to use for search
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<Course> searchCourses(String keyWords, int page, int pageSize);

    /**
     * Retrieves a course with the given id from the system
     *
     * @param id Id of the course to retrieve information for.
     */
    public Course getCourse(long id);

    /**
     * Retrieves a list of jobs from the system.
     *
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<Job> getJobs(int page, int pageSize);

    /**
     * Searches for jobs in the system that match the given keywords.
     *
     * @param keyWords keywords to use for search
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<Job> searchJobs(String keyWords, int page, int pageSize);

    /**
     * Retrieves a job with the given id from the system
     *
     * @param id Id of the job to retrieve information for.
     */
    public Job getJob(long id);

    /**
     * Retrieves information for the current user from the system.
     */
    public User getUserInfo();

    /**
     * Retrieves the resume for the current user from the system.
     */
    public InputStream getResume();

    /**
     * Uploads a resume for the current user into the system. This will overwrite any resume currently loaded for the user.
     *
     * @param fileName File name of the resume file being uploaded
     * @param resume {@link InputStream} containing the resume data to be saved to the system.
     */
    public Resume uploadResume(String fileName, InputStream resume);

    /**
     * Deletes the resume for the current user from the system.
     */
    public void deleteResume();

    /**
     * Retrieves a list of books that are recommended for the user based on the resume currently in the system.  Results are sorted
     * so that best matches appear first in the list.
     *
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<Book> getRecommendedBooks(int page, int pageSize);

    /**
     * Retrieves a list of courses that are recommended for the user based on the resume currently in the system.  Results are sorted
     * so that best matches appear first in the list.
     *
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<Course> getRecommendedCourses(int page, int pageSize);

    /**
     * Retrieves a list of jobs that are recommended for the user based on the resume currently in the system.  Results are sorted
     * so that best matches appear first in the list.
     *
     * @param page Used for paging results.  This represents which page to use.  Number is zero based meaning the first page is 0.
     * @param pageSize Size to use for each page of the results
     */
    public List<Job> getRecommendedJobs(int page, int pageSize);

    /**
     * Changes the password for the current user.
     *
     * WARNING!!!
     * This operation will require that all other operation instances for this user be reacquired from the API.
     *
     * @param password New password for the user.
     */
    public void changePassword(String password);
}
