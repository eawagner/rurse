package com.careeropts.rurse.web.resource;


import com.careeropts.rurse.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collections;

@Path("/user")
public class UserResource {


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public User getSingleUser(
            @PathParam("id") Long id) {

        //return dao.getSingle(id);
        return null;
    }

    @DELETE
    @Path("/{id}")
    public void deleteSingleUser(
            @PathParam("id") Long id) {

        //dao.delete(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Iterable<User> queryUsers(
            @QueryParam("pageNum") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        if (pageNum == null)
            pageNum = 0;

        if (size == null)
            size = Integer.MAX_VALUE;

        //return dao.getAll(pageNum, size);

        return Collections.emptyList();
    }
}
