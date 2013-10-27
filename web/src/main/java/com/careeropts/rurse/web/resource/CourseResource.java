package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.web.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Component
@Path("/course")
public class CourseResource {

    @Autowired
    ICourseService service;

    @GET
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public Course getSingleCourse(
            @PathParam("id") Long id) {

        return service.getSingle(id);
    }

    @PUT
    @Consumes({APPLICATION_XML, APPLICATION_JSON})
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public Course saveSingleCourse(
            @PathParam("id") Long id,
            Course model) {

        model.setId(id);
        return service.saveOrUpdate(model);
    }

    @DELETE
    @Path("/{id:\\d+}")
    public void deleteSingleCourse(
            @PathParam("id") Long id) {

        service.delete(id);
    }

    @GET
    @Produces({APPLICATION_JSON})
    public Iterable<Course> queryCourses(
            @QueryParam("query") String searchText,
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        return service.query(searchText, pageNum, size);

    }

    @POST
    @Consumes({APPLICATION_XML, APPLICATION_JSON})
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public Course addSingleCourse(
            Course model) {

        model.setId(null);
        return service.save(model);
    }
}
