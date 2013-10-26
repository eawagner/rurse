package com.careeropts.rurse.web.resource;


import com.careeropts.rurse.model.User;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import java.util.Arrays;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Component
@Path("/user")
public class UserResource {


    @GET
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id}")
    public User getSingleUser(
            @PathParam("id") Long id) {

        //return dao.getSingle(id);
        return new User(id, "user@email.com");
    }

    @DELETE
    @Path("/{id}")
    public void deleteSingleUser(
            @PathParam("id") Long id) {

        //dao.delete(id);
    }

    @GET
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public Iterable<User> queryUsers(
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        if (pageNum == null)
            pageNum = 0;

        if (size == null)
            size = Integer.MAX_VALUE;

        //return dao.getAll(pageNum, size);

        return Arrays.asList(getSingleUser(1L));
    }
}
