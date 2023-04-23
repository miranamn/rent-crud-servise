package org.acme.controllers;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.acme.entity.Advertising;
import org.acme.entity.Landlord;
import org.acme.services.AdvertisingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Path("/advertising")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/json")
public class AdvertisingController {

    private static final Logger logger = LogManager.getLogger(AdvertisingController.class);
    @Inject
    AdvertisingService advertisingService;
    @GET
    public List<Advertising> getAll(Map<String, Object> parameters) {
        if (parameters == null) return advertisingService.getAdvertising();
        return advertisingService.getAdvertising(parameters);
    }
    @GET
    @Path("/landlord")
    public List<Advertising> getAllLandlord(@QueryParam("landlord") Long id) {
        return advertisingService.getAdvertisingByLandlordId(id);
    }
    @POST
    public Advertising addAdvertising(@Valid Advertising newAdvertising){
        return advertisingService.addAdvertising(newAdvertising);
    }
    @DELETE
    @Path("/id/{id}")
    public Response delAdvertising(Long id){
        return advertisingService.deleteAdvertising(id);
    }
    @PUT
    @Path("/id/{id}")
    public Advertising updateAdvertising(Long id, @Valid Advertising newAdvertising){
        return advertisingService.updateAdvertising(id, newAdvertising);
    }
}