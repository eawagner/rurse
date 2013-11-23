package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Book;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.WebAppResponseException;
import com.careeropts.rurse.web.service.IBookService;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.ok;
import static org.apache.commons.logging.LogFactory.getLog;

/**
 * Responsible for the management of books in the system.
 *
 * @name Book Resource
 * @contextPath /rest
 */
@Component
@Path("/book")
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class BookResource {

    private static final Log logger = getLog(BookResource.class);

    @Autowired
    IBookService service;

    /**
     * Queries the system for all books currently in the system.
     *
     * @param searchText If provided will limit the books returned to the keywords provided.  Otherwise will return all books.
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned, otherwise it defaults to 50.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Book> queryBooks(
            @QueryParam("search") String searchText,
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") @DefaultValue("50") Integer size) {

        try {

            return service.query(searchText, pageNum, size);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
        }

    }

    /**
     * Adds a new book to the system.  A new id will be generated for the book and be provided in the response.
     *
     * @param model A book object representing the values to store for that book.
     */
    @POST
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Book addSingleBook(
            Book model) {

        try {

            model.setId(null);
            return service.save(model);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
        }
    }

    /**
     * Retrieves a single book from the system.
     *
     * @param id The id of a book.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id:\\d+}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Book getSingleBook(
            @PathParam("id") Long id) {

        try {

            return service.getSingle(id);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
        }
    }

    /**
     * Updates the information about a specific book.
     *
     * @param id The id of a book.
     * @param model A book object representing the new values to store for that book.
     */
    @POST
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id:\\d+}")
    public Book saveSingleBook(
            @PathParam("id") Long id,
            Book model) {

        try {

            model.setId(id);
            return service.update(model);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
        }
    }

    /**
     * Deletes a specific book from the system.
     *
     * @param id The id of a book.
     */
    @DELETE
    @Path("/{id:\\d+}")
    public Response deleteSingleBook(
            @PathParam("id") Long id) {

        try {

            service.delete(id);
            return ok().build();

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
        }
    }
}
