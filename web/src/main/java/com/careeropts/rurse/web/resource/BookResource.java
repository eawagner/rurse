package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.web.service.IBookService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/book")
public class BookResource {


    IBookService service;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Book getSingleBook(
            @PathParam("id") Long id) {

        return service.getSingle(id);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Book saveSingleBook(
            @PathParam("id") Long id,
            Book model) {

        model.setId(id);
        return service.saveOrUpdate(model);
    }

    @DELETE
    @Path("/{id}")
    public void deleteSingleBook(
            @PathParam("id") Long id) {

        service.delete(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Iterable<Book> queryBooks(
            @QueryParam("query") String searchText,
            @QueryParam("pageNum") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        return service.query(searchText, pageNum, size);

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Book addSingleBook(
            Book model) {

        model.setId(null);
        return service.save(model);
    }
}
