package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.web.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.ok;

/**
 * Responsible for the management of books in the system.
 *
 * @name Book Resource
 * @contextPath /rest
 */
@Component
@Path("/book")
public class BookResource {

    @Autowired
    IBookService service;

    /**
     * Queries the system for all books currently in the system.
     *
     * @param searchText If provided will limit the books returned to the keywords provided.  Otherwise will return all books.
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned.  If used with pageNum, then this specifies the size of a page.
     * @return
     */
    @GET
    @Produces({APPLICATION_JSON})
    public Iterable<Book> queryBooks(
            @QueryParam("search") String searchText,
            @QueryParam("pageNum") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        return service.query(searchText, pageNum, size);

    }

    /**
     * Adds a new book to the system.  A new id will be generated for the book and be provided in the response.
     *
     * @param model A book object representing the values to store for that book.
     * @return
     */
    @POST
    @Consumes({APPLICATION_XML, APPLICATION_JSON})
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public Book addSingleBook(
            Book model) {

        model.setId(null);
        return service.save(model);
    }

    /**
     * Retrieves a single book from the system.
     *
     * @param id The id of a book.
     * @return
     */
    @GET
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public Book getSingleBook(
            @PathParam("id") Long id) {

        return service.getSingle(id);
    }

    /**
     * Updates the information about a specific book.
     *
     * @param id The id of a book.
     * @param model A book object representing the new values to store for that book.
     * @return
     */
    @POST
    @Consumes({APPLICATION_XML, APPLICATION_JSON})
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public Book saveSingleBook(
            @PathParam("id") Long id,
            Book model) {

        model.setId(id);
        return service.saveOrUpdate(model);
    }

    /**
     * Deletes a specific book from the system.
     *
     * @param id The id of a book.
     * @return
     */
    @DELETE
    @Path("/{id:\\d+}")
    public Response deleteSingleBook(
            @PathParam("id") Long id) {

        service.delete(id);

        return ok().build();
    }
}
