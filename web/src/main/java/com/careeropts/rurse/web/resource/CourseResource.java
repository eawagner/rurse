package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.web.exception.InternalServerError;
import com.careeropts.rurse.web.exception.WebAppResponseException;
import com.careeropts.rurse.web.service.ICourseService;
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
 * Responsible for the management of courses in the system.
 *
 * @name Course Resource
 * @contextPath /rest
 */
@Component
@Path("/course")
@PreAuthorize("hasRole('ROLE_MANAGER')")
public class CourseResource {

    private static final Log logger = getLog(CourseResource.class);

    @Autowired
    ICourseService service;

    /**
     * Queries the system for all courses currently in the system.
     *
     * @param searchText If provided will limit the courses returned to the keywords provided.  Otherwise will return all courses.
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned, otherwise it defaults to 50.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Course> queryCourses(
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
     * Adds a new course to the system.  A new id will be generated for the course and be provided in the response.
     * @param model A course object representing the values to store for that course.
     */
    @POST
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public Course addSingleCourse(
            Course model) {

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
     * Retrieves a single course from the system.
     *
     * @param id The id of a course.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id:\\d+}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Course getSingleCourse(
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
     * Updates the information about a specific course.
     *
     * @param id The id of a course.
     * @param model A course object representing the new values to store for that course.
     */
    @POST
    @Consumes({APPLICATION_JSON, APPLICATION_XML})
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id:\\d+}")
    public Course saveSingleCourse(
            @PathParam("id") Long id,
            Course model) {

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
     * Deletes a specific course from the system.
     *
     * @param id The id of a course.
     */
    @DELETE
    @Path("/{id:\\d+}")
    public Response deleteSingleCourse(
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
