package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.web.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Component
@Path("/book")
public class BookResource {

    @Autowired
    IBookService service;

    @GET
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public Book getSingleBook(
            @PathParam("id") Long id) {

        return service.getSingle(id);
    }

    @PUT
    @Consumes({APPLICATION_XML, APPLICATION_JSON})
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public Book saveSingleBook(
            @PathParam("id") Long id,
            Book model) {

        model.setId(id);
        return service.saveOrUpdate(model);
    }

    @DELETE
    @Path("/{id:\\d+}")
    public void deleteSingleBook(
            @PathParam("id") Long id) {

        service.delete(id);
    }

    @GET
    @Produces({APPLICATION_JSON})
    public Iterable<Book> queryBooks(
            @QueryParam("query") String searchText,
            @QueryParam("pageNum") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        return service.query(searchText, pageNum, size);

    }

    @POST
    @Consumes({APPLICATION_XML, APPLICATION_JSON})
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public Book addSingleBook(
            Book model) {

        model.setId(null);
        return service.save(model);
    }
}
