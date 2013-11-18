package com.careeropts.rurse.web.service.impl;


import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.dao.object.*;
import com.careeropts.rurse.dao.recommend.IJobRecommendEngine;
import com.careeropts.rurse.dao.recommend.IUserRecommendEngine;
import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.model.User;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.NotFoundException;
import com.careeropts.rurse.web.service.IRecommendationService;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.Collections;
import java.util.List;

import static com.careeropts.rurse.model.Resume.DocType.TextDocument;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class RecommendationServiceTest {

    private static String TEST_EMAIL = "test@email.com";

    private static void setCurrentUser(String email) {
        SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(email, "password", Collections.<GrantedAuthority>emptyList()));
        SecurityContextHolder.setContext(context);
    }

    private static IRecommendationService getRecommendService(IUserDao dao, IUserRecommendEngine engine) {
        //clear context
        SecurityContextHolder.setContext(new SecurityContextImpl());
        return new RecommendationService(dao, engine, null, null);
    }

    private static IRecommendationService getRecommendService(IJobDao dao, IJobRecommendEngine engine) {
        //clear context
        SecurityContextHolder.setContext(new SecurityContextImpl());
        return new RecommendationService(null, null, dao, engine);
    }

    private static UserEntity genUserEntity(Long id, String email, String password, boolean manager, ResumeEntity resume) {
        UserEntity user = new UserEntity(email, password, manager, resume);
        user.setId(id);
        return user;
    }

    @Test
    public void recommendBooksForCurrentUserTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        ResumeEntity resume = new ResumeEntity("resume.txt", TextDocument.getMimeType(), "Resume Data".getBytes());

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, resume));
        when(engine.recommendBooks(any(ResumeEntity.class), anyInt(), anyInt())).thenReturn(
                asList(new BookEntity(1L, "title", "description", null, null, null, null))
        );

        List<Book> output = service.recommendBooksForCurrentUser (0, 100);

        verify(dao).getByEmail(TEST_EMAIL);
        verify(engine).recommendBooks(resume, 0, 100);

        assertNotNull(output);
        assertEquals(1, output.size());
        assertEquals(new Long(1), output.get(0).getId());
    }

    @Test(expected = InternalServerError.class)
    public void recommendBooksForCurrentUserNoAuthTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);

        service.recommendBooksForCurrentUser (0, 100);
    }

    @Test(expected = InternalServerError.class)
    public void recommendBooksForCurrentUserNoUserTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(null);

        service.recommendBooksForCurrentUser (0, 100);
    }

    @Test
    public void recommendBooksForCurrentUserNoResumeTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, null));

        List<Book> output = service.recommendBooksForCurrentUser (0, 100);

        verify(dao).getByEmail(TEST_EMAIL);

        assertNotNull(output);
        assertEquals(0, output.size());
    }

    @Test
    public void recommendBooksForCurrentUserNormalizeNullTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        ResumeEntity resume = new ResumeEntity("resume.txt", TextDocument.getMimeType(), "Resume Data".getBytes());

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, resume));

        service.recommendBooksForCurrentUser (null, null);

        verify(dao).getByEmail(TEST_EMAIL);
        verify(engine).recommendBooks(resume, 0, 50);
    }

    @Test
    public void recommendBooksForCurrentUserNormalizeNegTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        ResumeEntity resume = new ResumeEntity("resume.txt", TextDocument.getMimeType(), "Resume Data".getBytes());

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, resume));

        service.recommendBooksForCurrentUser (null, null);

        verify(dao).getByEmail(TEST_EMAIL);
        verify(engine).recommendBooks(resume, 0, 50);
    }

    @Test
    public void recommendCoursesForCurrentUserTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        ResumeEntity resume = new ResumeEntity("resume.txt", TextDocument.getMimeType(), "Resume Data".getBytes());

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, resume));
        when(engine.recommendCourses(any(ResumeEntity.class), anyInt(), anyInt())).thenReturn(
                asList(new CourseEntity(1L, "title", "description", null, null))
        );

        List<Course> output = service.recommendCoursesForCurrentUser(0, 100);

        verify(dao).getByEmail(TEST_EMAIL);
        verify(engine).recommendCourses(resume, 0, 100);

        assertNotNull(output);
        assertEquals(1, output.size());
        assertEquals(new Long(1), output.get(0).getId());
    }

    @Test(expected = InternalServerError.class)
    public void recommendCoursesForCurrentUserNoAuthTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);

        service.recommendCoursesForCurrentUser(0, 100);
    }

    @Test(expected = InternalServerError.class)
    public void recommendCoursesForCurrentUserNoUserTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(null);

        service.recommendCoursesForCurrentUser(0, 100);
    }

    @Test
    public void recommendCoursesForCurrentUserNoResumeTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, null));

        List<Course> output = service.recommendCoursesForCurrentUser(0, 100);

        verify(dao).getByEmail(TEST_EMAIL);

        assertNotNull(output);
        assertEquals(0, output.size());
    }

    @Test
    public void recommendCoursesForCurrentUserNormalizeNullTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        ResumeEntity resume = new ResumeEntity("resume.txt", TextDocument.getMimeType(), "Resume Data".getBytes());

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, resume));

        service.recommendCoursesForCurrentUser(null, null);

        verify(dao).getByEmail(TEST_EMAIL);
        verify(engine).recommendCourses(resume, 0, 50);
    }

    @Test
    public void recommendCoursesForCurrentUserNormalizeNegTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        ResumeEntity resume = new ResumeEntity("resume.txt", TextDocument.getMimeType(), "Resume Data".getBytes());

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, resume));

        service.recommendCoursesForCurrentUser(null, null);

        verify(dao).getByEmail(TEST_EMAIL);
        verify(engine).recommendCourses(resume, 0, 50);
    }

    @Test
    public void recommendJobsForCurrentUserTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        ResumeEntity resume = new ResumeEntity("resume.txt", TextDocument.getMimeType(), "Resume Data".getBytes());

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, resume));
        when(engine.recommendJobs(any(ResumeEntity.class), anyInt(), anyInt())).thenReturn(
                asList(new JobEntity(1L, "title", "description", null, null, null, true))
        );

        List<Job> output = service.recommendJobsForCurrentUser(0, 100);

        verify(dao).getByEmail(TEST_EMAIL);
        verify(engine).recommendJobs(resume, 0, 100);

        assertNotNull(output);
        assertEquals(1, output.size());
        assertEquals(new Long(1), output.get(0).getId());
    }

    @Test(expected = InternalServerError.class)
    public void recommendJobsForCurrentUserNoAuthTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);

        service.recommendJobsForCurrentUser(0, 100);
    }

    @Test(expected = InternalServerError.class)
    public void recommendJobsForCurrentUserNoUserTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(null);

        service.recommendJobsForCurrentUser(0, 100);
    }

    @Test
    public void recommendJobsForCurrentUserNoResumeTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, null));

        List<Job> output = service.recommendJobsForCurrentUser(0, 100);

        verify(dao).getByEmail(TEST_EMAIL);

        assertNotNull(output);
        assertEquals(0, output.size());
    }

    @Test
    public void recommendJobsForCurrentUserNormalizeNullTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        ResumeEntity resume = new ResumeEntity("resume.txt", TextDocument.getMimeType(), "Resume Data".getBytes());

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, resume));

        service.recommendJobsForCurrentUser(null, null);

        verify(dao).getByEmail(TEST_EMAIL);
        verify(engine).recommendJobs(resume, 0, 50);
    }

    @Test
    public void recommendJobsForCurrentUserNormalizeNegTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserRecommendEngine engine = mock(IUserRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);
        setCurrentUser(TEST_EMAIL);

        ResumeEntity resume = new ResumeEntity("resume.txt", TextDocument.getMimeType(), "Resume Data".getBytes());

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, resume));

        service.recommendJobsForCurrentUser(null, null);

        verify(dao).getByEmail(TEST_EMAIL);
        verify(engine).recommendJobs(resume, 0, 50);
    }




    @Test
    public void recommendUsersForJobTest() {
        IJobDao dao = mock(IJobDao.class);
        IJobRecommendEngine engine = mock(IJobRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);

        JobEntity job = new JobEntity(1L, "title", "description", "location", "city", "state", true);

        when(dao.getSingle(1L)).thenReturn(job);
        when(engine.recommendUsers(any(JobEntity.class), anyInt(), anyInt())).thenReturn(
                asList(new UserEntity(TEST_EMAIL, "password", false, new ResumeEntity("resume.txt", TextDocument.getMimeType(), "Resume Data".getBytes())))
        );

        List<User> output = service.recommendUsersForJob(1L, 0, 100);

        verify(dao).getSingle(1L);
        verify(engine).recommendUsers(job, 0, 100);

        assertNotNull(output);
        assertEquals(1, output.size());
        assertEquals(TEST_EMAIL, output.get(0).getEmail());
    }

    @Test(expected = NotFoundException.class)
    public void recommendUsersForJobNullIdTest() {
        IJobDao dao = mock(IJobDao.class);
        IJobRecommendEngine engine = mock(IJobRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);

        service.recommendUsersForJob(null, 0, 100);
    }

    @Test(expected = NotFoundException.class)
    public void recommendUsersForJobNoDataTest() {
        IJobDao dao = mock(IJobDao.class);
        IJobRecommendEngine engine = mock(IJobRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);

        when(dao.getSingle(1L)).thenReturn(null);

        service.recommendUsersForJob(null, 0, 100);
    }

    @Test
    public void recommendUsersForJobNormalizeNullTest() {
        IJobDao dao = mock(IJobDao.class);
        IJobRecommendEngine engine = mock(IJobRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);

        JobEntity job = new JobEntity(1L, "title", "description", "location", "city", "state", true);

        when(dao.getSingle(1L)).thenReturn(job);

        service.recommendUsersForJob(1L, null, null);

        verify(dao).getSingle(1L);
        verify(engine).recommendUsers(job, 0, 50);
    }

    @Test
    public void recommendUsersForJobNormalizeNegTest() {
        IJobDao dao = mock(IJobDao.class);
        IJobRecommendEngine engine = mock(IJobRecommendEngine.class);
        IRecommendationService service = getRecommendService(dao, engine);

        JobEntity job = new JobEntity(1L, "title", "description", "location", "city", "state", true);

        when(dao.getSingle(1L)).thenReturn(job);

        service.recommendUsersForJob(1L, -1, -1);

        verify(dao).getSingle(1L);
        verify(engine).recommendUsers(job, 0, 50);
    }
}
