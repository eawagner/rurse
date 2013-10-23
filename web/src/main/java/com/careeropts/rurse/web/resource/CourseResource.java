package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.dao.ICourseDao;
import com.careeropts.rurse.model.Course;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/Course")
public class CourseResource {


    ICourseDao dao;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Course getSingleCourse(
            @PathParam("id") String id) {

        return dao.getSingle(id);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Course saveSingleCourse(
            @PathParam("id") String id,
            Course model) {

        model.setId(id);
        return dao.saveOrUpdate(model);
    }

    @DELETE
    @Path("/{id}")
    public void deleteSingleCourse(
            @PathParam("id") String id) {

        dao.delete(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Iterable<Course> queryCourses(
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
    public Course addSingleCourse(
            Course model) {

        model.setId(null);
        return dao.save(model);
    }
}
