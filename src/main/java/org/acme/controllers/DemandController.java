package org.acme.controllers;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import org.acme.entity.Demand;
import org.acme.services.DemandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Path("/demands")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/json")
public class DemandController {

    private static final Logger logger = LogManager.getLogger(DemandController.class);
    @Inject
    DemandService demandService;
    @GET
    public List<Demand> getAll(@QueryParam("title") String title) {
        return demandService.getDemand(title);
    }
    @POST
    public Demand addDemand(@Valid Demand newDemand){
        return demandService.addDemand(newDemand);
    }
    @DELETE
    @Path("/{id}")
    public Response delDemand(Long id){
        return demandService.deleteDemand(id);
    }
    @PUT
    @Path("/{id}")
    public Demand updateDemand(Long id, @Valid Demand newDemand){
        return demandService.updateDemand(id, newDemand);
    }
}