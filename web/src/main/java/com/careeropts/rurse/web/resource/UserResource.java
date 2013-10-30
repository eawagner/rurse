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
     * @return
     */
    @GET
    @Produces({APPLICATION_JSON})
    public Iterable<User> queryUsers(
            @QueryParam("search") String searchText,
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        return service.query(searchText, pageNum, size);
    }


    /**
     * Retrieves information about the current authenticated user.
     * @return
     */
    @GET
    @Path("/current")
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public User getCurrentUser() {
        return service.getCurrentUser();
    }

    /**
     * Retrieves the resume document for the current authenticated user.
     * @return
     */
    @GET
    @Path("/current/resume")
    public Response getCurrentUserResume() {
        return service.getResumeResponse();
    }

    /**
     * Uploads a resume document for the current authenticated user.
     *
     * Consumes multipart/form-data.  Information about the filename and mime-type of the file should be sent with the
     * request.
     *
     * @param uploadedInputStream
     * @param bodyPart
     * @return
     */
    @POST
    @Consumes(MULTIPART_FORM_DATA)
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
     *
     * @return
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
     * @return
     */
    @GET
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public User getUser(
            @PathParam("id") Long id) {

        return service.getUser(id);
    }

    /**
     * Retrieves the resume document for a specific user.
     * @param id The id of a user.
     * @return
     */
    @GET
    @Path("/{id:\\d+}/resume")
    public Response getCurrentUserResume(
            @PathParam("id") Long id) {

        //TODO this ignores any accept header, but that is ok for now.
        return service.getResumeResponse(id);
    }

    /**
     * Removes a specific user from the system.
     * @param id The id of a user.
     * @return
     */
    @DELETE
    @Path("/{id:\\d+}")
    public Response deleteUser(
            @PathParam("id") Long id) {

        service.delete(id);
        return ok().build();
    }
}
