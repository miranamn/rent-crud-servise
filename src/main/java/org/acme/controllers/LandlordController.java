package org.acme.controllers;


import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import org.acme.entity.Landlord;
import org.acme.services.LandlordService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Path("/landlords")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/json")
public class LandlordController {

    private static final Logger logger = LogManager.getLogger(LandlordController.class);
    @Inject
    LandlordService landlordService;

    @GET
    public List<Landlord> getAll(@QueryParam("login") String login) {
        return landlordService.getLandlord(login);
    }
    @POST
    public Landlord addLandlord(@Valid Landlord newLandlord){
        return landlordService.addLandlord(newLandlord);
    }
    @DELETE
    @Path("/{id}")
    public Response delLandlord(Long id){
        return landlordService.deleteLandlord(id);
    }
    @PUT
    @Path("/{id}")
    public Landlord updateLandlord(Long id, @Valid Landlord newLandlord){
        return landlordService.updateLandlord(id, newLandlord);
    }
}