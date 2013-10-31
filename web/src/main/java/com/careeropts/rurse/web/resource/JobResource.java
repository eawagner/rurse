package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.web.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.ok;

/**
 * Responsible for the management of job listings in the system.
 *
 * @name Job Resource
 * @contextPath /rest
 */
@Component
@Path("/job")
public class JobResource {

    @Autowired
    IJobService service;

    /**
     * Queries the system for all job listings currently in the system.
     *
     * @param searchText If provided will limit the job listings returned to the keywords provided.  Otherwise will return all job listings.
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Produces({APPLICATION_JSON})
    public Iterable<Job> queryJobs(
            @QueryParam("search") String searchText,
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        return service.query(searchText, pageNum, size);

    }

    /**
     * Adds a new job listing to the system.  A new id will be generated for the job listing and be provided in the response.
     *
     * @param model A job listing object representing the values to store for that job listing.
     */
    @POST
    @Consumes({APPLICATION_XML, APPLICATION_JSON})
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public Job addSingleJob(
            Job model) {

        model.setId(null);
        return service.save(model);
    }

    /**
     * Retrieves a single job listing from the system.
     *
     * @param id The id of a job listing.
     */
    @GET
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public Job getSingleJob(
            @PathParam("id") Long id) {

        return service.getSingle(id);
    }

    /**
     * Updates the information about a specific job listing.
     *
     * @param id The id of a job listing.
     * @param model A job listing object representing the new values to store for that job listing.
     */
    @POST
    @Consumes({APPLICATION_XML, APPLICATION_JSON})
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public Job saveSingleJob(
            @PathParam("id") Long id,
            Job model) {

        model.setId(id);
        return service.saveOrUpdate(model);
    }

    /**
     * Deletes a specific job listing from the system.
     *
     * @param id The id of a job listing.
     */
    @DELETE
    @Path("/{id:\\d+}")
    public Response deleteSingleJob(
            @PathParam("id") Long id) {

        service.delete(id);

        return ok().build();
    }
}
