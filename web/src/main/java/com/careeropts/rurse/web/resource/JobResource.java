package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.dao.IJobDao;
import com.careeropts.rurse.model.Job;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/Job")
public class JobResource {


    IJobDao dao;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Job getSingleJob(
            @PathParam("id") String id) {

        return dao.getSingle(id);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Job saveSingleJob(
            @PathParam("id") String id,
            Job model) {

        model.setId(id);
        return dao.saveOrUpdate(model);
    }

    @DELETE
    @Path("/{id}")
    public void deleteSingleJob(
            @PathParam("id") String id) {

        dao.delete(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Iterable<Job> queryJobs(
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
    public Job addSingleJob(
            Job model) {

        model.setId(null);
        return dao.save(model);
    }
}
