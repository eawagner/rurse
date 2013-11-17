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
import com.google.common.base.Function;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.careeropts.rurse.model.Resume.DocType;
import static com.careeropts.rurse.model.Resume.DocType.fromMimeType;
import static com.careeropts.rurse.web.service.util.EntityTransform.fromEntity;
import static com.careeropts.rurse.web.service.util.SecurityUtils.getAuthenticatedUser;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.transform;
import static java.lang.String.format;
import static javax.ws.rs.core.Response.ok;
import static org.apache.commons.io.IOUtils.copy;
import static org.apache.tika.config.TikaConfig.getDefaultConfig;

@Service
@Transactional
public class UserService implements IUserService{

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private static TikaConfig TIKA_CONFIG = getDefaultConfig();

    private final IUserDao dao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    private static DocType deriveFileDocType(String name, byte[] data) {

        //With no mimetype information try to derive the mime type from the file name and/or contents.
        Metadata metadata = new Metadata();
        metadata.add(Metadata.RESOURCE_NAME_KEY, name);

        try (ByteArrayInputStream input = new ByteArrayInputStream(data)) {
            MediaType mediaType = TIKA_CONFIG.getDetector().detect(input, metadata);
            if (mediaType != null)
                return fromMimeType(mediaType.toString());


        } catch (IOException ex) {
            logger.error(format("Error deriving MIME type of the uploaded resume (%s)", name), ex);
        }

        return null;
    }

    private static ResumeEntity generateNewResume(String name, InputStream resumeData) {
        if (isNullOrEmpty(name))
            throw new BadRequestException("The file name is required for a resume");

        if (resumeData == null)
            throw new BadRequestException("No file contents were found");

        byte[] data;
        try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            copy(resumeData, output);
            output.flush();

            data = output.toByteArray();
        } catch (IOException e) {
            throw new InternalServerError();
        }

        DocType docType = deriveFileDocType(name, data);
        if (docType == null)
            throw new BadRequestException("Unrecognized document type.  Text and Micorosoft Word documents are accepted.");

        return new ResumeEntity(name, docType.getMimeType(), data);
    }

    private String encodePassword(String password) {
        if (isNullOrEmpty(password))
            throw new BadRequestException("Invalid password");

        return passwordEncoder.encode(password);
    }

    private UserEntity getCurrent() {
        //retrieve the user email from the security context.
        String current = getAuthenticatedUser();
        if (current == null)
            throw new InternalServerError("Unable to find an authenticated user.");

        UserEntity user = dao.getByEmail(current);

        if (user == null)
            throw new InternalServerError("Unable to find the authorized user.");

        return user;
    }

    private UserEntity getById(Long id) {
        if (id == null)
            throw new NotFoundException();

        UserEntity user = dao.getSingle(id);
        if (user == null)
            throw new NotFoundException("No user user exists with the following id: " + id);

        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createAccount(String email, String password) {
        if (!EmailValidator.getInstance().isValid(email))
            throw new BadRequestException("Invalid email address");

        if (dao.getByEmail(email) != null)
            throw new BadRequestException("Account already exists");

        UserEntity user = dao.save(new UserEntity(email, encodePassword(password), false, null));
        if (user == null)
            throw new InternalServerError();

        if (logger.isInfoEnabled())
            logger.info(format("Creating new user %s", email));

        return fromEntity(user);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public User getUser(Long id) {
        return fromEntity(getById(id));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Response getResumeResponse(Long id) {

        UserEntity user = getById(id);

        if (user.getResume() == null)
            throw new NotFoundException();

        ResumeEntity resume = user.getResume();

        return ok(resume.getData())
                .type(resume.getFileType())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<User> query(String searchText, Integer pageNum, Integer perPage) {
        if (pageNum == null || pageNum < 0)
            pageNum = 0;

        if (perPage == null || perPage < 0)
            perPage = 50;

        List<UserEntity> results;

        if (isNullOrEmpty(searchText))
            results = dao.getAll(pageNum, perPage);
        else
            results = dao.search(searchText, pageNum, perPage);

        return transform(results, new Function<UserEntity, User>() {
            @Override
            public User apply(UserEntity userEntity) {
                return fromEntity(userEntity);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateAuths(Long id, boolean manager) {
        UserEntity user = getById(id);

        user.setManager(manager);
        user = dao.update(user);

        if (user == null)
            throw  new InternalServerError();

        if (logger.isInfoEnabled())
            logger.info(format("Changing the authorizations for %s, manager=%b", user.getEmail(), manager));

        return fromEntity(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) {
        if (!dao.delete(getById(id)))
            throw new NotFoundException();

        if (logger.isInfoEnabled())
            logger.info(format("Deleted user %d", id));
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void changePassword(String password) {
        password = encodePassword(password);

        UserEntity savedObj = getCurrent();

        savedObj.setPassword(password);
        if (dao.update(savedObj) == null)
            throw new InternalServerError();

    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public User getCurrentUser() {
        return fromEntity(getCurrent());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Response getResumeResponse() {
        UserEntity user = getCurrent();

        if (user.getResume() == null)
            throw new NotFoundException();

        ResumeEntity resume = user.getResume();

        return ok(resume.getData())
                .type(resume.getFileType())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Resume saveResume(String name, InputStream resumeData) {

        //save the resume
        UserEntity user = getCurrent();
        ResumeEntity oldResume = user.getResume();

        user.setResume(generateNewResume(name, resumeData));
        user = dao.update(user);

        if (user == null || user.getResume() == null)
            throw new InternalServerError();

        //Delete any old resume as it will be orphaned.
        if (oldResume != null && !dao.deleteResume(oldResume))
            throw new InternalServerError();

        if (logger.isInfoEnabled())
            logger.info(format("User (%s) uploaded a new resume to the system", user.getEmail()));

        return fromEntity(user.getResume());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteResume() {
        UserEntity user = getCurrent();

        if (user.getResume() == null)
            throw new NotFoundException();

        ResumeEntity resume = user.getResume();

        user.setResume(null);

        user = dao.update(user);

        //delete resume seperately because database may not cascade a delete for dereferenced entities.
        if (!dao.deleteResume(resume) || user == null)
            throw new InternalServerError();

        if (logger.isInfoEnabled())
            logger.info(format("User (%s) deleted their resume from the system", user.getEmail()));
    }
}
