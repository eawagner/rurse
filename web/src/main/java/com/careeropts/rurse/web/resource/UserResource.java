package com.careeropts.rurse.web.resource;


import com.careeropts.rurse.model.*;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.WebAppResponseException;
import com.careeropts.rurse.web.service.IRecommendationService;
import com.careeropts.rurse.web.service.IUserService;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

import static javax.ws.rs.core.MediaType.*;
import static javax.ws.rs.core.Response.ok;
import static org.apache.commons.logging.LogFactory.getLog;

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

    private static final Log logger = getLog(UserResource.class);

    @Autowired
    IUserService service;

    @Autowired
    IRecommendationService recommendationService;

    /**
     * Retrieves a list of all the users in the system.
     *
     * @param searchText If provided will limit the users returned to the users with resumes with the keywords provided.  Otherwise will return all users.
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned, otherwise it defaults to 50.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public List<User> queryUsers(
            @QueryParam("search") String searchText,
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") @DefaultValue("50") Integer size) {

        try {

            return service.query(searchText, pageNum, size);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
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
            throw new InternalServerError();
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
            throw new InternalServerError();
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
            throw new InternalServerError();
        }
    }

    /**
     * Uploads a resume document for the current authenticated user.
     *
     * @param fileName File name of the resume being uploaded.
     * @param inputStream Input stream of the file to store
     */
    @POST
    @Consumes({APPLICATION_OCTET_STREAM})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/current/resume")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Resume saveCurrentResume (
            @QueryParam("fileName") String fileName,
            InputStream inputStream) {

        try {

            return service.saveResume(fileName, inputStream);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
        }
    }

    /**
     * Uploads a resume document for the current authenticated user.  This endpoint is to allow simple integration
     * with the HTML file upload form. Consumes multipart/form-data.
     *
     * @param inputStream Input stream of the file to store
     * @param bodyPart Metadata about the file being uploaded.
     */
    @POST
    @Consumes(MULTIPART_FORM_DATA)
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/current/resume")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Resume saveCurrentResume (
            @FormDataParam("file") InputStream inputStream,
            @FormDataParam("file") FormDataBodyPart bodyPart) {

        try {
            String fileName = null;

            if (bodyPart != null)
                fileName = (bodyPart.getContentDisposition() == null ? null : bodyPart.getContentDisposition().getFileName());

            return service.saveResume(fileName, inputStream);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
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
            throw new InternalServerError();
        }
    }

    /**
     * If the current user has a resume in the system, will return a list of recommended books for the user
     * based on the content of their resume.
     *
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned, otherwise it defaults to 50.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/current/recommend/book")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Book> getRecommendedBooks(
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") @DefaultValue("50") Integer size) {

        try {

            return recommendationService.recommendBooksForCurrentUser(pageNum, size);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
        }
    }

    /**
     * If the current user has a resume in the system, will return a list of recommended courses for the user
     * based on the content of their resume.
     *
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned, otherwise it defaults to 50.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/current/recommend/course")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Course> getRecommendedCoursess(
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") @DefaultValue("50") Integer size) {

        try {

            return recommendationService.recommendCoursesForCurrentUser(pageNum, size);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
        }
    }

    /**
     * If the current user has a resume in the system, will return a list of recommended jobs for the user
     * based on the content of their resume.
     *
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned, otherwise it defaults to 50.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/current/recommend/job")
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Job> getRecommendedJobs(
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") @DefaultValue("50") Integer size) {

        try {

            return recommendationService.recommendJobsForCurrentUser(pageNum, size);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
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
            throw new InternalServerError();
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
            throw new InternalServerError();
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
            throw new InternalServerError();
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
            throw new InternalServerError();
        }
    }
}
