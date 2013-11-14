package com.careeropts.rurse.web.resource;


import com.careeropts.rurse.model.*;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.WebAppResponseException;
import com.careeropts.rurse.web.service.IUserService;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

import static javax.ws.rs.core.MediaType.*;
import static javax.ws.rs.core.Response.ok;

/**
 * Responsible for the management of user information in the system and resumes associated with those users.
 *
 * @name User Resource
 * @contextPath /rest
 */
@Component
@Path("/user")
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class UserResource {

    private static Logger logger = LoggerFactory.getLogger(UserResource.class);

    @Autowired
    IUserService service;

    /**
     * Retrieves a list of all the users in the system.
     *
     * @param searchText If provided will limit the users returned to the users with resumes with the keywords provided.  Otherwise will return all users.
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public List<User> queryUsers(
            @QueryParam("search") String searchText,
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        try {

            return service.query(searchText, pageNum, size);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }


    /**
     * Retrieves information about the current authenticated user.
     */
    @GET
    @Path("/current")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @PreAuthorize("hasRole('ROLE_USER')")
    public User getCurrentUser() {

        try {

            return service.getCurrentUser();

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     * Allows the authenticated user to change their password.
     * @param password The new password to use.
     */
    @POST
    @Consumes({TEXT_PLAIN})
    @Path("/current/password")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Response changePassword(
            String password) {

        try {

            service.changePassword(password);
            return ok().build();

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     * Retrieves the resume document for the current authenticated user.
     */
    @GET
    @Path("/current/resume")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Response getCurrentUserResume() {

        try {

            return service.getResumeResponse();

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     * Uploads a resume document for the current authenticated user.  Consumes multipart/form-data.
     * Information about the filename and mime-type of the file should be sent with the request.
     *
     * @param uploadedInputStream Input stream of the file to store
     * @param bodyPart Metadata about the file being uploaded.
     */
    @POST
    @Consumes(MULTIPART_FORM_DATA)
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/current/resume")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Resume saveCurrentResume(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataBodyPart bodyPart) {

        try {

            String fileType = null;
            String fileName = null;

            if (bodyPart != null)
                fileName = (bodyPart.getContentDisposition() == null ? null : bodyPart.getContentDisposition().getFileName());

            if (bodyPart != null)
                fileType = (bodyPart.getMediaType() == null ? null : bodyPart.getMediaType().toString());

            return service.saveResume(fileName, fileType, uploadedInputStream);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     * Deletes the resume document for the current authenticated user.
     */
    @DELETE
    @Path("/current/resume")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Response deleteCurrentResume() {

        try {

            service.deleteResume();
            return ok().build();

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     *
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Path("/current/recommendation/book")
    public List<Book> getRecommendedBooks(
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        try {

            return null;

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     *
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Path("/current/recommendation/course")
    public List<Course> getRecommendedCoursess(
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        try {

            return null;

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     *
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Path("/current/recommendation/job")
    public List<Job> getRecommendedJobs(
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        try {

            return null;

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }


    /**
     * Retrieves a specific user.
     *
     * @param id The id of a user.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id:\\d+}")
    public User getUser(
            @PathParam("id") Long id) {

        try {

            return service.getUser(id);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     * Removes a specific user from the system.
     * @param id The id of a user.
     */
    @DELETE
    @Path("/{id:\\d+}")
    public Response deleteUser(
            @PathParam("id") Long id) {

        try {

            service.delete(id);
            return ok().build();

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     * Retrieves the resume document for a specific user.
     * @param id The id of a user.
     */
    @GET
    @Path("/{id:\\d+}/resume")
    public Response getUserResume(
            @PathParam("id") Long id) {

        try {

            //TODO this ignores any accept header, but that is ok for now.
            return service.getResumeResponse(id);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }

    /**
     * Allows a manager to change the authorizations of another user in the system.
     * @param id The Id of a user.
     * @param manager If set to true the given user will have manager authorizations in the RURSE system.
     */
    @POST
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id:\\d+}/auth")
    public User changeAuths(
            @PathParam("id") Long id,
            @QueryParam("manager") boolean manager) {

        try {

            return service.updateAuths(id, manager);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage());
        }
    }
}
