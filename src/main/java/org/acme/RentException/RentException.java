package org.acme.RentException;

import javax.ws.rs.core.Response;

public class RentException extends RuntimeException{
    public Response.Status response = Response.Status.INTERNAL_SERVER_ERROR;
    public RentException(String message){
        super(message);
    }
}
