package com.careeropts.rurse.web.service.impl;


import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.dao.object.ResumeDO;
import com.careeropts.rurse.dao.object.UserDO;
import com.careeropts.rurse.model.Resume;
import com.careeropts.rurse.model.User;
import com.careeropts.rurse.web.exception.BadRequestException;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.NotFoundException;
import com.careeropts.rurse.web.service.IUserService;
import com.google.common.base.Function;
import org.apache.commons.io.IOUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.careeropts.rurse.model.Resume.DocType;
import static com.careeropts.rurse.model.Resume.DocType.fromMimeType;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.transform;
import static javax.ws.rs.core.Response.ok;

@Service
@Transactional
public class UserService implements IUserService{

    private static String CURRENT_USER = "user@email.com";

    @Autowired
    IUserDao dao;

    private static boolean validPassword(String password) {
        //TODO look into stronger password requirements.
        return !isNullOrEmpty(password);
    }


    private static Resume fromDatabaseObject(ResumeDO dataObject) {
        if (dataObject == null)
            return null;

        return new Resume(
                dataObject.getFileName(),
                fromMimeType(dataObject.getFileType())
        );
    }

    private static User fromDatabaseObject(UserDO dataObject, boolean includeResume) {
        if (dataObject == null)
            return null;

        return new User(
                dataObject.getId(),
                dataObject.getEmail(),
                (dataObject.isManager() ? User.UserType.Manager: User.UserType.Basic),
                (includeResume? fromDatabaseObject(dataObject.getResume()) : null)
        );
    }

    private UserDO getByEmail(String email) {
        if (!EmailValidator.getInstance().isValid(email))
            throw new BadRequestException("Invalid email address");

        UserDO user = dao.getByEmail(email);

        if (user == null) {
            throw new InternalServerError();
        }

        return user;
    }


    @Override
    public User createAccount(String email, String password) {
        if (isNullOrEmpty(email))
            throw new BadRequestException("Invalid email address");

        if (!validPassword(password))
            throw new BadRequestException("Invalid password");

        UserDO user;

        try {
            user = dao.save(new UserDO(email, password, false, null));
        } catch (Exception ex) {
            throw new BadRequestException("Account already exists");
        }

        if (user == null) {
            throw new InternalServerError();
        }

        return fromDatabaseObject(user, false);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(Long id) {
        if (id == null)
            throw new NotFoundException();

        UserDO user = dao.getSingle(id);

        if (user == null)
            throw new NotFoundException();

        return fromDatabaseObject(user, true);
    }

    @Transactional(readOnly = true)
    @Override
    public Response getResumeResponse(Long id) {
        if (id == null)
            throw new NotFoundException();

        UserDO user = dao.getSingle(id);

        if (user == null || user.getResume() == null)
            throw new NotFoundException();

        ResumeDO resume = user.getResume();

        return ok(resume.getData())
                .type(resume.getFileType())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public Iterable<User> getAll(Integer pageNum, Integer perPage) {
        if (pageNum == null || pageNum < 0)
            pageNum = 0;

        if (perPage == null || perPage < 0)
            perPage = Integer.MAX_VALUE;

        return transform(dao.getAll(pageNum, perPage), new Function<UserDO, User>() {
            @Override
            public User apply(UserDO userDO) {
                return fromDatabaseObject(userDO, false);
            }
        });
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new NotFoundException();

        if (!dao.delete(id))
            throw new NotFoundException();
    }

    @Override
    public void changePassword(String password) {
        if (!validPassword(password))
            throw new BadRequestException("Invalid password");

        UserDO savedObj = getByEmail(CURRENT_USER);

        //TODO change password before securing.
        savedObj.setPassword(password);
        if (dao.saveOrUpdate(savedObj) == null) {
            throw new InternalServerError();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User getCurrentUser() {
        UserDO user = dao.getByEmail(CURRENT_USER);

        if (user == null)
            throw new NotFoundException();

        return fromDatabaseObject(user, true);

    }

    @Transactional(readOnly = true)
    @Override
    public Response getResumeResponse() {
        UserDO user = dao.getByEmail(CURRENT_USER);

        if (user == null || user.getResume() == null)
            throw new NotFoundException();

        ResumeDO resume = user.getResume();

        return ok(resume.getData())
                .type(resume.getFileType())
                .build();
    }

    @Override
    public Resume saveResume(String name, String type, InputStream resumeData) {
        if (name == null)
            throw new BadRequestException("The file name is required for a resume");

        DocType docType = fromMimeType(type);
        if (docType == null)
            throw new BadRequestException("Unrecognized document type");

        if (resumeData == null)
            throw new BadRequestException("Empty file");

        byte[] data;
        try (InputStream input = resumeData;
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            IOUtils.copy(input, output);
            output.flush();

            data = output.toByteArray();
        } catch (IOException e) {
            throw new InternalServerError();
        }

        //save the resume
        UserDO user = dao.getByEmail(CURRENT_USER);

        if (user == null)
            throw new NotFoundException();

        user.setResume(new ResumeDO(name, docType.getMimeType(), data));

        user = dao.save(user);

        if (user == null || user.getResume() == null)
            throw new InternalServerError();

        return fromDatabaseObject(user.getResume());
    }

    @Override
    public void deleteResume() {
        UserDO user = dao.getByEmail(CURRENT_USER);

        if (user == null)
            throw new NotFoundException();

        user.setResume(null);

        user = dao.save(user);

        if (user == null)
            throw new BadRequestException();
    }
}
