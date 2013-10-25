package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.web.service.IJobService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("/Job")
public class JobResource {


    IJobService service;

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Job getSingleJob(
            @PathParam("id") Long id) {

        return service.getSingle(id);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/{id}")
    public Job saveSingleJob(
            @PathParam("id") Long id,
            Job model) {

        model.setId(id);
        return service.saveOrUpdate(model);
    }

    @DELETE
    @Path("/{id}")
    public void deleteSingleJob(
            @PathParam("id") Long id) {

        service.delete(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Iterable<Job> queryJobs(
            @QueryParam("query") String searchText,
            @QueryParam("pageNum") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        return service.query(searchText, pageNum, size);

    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Job addSingleJob(
            Job model) {

        model.setId(null);
        return service.save(model);
    }
}
