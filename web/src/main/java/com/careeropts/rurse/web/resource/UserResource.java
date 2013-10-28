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
 * User Resource
 *
 * @name User Resource
 * @contextPath /rest
 */
@Component
@Path("/user")
public class UserResource {

    @Autowired
    IUserService service;

    @GET
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public User getCurrentUser() {
        return service.getCurrentUser();
    }

    @GET
    @Path("/resume")
    public Response getCurrentUserResume() {
        return service.getResumeResponse();
    }

    @POST
    @Consumes(MULTIPART_FORM_DATA)
    @Path("/resume")
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

    @DELETE
    @Path("/resume")
    public Response deleteCurrentResume() {

        service.deleteResume();
        return ok().build();
    }

    @GET
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public User getUser(
            @PathParam("id") Long id) {

        return service.getUser(id);
    }

    @GET
    @Path("/{id:\\d+}/resume")
    public Response getCurrentUserResume(
            @PathParam("id") Long id) {

        //TODO this ignores any accept header, but that is ok for now.
        return service.getResumeResponse(id);
    }

    @DELETE
    @Path("/{id:\\d+}")
    public Response deleteUser(
            @PathParam("id") Long id) {

        service.delete(id);
        return ok().build();
    }

    @GET
    @Produces({APPLICATION_JSON})
    @Path("/list")
    public Iterable<User> queryUsers(
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        return service.getAll(pageNum, size);
    }
}
