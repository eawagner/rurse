package com.careeropts.rurse.web.resource;


import com.careeropts.rurse.model.Resume;
import com.careeropts.rurse.model.User;
import com.careeropts.rurse.web.service.IUserService;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserResource {

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

        return service.query(searchText, pageNum, size);
    }


    /**
     * Retrieves information about the current authenticated user.
     */
    @GET
    @Path("/current")
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public User getCurrentUser() {
        return service.getCurrentUser();
    }

    /**
     * Allows the authenticated user to change their password.
     * @param password The new password to use.
     */
    @POST
    @Consumes({TEXT_PLAIN})
    @Path("/current/password")
    public Response changePassword(
            String password) {

        service.changePassword(password);

        return ok().build();
    }

    /**
     * Retrieves the resume document for the current authenticated user.
     */
    @GET
    @Path("/current/resume")
    public Response getCurrentUserResume() {
        return service.getResumeResponse();
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
    public Resume saveCurrentResume(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataBodyPart bodyPart) {

        String fileType = null;
        String fileName = null;

        if (bodyPart != null)
            fileName = (bodyPart.getContentDisposition() == null ? null : bodyPart.getContentDisposition().getFileName());

        if (bodyPart != null)
            fileType = (bodyPart.getMediaType() == null ? null : bodyPart.getMediaType().toString());

        return service.saveResume(fileName, fileType, uploadedInputStream);
    }

    /**
     * Deletes the resume document for the current authenticated user.
     */
    @DELETE
    @Path("/current/resume")
    public Response deleteCurrentResume() {

        service.deleteResume();
        return ok().build();
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

        return service.getUser(id);
    }

    /**
     * Removes a specific user from the system.
     * @param id The id of a user.
     */
    @DELETE
    @Path("/{id:\\d+}")
    public Response deleteUser(
            @PathParam("id") Long id) {

        service.delete(id);
        return ok().build();
    }

    /**
     * Retrieves the resume document for a specific user.
     * @param id The id of a user.
     */
    @GET
    @Path("/{id:\\d+}/resume")
    public Response getCurrentUserResume(
            @PathParam("id") Long id) {

        //TODO this ignores any accept header, but that is ok for now.
        return service.getResumeResponse(id);
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

        return service.updateAuths(id, manager);
    }
}
