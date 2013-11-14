package com.careeropts.rurse.web.service;


import com.careeropts.rurse.model.Resume;
import com.careeropts.rurse.model.User;

import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

/**
 * Interface responsible for all business logic regarding the management of users and resumes.
 */
public interface IUserService {

    /**
     * Creates a new user account.
     * @param email Email address of the new user.
     * @param password Password for the new user.
     */
    public User createAccount (String email, String password);

    /**
     * Gets a single user with the given id.
     * @param id The id of the user.
     */
    public User getUser(Long id);

    /**
     * Retrieves the {@link Response} with the correct parameters to return the resume document stored for a given user.
     * @param id The id of the user.
     */
    public Response getResumeResponse(Long id);

    /**
     * Queries the system for all users.  The query can be controlled via the use of search text and/or pagination
     * options.
     * @param searchText Search text to limit the results.
     * @param pageNum The page number of the results to be retrieved.
     * @param perPage The number of results per page.
     */
    public List<User> query(String searchText, Integer pageNum, Integer perPage);

    /**
     * Deletes a user from the system with the given id.
     * @param id The id of the user.
     */
    public void delete(Long id);

    /**
     * Updates the authorizations for a user with the given id.
     * @param id The id of the user.
     * @param manager Value determines if the user will have manager auths.
     */
    public User updateAuths(Long id, boolean manager);

    /**
     * Changes the password for the authenticated user of the request.
     * @param password New password for the user.
     */
    public void changePassword (String password);

    /**
     * Retrieves the currently authenticated user.
     */
    public User getCurrentUser();

    /**
     * Retrieves the {@link Response} with the correct parameters to return the resume document stored for the authenticated user.
     */
    public Response getResumeResponse();

    /**
     * Saves a new resume for the authenticated user.
     * @param name File name of the resume.
     * @param resumeData Datastream holding the binary resume data.
     */
    public Resume saveResume(String name, InputStream resumeData);

    /**
     * Deletes the resume for the authenticated user.
     */
    public void deleteResume();
}
