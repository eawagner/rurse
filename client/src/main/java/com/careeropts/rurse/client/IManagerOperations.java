package com.careeropts.rurse.client;


import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.model.User;

import java.io.InputStream;
import java.util.List;

public interface IManagerOperations extends IUserOperations {

    public Book saveBook(Book book);
    public Book updateBook(Book book);
    public void deleteBook(long id);

    public Course saveCourse(Course course);
    public Course updateCourse(Course course);
    public void deleteCourse(long id);

    public Job saveJob(Job job);
    public Job updateJob(Job job);
    public void deleteJob(long id);
    public List<User> recommendedUsersForJob(long id);

    public List<User> getUsers();
    public List<User> getUsers(int page, int pageSize);
    public List<User> searchUsers(String keyWords);
    public List<User> searchUsers(String keyWords, int page, int pageSize);
    public User getUserInfo(long id);
    public InputStream getResume(long id);

    public User changeAuthorization(long id, boolean makeManager);
    public void deleteUser(long id);
}
