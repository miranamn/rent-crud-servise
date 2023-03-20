package org.acme.controllers;

import org.acme.entity.Customer;
import org.acme.services.CustomerService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/json")
public class CustomerController {

//TODO: ОТРАБОТАТЬ ЛОВЛЮ ЭКСЕПШЕНОВ
    private static final Logger logger = LogManager.getLogger(CustomerController.class);
    @Inject
    CustomerService customerService;

    @GET
    public List<Customer> getAll(@QueryParam("login") String login) {
        return customerService.getPerson(login);
    }
    @POST
    public Customer addCustomer(@Valid Customer newCustomer){
        return customerService.addCustomer(newCustomer);
    }
    @DELETE
    @Path("/{id}")
    public Response delCustomer(Long id){
        return customerService.deleteCustomer(id);
    }
    @PUT
    @Path("/{id}")
    public Customer updateCustomer(Long id, Customer newCustomer){
        return customerService.updateCustomer(id, newCustomer);
    }


}