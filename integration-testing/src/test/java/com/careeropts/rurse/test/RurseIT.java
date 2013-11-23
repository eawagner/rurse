package com.careeropts.rurse.test;


import com.careeropts.rurse.client.IManagerOperations;
import com.careeropts.rurse.client.IUserOperations;
import com.careeropts.rurse.client.RurseApplication;
import com.careeropts.rurse.model.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static java.lang.Integer.MAX_VALUE;
import static org.apache.commons.io.IOUtils.toByteArray;
import static org.junit.Assert.*;

public class RurseIT {

    private static final String TEST_USER_EMAIL = "test@test.com";
    private static final String TEST_USER_PASS = "password";

    public static RurseApplication api;

    public static IManagerOperations ops;

    @BeforeClass
    public static void setup() {
        api = new RurseApplication("http://localhost:8888");
        ops = api.managerOperations("admin@test.com", "password");
    }

    @Test
    public void changePassword() {
        ops.changePassword("newpass");
        ops = api.managerOperations("admin@test.com", "newpass");
        assertNotNull(ops.getUserInfo());
    }

    @Test
    public void bookOperations(){
        /* Books */
        Book book = ops.saveBook(new Book(null, "title", "description", "publisher", null, 100.56788, "978-1-4028-9462-6"));
        assertNotNull(book);
        assertNotNull(book.getId());

        book.setTitle("Updated Title");
        book.setDescription("Updated Description");
        book = ops.updateBook(book);

        assertEquals(book, ops.getBook(book.getId()));

        assertTrue(ops.getBooks(0, MAX_VALUE).contains(book));
        assertTrue(ops.searchBooks("title", 0, MAX_VALUE).contains(book));
        assertFalse(ops.searchBooks("bad", 0, MAX_VALUE).contains(book));

        ops.deleteBook(book.getId());
    }

    @Test
    public void courseOperations(){
        /* Courses */
        Course course = ops.saveCourse(new Course(null, "title", "description", 100.56788, "2 weeks"));
        assertNotNull(course);
        assertNotNull(course.getId());

        course.setTitle("Updated Title");
        course.setDescription("Updated Description");
        course = ops.updateCourse(course);

        assertEquals(course, ops.getCourse(course.getId()));

        assertTrue(ops.getCourses(0, MAX_VALUE).contains(course));
        assertTrue(ops.searchCourses("title", 0, MAX_VALUE).contains(course));
        assertFalse(ops.searchCourses("bad", 0, MAX_VALUE).contains(course));

        ops.deleteCourse(course.getId());
    }

    @Test
    public void jobOperations(){
        /* Jobs */
        Job job = ops.saveJob(new Job(null, "title", "description", "location", "Adelphi", "MD", true));
        assertNotNull(job);
        assertNotNull(job.getId());

        job.setTitle("Updated Title");
        job.setDescription("Updated Description");
        job = ops.updateJob(job);

        assertEquals(job, ops.getJob(job.getId()));

        assertTrue(ops.getJobs(0, MAX_VALUE).contains(job));
        assertTrue(ops.searchJobs("title", 0, MAX_VALUE).contains(job));
        assertFalse(ops.searchJobs("bad", 0, MAX_VALUE).contains(job));

        ops.deleteJob(job.getId());
    }

    private static User setupUser(RurseApplication api, String email) throws Exception {
        User user = api.createAccount(email, TEST_USER_PASS);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(email, user.getEmail());

        IUserOperations ops = api.userOperations(email, TEST_USER_PASS);

        //Make sure there are no recommendations
        assertEquals(0, ops.getRecommendedBooks(0, MAX_VALUE).size());
        assertEquals(0, ops.getRecommendedCourses(0, MAX_VALUE).size());
        assertEquals(0, ops.getRecommendedJobs(0, MAX_VALUE).size());

        ops.uploadResume("resume.txt", new ByteArrayInputStream("Searchable test resume".getBytes()));

        user = ops.getUserInfo();
        assertNotNull(user);
        assertNotNull(user.getResume());
        assertEquals(Resume.DocType.TextDocument, user.getResume().getFileType());
        assertEquals("resume.txt", user.getResume().getFileName());

        String resumeData = new String(toByteArray(ops.getResume()));
        assertEquals("Searchable test resume", resumeData);

        //Assert there are now recommendations
        assertNotEquals(0, ops.getRecommendedBooks(0, MAX_VALUE).size());
        assertNotEquals(0, ops.getRecommendedCourses(0, MAX_VALUE).size());
        assertNotEquals(0, ops.getRecommendedJobs(0, MAX_VALUE).size());

        return user;
    }

    @Test
    public void userOperations() throws Exception {

        Book book = ops.saveBook(new Book(null, "title", "searchable for testing", "publisher", null, 100.56788, "978-1-4028-9462-6"));
        Course course = ops.saveCourse(new Course(null, "title", "searchable for testing", 100.56788, "2 weeks"));
        Job job = ops.saveJob(new Job(null, "title", "searchable for testing", "location", "Adelphi", "MD", true));

        User user = setupUser(api, TEST_USER_EMAIL);

        user = ops.changeAuthorization(user.getId(), true);
        assertTrue(user.isManager());

        assertEquals(user, ops.getUserInfo(user.getId()));
        assertTrue(ops.getUsers(0, MAX_VALUE).contains(user));

        String resumeData = new String(toByteArray(ops.getResume(user.getId())));
        assertEquals("Searchable test resume", resumeData);

        assertTrue(ops.recommendedUsersForJob(job.getId(), 0, MAX_VALUE).contains(user));

        api.userOperations(TEST_USER_EMAIL, TEST_USER_PASS).deleteResume();
        user = ops.getUserInfo(user.getId());
        assertNotNull(user);
        assertNull(user.getResume());

        assertFalse(ops.recommendedUsersForJob(job.getId(), 0, MAX_VALUE).contains(user));

        ops.deleteUser(user.getId());

        ops.deleteBook(book.getId());
        ops.deleteCourse(course.getId());
        ops.deleteJob(job.getId());
    }

    @AfterClass
    public static void teardown() throws IOException {
        api.close();
    }

}
