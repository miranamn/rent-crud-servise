package org.acme.controllers;

import org.acme.entity.Customer;
import org.acme.services.CustomerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes("application/json")
public class CustomerController {

    @Inject
    CustomerService customerService;

    @GET
    public List<Customer> getAll() {
        return customerService.getPerson();
    }
    @POST
    public Customer addCustomer(Customer newCustomer){
        return customerService.addCustomer(newCustomer);
    }

}