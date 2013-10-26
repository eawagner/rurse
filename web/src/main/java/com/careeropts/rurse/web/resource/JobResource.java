package com.careeropts.rurse.web.resource;

import com.careeropts.rurse.model.Job;
import com.careeropts.rurse.web.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Component
@Path("/job")
public class JobResource {

    @Autowired
    IJobService service;

    @GET
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public Job getSingleJob(
            @PathParam("id") Long id) {

        return service.getSingle(id);
    }

    @PUT
    @Consumes({APPLICATION_XML, APPLICATION_JSON})
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    @Path("/{id:\\d+}")
    public Job saveSingleJob(
            @PathParam("id") Long id,
            Job model) {

        model.setId(id);
        return service.saveOrUpdate(model);
    }

    @DELETE
    @Path("/{id:\\d+}")
    public void deleteSingleJob(
            @PathParam("id") Long id) {

        service.delete(id);
    }

    @GET
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public Iterable<Job> queryJobs(
            @QueryParam("query") String searchText,
            @QueryParam("pageNum") @DefaultValue("0") Integer pageNum,
            @QueryParam("resultSize") Integer size) {

        return service.query(searchText, pageNum, size);

    }

    @POST
    @Consumes({APPLICATION_XML, APPLICATION_JSON})
    @Produces({APPLICATION_XML, APPLICATION_JSON})
    public Job addSingleJob(
            Job model) {

        model.setId(null);
        return service.save(model);
    }
}
