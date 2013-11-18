package com.careeropts.rurse.web.service.impl;


import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.dao.object.ResumeEntity;
import com.careeropts.rurse.dao.object.UserEntity;
import com.careeropts.rurse.model.Resume;
import com.careeropts.rurse.model.User;
import com.careeropts.rurse.web.exception.BadRequestException;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.NotFoundException;
import com.careeropts.rurse.web.service.IUserService;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.List;

import static com.careeropts.rurse.model.Resume.DocType.TextDocument;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private static String TEST_EMAIL = "test@email.com";

    private static void setCurrentUser(String email) {
        SecurityContext context = new SecurityContextImpl();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(email, "password", Collections.<GrantedAuthority>emptyList()));
        SecurityContextHolder.setContext(context);
    }

    private static IUserService getUserService(IUserDao userDao) {
        return getUserService(userDao, null);
    }

    private static IUserService getUserService(IUserDao userDao, PasswordEncoder encoder) {
        //clear context
        SecurityContextHolder.setContext(new SecurityContextImpl());
        return new UserService(userDao, encoder);
    }

    private static UserEntity genUserEntity(Long id, String email, String password, boolean manager, ResumeEntity resume) {
        UserEntity user = new UserEntity(email, password, manager, resume);
        user.setId(id);
        return user;
    }

    @Test
    public void createAccountTest() {
        IUserDao dao = mock(IUserDao.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        IUserService service = getUserService(dao, encoder);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(null);
        when(dao.save(any(UserEntity.class))).then(returnsFirstArg());
        when(encoder.encode("password")).thenReturn("password");

        User user = service.createAccount(TEST_EMAIL, "password");

        verify(dao).getByEmail(TEST_EMAIL);
        verify(dao).save(any(UserEntity.class));
        verify(encoder).encode("password");

        assertEquals(TEST_EMAIL, user.getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void createAccountInvalidEmailTest() {
        IUserDao dao = mock(IUserDao.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        IUserService service = getUserService(dao, encoder);

        service.createAccount("bad", "password");

    }

    @Test(expected = BadRequestException.class)
    public void createAccountAlreadExistsTest() {
        IUserDao dao = mock(IUserDao.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        IUserService service = getUserService(dao, encoder);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(new UserEntity(TEST_EMAIL, "password", false, null));

        service.createAccount(TEST_EMAIL, "password");
    }

    @Test(expected = BadRequestException.class)
    public void createAccountInvalidPasswordTest() {
        IUserDao dao = mock(IUserDao.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        IUserService service = getUserService(dao, encoder);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(null);

        service.createAccount(TEST_EMAIL, "");
    }

    @Test(expected = InternalServerError.class)
    public void createAccountSaveProblemTest() {
        IUserDao dao = mock(IUserDao.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        IUserService service = getUserService(dao, encoder);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(null);
        when(dao.save(any(UserEntity.class))).thenReturn(null);
        when(encoder.encode("password")).thenReturn("password");

        service.createAccount(TEST_EMAIL, "password");
    }

    @Test
    public void getUserTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getSingle(1L)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, null));

        User user = service.getUser(1L);

        verify(dao).getSingle(1L);

        assertEquals(new Long(1), user.getId());
        assertEquals(TEST_EMAIL, user.getEmail());
    }

    @Test(expected = NotFoundException.class)
    public void getUserNullIdTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        service.getUser(null);
    }

    @Test(expected = NotFoundException.class)
    public void getUserNotFoundTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getSingle(1L)).thenReturn(null);

        service.getUser(1L);
    }

    @Test
    public void getCurrentUserTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, null));

        User user = service.getCurrentUser();

        verify(dao).getByEmail(TEST_EMAIL);

        assertEquals(new Long(1), user.getId());
        assertEquals(TEST_EMAIL, user.getEmail());
    }

    @Test(expected = InternalServerError.class)
    public void getCurrentUserNoAuthTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        service.getCurrentUser();
    }

    @Test(expected = InternalServerError.class)
    public void getCurrentUserNotFoundTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(null);

        service.getCurrentUser();
    }

    @Test
    public void getResumeResponseTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getSingle(1L)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, new ResumeEntity("test.xml", "text/plain", "Resume text".getBytes()))
        );

        Response response = service.getResumeResponse(1L);

        verify(dao).getSingle(1L);

        assertArrayEquals("Resume text".getBytes(), (byte[]) response.getEntity());
        assertEquals("text/plain", response.getMetadata().getFirst("Content-Type").toString());
    }

    @Test(expected = NotFoundException.class)
    public void getResumeResponseNullIdTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        service.getResumeResponse(null);
    }

    @Test(expected = NotFoundException.class)
    public void getResumeResponseUserNotFoundTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getSingle(1L)).thenReturn(null);

        service.getResumeResponse(1L);
    }

    @Test(expected = NotFoundException.class)
    public void getResumeResponseNotFoundTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getSingle(1L)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, null)
        );

        service.getResumeResponse(1L);
    }

    @Test
    public void getCurrentResumeResponseTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, new ResumeEntity("test.xml", "text/plain", "Resume text".getBytes()))
        );

        Response response = service.getResumeResponse();

        verify(dao).getByEmail(TEST_EMAIL);

        assertArrayEquals("Resume text".getBytes(), (byte[]) response.getEntity());
        assertEquals("text/plain", response.getMetadata().getFirst("Content-Type").toString());
    }

    @Test(expected = InternalServerError.class)
    public void getCurrentResumeResponseNullIdTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        service.getResumeResponse();
    }

    @Test(expected = InternalServerError.class)
    public void getCurrentResumeResponseUserNotFoundTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(null);

        service.getResumeResponse();
    }

    @Test(expected = NotFoundException.class)
    public void getCurrentResumeResponseNotFoundTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, null)
        );

        service.getResumeResponse();
    }

    @Test
    public void queryAllTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getAll(anyInt(), anyInt())).thenReturn(
                asList(genUserEntity(1L, TEST_EMAIL, "", false, new ResumeEntity("test.xml", "text/plain", "Resume text".getBytes())))
        );

        List<User> users = service.query(null, 0, 100);

        verify(dao).getAll(0, 100);
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(TEST_EMAIL, users.get(0).getEmail());
    }

    @Test
    public void querySearchTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.search(anyString(), anyInt(), anyInt())).thenReturn(
                asList(genUserEntity(1L, TEST_EMAIL, "", false, new ResumeEntity("test.xml", "text/plain", "Resume text".getBytes())))
        );

        List<User> users = service.query("search", 0, 100);

        verify(dao).search("search", 0, 100);
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals(TEST_EMAIL, users.get(0).getEmail());
    }

    @Test
    public void queryNormalizeNullTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        service.query(null, null, null);
        verify(dao).getAll(0, 50);
    }

    @Test
    public void queryNormalizeNegTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        service.query(null, -1, -1);
        verify(dao).getAll(0, 50);
    }

    @Test
    public void deleteTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getSingle(1L)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, null)
        );
        when(dao.delete(any(UserEntity.class))).thenReturn(true);

        service.delete(1L);

        verify(dao).getSingle(1L);
        verify(dao).delete(any(UserEntity.class));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNullIdTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        service.delete(null);

    }

    @Test(expected = NotFoundException.class)
    public void deleteUserNotFoundTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getSingle(1L)).thenReturn(null);

        service.delete(1L);
    }

    @Test(expected = NotFoundException.class)
    public void deleteDaoCounldFindUserTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getSingle(1L)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, null)
        );
        when(dao.delete(any(UserEntity.class))).thenReturn(false);

        service.delete(1L);
    }

    @Test
    public void updateAuthsTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getSingle(1L)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, null)
        );
        when(dao.update(any(UserEntity.class))).then(returnsFirstArg());

        User user = service.updateAuths(1L, true);

        verify(dao).getSingle(1L);
        verify(dao).update(any(UserEntity.class));
        assertNotNull(user);
        assertEquals(TEST_EMAIL, user.getEmail());
        assertTrue(user.isManager());
    }

    @Test(expected = NotFoundException.class)
    public void updateAuthsNullIdTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        service.updateAuths(null, true);
    }

    @Test(expected = NotFoundException.class)
    public void updateAuthsUserNotFoundTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getSingle(1L)).thenReturn(null);

        service.updateAuths(1L, true);
    }

    @Test(expected = InternalServerError.class)
    public void updateAuthsUpdateFailTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        when(dao.getSingle(1L)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, null)
        );
        when(dao.update(any(UserEntity.class))).thenReturn(null);

        service.updateAuths(1L, true);
    }

    @Test
    public void changePasswordTest() {
        IUserDao dao = mock(IUserDao.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        IUserService service = getUserService(dao, encoder);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, null));
        when(dao.update(any(UserEntity.class))).then(new Answer<UserEntity>() {
            @Override
            public UserEntity answer(InvocationOnMock invocationOnMock) throws Throwable {
                UserEntity user = (UserEntity)invocationOnMock.getArguments()[0];
                assertEquals(user.getPassword(), "password");
                return user;
            }
        });
        when(encoder.encode("password")).thenReturn("password");

        service.changePassword("password");

        verify(dao).getByEmail(TEST_EMAIL);
        verify(dao).update(any(UserEntity.class));
        verify(encoder).encode("password");
    }

    @Test(expected = InternalServerError.class)
    public void changePasswordNoAuthTest() {
        IUserDao dao = mock(IUserDao.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        IUserService service = getUserService(dao, encoder);

        service.changePassword("password");
    }

    @Test(expected = InternalServerError.class)
    public void changePasswordUserNotFoundTest() {
        IUserDao dao = mock(IUserDao.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        IUserService service = getUserService(dao, encoder);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(null);

        service.changePassword("password");
    }

    @Test(expected = InternalServerError.class)
    public void changePasswordUpdateFailTest() {
        IUserDao dao = mock(IUserDao.class);
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        IUserService service = getUserService(dao, encoder);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, null));
        when(dao.update(any(UserEntity.class))).thenReturn(null);

        service.changePassword("password");
    }

    @Test
    public void saveResumeTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, new ResumeEntity("test.xml", "text/plain", "old text".getBytes()))
        );
        when(dao.update(any(UserEntity.class))).then(new Answer<UserEntity>() {
            @Override
            public UserEntity answer(InvocationOnMock invocationOnMock) throws Throwable {
                UserEntity user = (UserEntity) invocationOnMock.getArguments()[0];
                assertNotNull(user.getResume());
                assertEquals(user.getResume().getFileName(), "resume.txt");
                assertEquals(user.getResume().getFileType(), TextDocument.getMimeType());
                assertArrayEquals("Resume Text Data".getBytes(), user.getResume().getData());
                return user;
            }
        });
        when(dao.deleteResume(any(ResumeEntity.class))).thenReturn(true);

        Resume resume = service.saveResume("resume.txt", new ByteArrayInputStream("Resume Text Data".getBytes()));

        verify(dao).getByEmail(TEST_EMAIL);
        verify(dao).update(any(UserEntity.class));
        verify(dao).deleteResume(any(ResumeEntity.class));

        assertEquals("resume.txt", resume.getFileName());
        assertEquals(TextDocument, resume.getFileType());
    }

    @Test(expected = InternalServerError.class)
    public void saveResumeNoAuthTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        service.saveResume("resume.txt", new ByteArrayInputStream("Resume Text Data".getBytes()));
    }

    @Test(expected = InternalServerError.class)
    public void saveResumeUserNotFoundTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(null);

        service.saveResume("resume.txt", new ByteArrayInputStream("Resume Text Data".getBytes()));
    }

    @Test(expected = BadRequestException.class)
    public void saveResumeNullFilenameTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, null));

        service.saveResume(null, new ByteArrayInputStream("Resume Text Data".getBytes()));
    }

    @Test(expected = BadRequestException.class)
    public void saveResumeNullDataTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, null));

        service.saveResume("resume.txt", null);
    }

    @Test(expected = InternalServerError.class)
    public void saveResumeUpdateFailTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, null));
        when(dao.update(any(UserEntity.class))).thenReturn(null);

        service.saveResume("resume.txt", new ByteArrayInputStream("Resume Text Data".getBytes()));
    }

    @Test(expected = InternalServerError.class)
    public void saveResumeResumeDateFailTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, new ResumeEntity("test.xml", "text/plain", "old text".getBytes()))
        );
        when(dao.update(any(UserEntity.class))).then(returnsFirstArg());
        when(dao.deleteResume(any(ResumeEntity.class))).thenReturn(false);

        service.saveResume("resume.txt", new ByteArrayInputStream("Resume Text Data".getBytes()));
    }

    @Test
    public void deleteResumeTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, new ResumeEntity("test.xml", "text/plain", "old text".getBytes()))
        );
        when(dao.update(any(UserEntity.class))).then(new Answer<UserEntity>() {
            @Override
            public UserEntity answer(InvocationOnMock invocationOnMock) throws Throwable {
                UserEntity user = (UserEntity) invocationOnMock.getArguments()[0];
                assertNull(user.getResume());
                return user;
            }
        });
        when(dao.deleteResume(any(ResumeEntity.class))).thenReturn(true);

        service.deleteResume();

        verify(dao).getByEmail(TEST_EMAIL);
        verify(dao).update(any(UserEntity.class));
        verify(dao).deleteResume(any(ResumeEntity.class));
    }

    @Test(expected = InternalServerError.class)
    public void deleteResumeNoAuthTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);

        service.deleteResume();
    }

    @Test(expected = InternalServerError.class)
    public void deleteResumeUserNotFoundTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(null);

        service.deleteResume();
    }

    @Test(expected = NotFoundException.class)
    public void deleteResumeDoesntExistTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(genUserEntity(1L, TEST_EMAIL, "", false, null));

        service.deleteResume();
    }

    @Test(expected = InternalServerError.class)
    public void deleteResumeUserUpdateFailTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, new ResumeEntity("test.xml", "text/plain", "old text".getBytes()))
        );
        when(dao.update(any(UserEntity.class))).thenReturn(null);

        service.deleteResume();
    }

    @Test(expected = InternalServerError.class)
    public void deleteResumeResumeDeleteFailTest() {
        IUserDao dao = mock(IUserDao.class);
        IUserService service = getUserService(dao);
        setCurrentUser(TEST_EMAIL);

        when(dao.getByEmail(TEST_EMAIL)).thenReturn(
                genUserEntity(1L, TEST_EMAIL, "", false, new ResumeEntity("test.xml", "text/plain", "old text".getBytes()))
        );
        when(dao.update(any(UserEntity.class))).then(returnsFirstArg());
        when(dao.deleteResume(any(ResumeEntity.class))).thenReturn(false);

        service.deleteResume();
    }
}
