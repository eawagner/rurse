package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.model.User;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.WebAppResponseException;
import com.careeropts.rurse.web.service.IJobService;
import com.careeropts.rurse.web.service.IRecommendationService;
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
 * Responsible for the management of job listings in the system.
 *
 * @name Job Resource
 * @contextPath /rest
 */
@Component
@Path("/job")
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class JobResource {

    private static final Log logger = getLog(JobResource.class);

    @Autowired
    IJobService service;

    @Autowired
    IRecommendationService recommendationService;

    /**
     * Queries the system for all job listings currently in the system.
     *
     * @param searchText If provided will limit the job listings returned to the keywords provided.  Otherwise will return all job listings.
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned, otherwise it defaults to 50.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Job> queryJobs(
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
     * Adds a new job listing to the system.  A new id will be generated for the job listing and be provided in the response.
     *
     * @param model A job listing object representing the values to store for that job listing.
     */
    @POST
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Job addSingleJob(
            Job model) {

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
     * Retrieves a single job listing from the system.
     *
     * @param id The id of a job listing.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id:\\d+}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Job getSingleJob(
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
     * Updates the information about a specific job listing.
     *
     * @param id The id of a job listing.
     * @param model A job listing object representing the new values to store for that job listing.
     */
    @POST
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id:\\d+}")
    public Job saveSingleJob(
            @PathParam("id") Long id,
            Job model) {

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
     * Deletes a specific job listing from the system.
     *
     * @param id The id of a job listing.
     */
    @DELETE
    @Path("/{id:\\d+}")
    public Response deleteSingleJob(
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

    /**
     * Queries the system for all user's with resumes which the system recommends for specified job listing.
     *
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned, otherwise it defaults to 50.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id:\\d+}/recommend/user")
    public List<User> getRecommendedUsersForJob(
            @PathParam("id") Long id,
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") @DefaultValue("50") Integer size) {

        try {

            return recommendationService.recommendUsersForJob(id, pageNum, size);

        } catch (WebAppResponseException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalServerError();
        }
    }
}
