package com.careeropts.rurse.client;


import com.careeropts.rurse.model.*;

import java.io.InputStream;
import java.util.List;

public interface IUserOperations {


    public List<Book> getBooks();
    public List<Book> getBooks(int page, int pageSize);
    public List<Book> searchBooks(String keyWords);
    public List<Book> searchBooks(String keyWords, int page, int pageSize);
    public Book getBook(long id);

    public List<Course> getCourses();
    public List<Course> getCourses(int page, int pageSize);
    public List<Course> searchCourses(String keyWords);
    public List<Course> searchCourses(String keyWords, int page, int pageSize);
    public Course getCourse(long id);

    public List<Job> getJobs();
    public List<Job> getJobs(int page, int pageSize);
    public List<Job> searchJobs(String keyWords);
    public List<Job> searchJobs(String keyWords, int page, int pageSize);
    public Job getJob(long id);

    public User getUserInfo();
    public InputStream getResume();
    public Resume uploadResume(String fileName, InputStream resume);
    public void deleteResume();

    public List<Book> getRecommendedBooks();
    public List<Book> getRecommendedBooks(int page, int pageSize);
    public List<Course> getRecommendedCourses();
    public List<Course> getRecommendedCourses(int page, int pageSize);
    public List<Job> getRecommendedJobs();
    public List<Job> getRecommendedJobs(int page, int pageSize);
}
