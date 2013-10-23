package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.dao.IBookDao;
import com.careeropts.rurse.model.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/book")
public class BookResource {


    IBookDao dao;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Book getSingleBook(
            @PathParam("id") String id) {

        return dao.getSingle(id);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Book saveSingleBook(
            @PathParam("id") String id,
            Book model) {

        model.setId(id);
        return dao.saveOrUpdate(model);
    }

    @DELETE
    @Path("/{id}")
    public void deleteSingleBook(
            @PathParam("id") String id) {

        dao.delete(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Iterable<Book> queryBooks(
            @QueryParam("query") String searchText,
            @QueryParam("pageNum") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        if (pageNum == null)
            pageNum = 0;

        if (size == null)
            size = Integer.MAX_VALUE;

        if (searchText != null) {
            return dao.search(searchText, pageNum, size);
        } else {
            return dao.getAll(pageNum, size);
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Book addSingleBook(
            Book model) {

        model.setId(null);
        return dao.save(model);
    }
}
