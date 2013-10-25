package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Course;
import com.careeropts.rurse.web.service.ICourseService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/Course")
public class CourseResource {


    ICourseService service;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Course getSingleCourse(
            @PathParam("id") String id) {

        return service.getSingle(id);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Course saveSingleCourse(
            @PathParam("id") String id,
            Course model) {

        model.setId(id);
        return service.saveOrUpdate(model);
    }

    @DELETE
    @Path("/{id}")
    public void deleteSingleCourse(
            @PathParam("id") String id) {

        service.delete(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Iterable<Course> queryCourses(
            @QueryParam("query") String searchText,
            @QueryParam("pageNum") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        return service.query(searchText, pageNum, size);

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Course addSingleCourse(
            Course model) {

        model.setId(null);
        return service.save(model);
    }
}
