package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.web.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.Response.ok;

/**
 * Responsible for the management of courses in the system.
 *
 * @name Course Resource
 * @contextPath /rest
 */
@Component
@Path("/course")
public class CourseResource {

    @Autowired
    ICourseService service;

    /**
     * Queries the system for all courses currently in the system.
     *
     * @param searchText If provided will limit the courses returned to the keywords provided.  Otherwise will return all courses.
     * @param pageNum If provided the value Specifies which page to retrieve for pagination.  This is a zero-based index, i.e. the first page is pageNum=0.
     * @param size If provided limits the results to be returned.  If used with pageNum, then this specifies the size of a page.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    public List<Course> queryCourses(
            @QueryParam("search") String searchText,
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        return service.query(searchText, pageNum, size);

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

        model.setId(null);
        return service.save(model);
    }

    /**
     * Retrieves a single course from the system.
     *
     * @param id The id of a course.
     */
    @GET
    @Produces({APPLICATION_JSON, APPLICATION_XML})
    @Path("/{id:\\d+}")
    public Course getSingleCourse(
            @PathParam("id") Long id) {

        return service.getSingle(id);
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

        model.setId(id);
        return service.update(model);
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

        service.delete(id);

        return ok().build();
    }
}
