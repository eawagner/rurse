package com.careeropts.rurse.web.resource;


import com.careeropts.rurse.dao.IUserDao;
import com.careeropts.rurse.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/user")
public class UserResource {

    IUserDao dao;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public User getSingleUser(
            @PathParam("id") String id) {

        return dao.getSingle(id);
    }

    @DELETE
    @Path("/{id}")
    public void deleteSingleUser(
            @PathParam("id") String id) {

        dao.delete(id);
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

        return dao.getAll(pageNum, size);
    }
}
