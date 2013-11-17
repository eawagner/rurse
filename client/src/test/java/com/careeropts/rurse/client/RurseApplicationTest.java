package com.careeropts.rurse.client;


import com.careeropts.rurse.model.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

import static java.lang.Integer.MAX_VALUE;
import static org.apache.commons.io.IOUtils.toByteArray;
import static org.junit.Assert.*;

public class RurseApplicationTest {

    private static final String TEST_URL = "http://localhost:8080";

    private static void bookOperations(IManagerOperations ops) {
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

    private static void courseOperations(IManagerOperations ops) {
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

    private static void jobOperations(IManagerOperations ops) {
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
        User user = api.createAccount(email, "password");
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(email, user.getEmail());

        IUserOperations ops = api.userOperations(email, "password");

        assertEquals(0, ops.getRecommendedBooks(0, MAX_VALUE).size());
        assertEquals(0, ops.getRecommendedCourses(0, MAX_VALUE).size());
        assertEquals(0, ops.getRecommendedJobs(0, MAX_VALUE).size());

        File resume = new File("/home/abbot/Desktop/Edward Resume.txt");
        try (FileInputStream resumeStream = new FileInputStream(resume)) {
            ops.uploadResume(resume.getAbsolutePath(), resumeStream);
        }

        user = ops.getUserInfo();
        assertNotNull(user);
        assertNotNull(user.getResume());
        assertEquals(Resume.DocType.TextDocument, user.getResume().getFileType());
        assertEquals(resume.getName(), user.getResume().getFileName());

        String resumeData = new String(toByteArray(ops.getResume()));
        assertNotEquals(0, resumeData.length());

        assertNotEquals(0, ops.getRecommendedBooks(0, MAX_VALUE).size());
        assertNotEquals(0, ops.getRecommendedCourses(0, MAX_VALUE).size());
        assertNotEquals(0, ops.getRecommendedJobs(0, MAX_VALUE).size());

        return user;
    }

    private static void userOperations(RurseApplication api, IManagerOperations ops) throws Exception {
        /* User */
        User user = setupUser(api, "testuser@test.com");
        user = ops.changeAuthorization(user.getId(), true);
        assertTrue(user.isManager());

        assertEquals(user, ops.getUserInfo(user.getId()));
        assertTrue(ops.getUsers(0, MAX_VALUE).contains(user));

        String resumeData = new String(toByteArray(ops.getResume(user.getId())));
        assertNotEquals(0, resumeData.length());

        assertTrue(ops.recommendedUsersForJob(1, 0, MAX_VALUE).contains(user));

        api.userOperations("testuser@test.com", "password").deleteResume();
        user = ops.getUserInfo(user.getId());
        assertNotNull(user);
        assertNull(user.getResume());

        ops.deleteUser(user.getId());
    }

    //Only used to test integration during development
    @Ignore
    @Test
    public void devTest() throws Exception {
        RurseApplication api = new RurseApplication(TEST_URL);

        IManagerOperations ops = api.managerOperations("user@email.com", "password");

        bookOperations(ops);
        courseOperations(ops);
        jobOperations(ops);
        userOperations(api, ops);
    }


}
