package org.acme.services;

import org.acme.RentException.RentException;
import org.acme.entity.Advertising;
import org.acme.entity.Landlord;

import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;


@Singleton
public class AdvertisingService {

    public List<Advertising> getAdvertising(String title) {
        return title == null ?  getAdvertising() : getAdvertisingByTitle(title);
    }
    private List<Advertising> getAdvertising() {
        return Advertising.listAll();
    }

    public List<Advertising> getAdvertisingByTitle(String title){
        return Advertising.find("title = ?1", title).list();
    }
    public List<Advertising> getAdvertisingByLandlordId(Long id){
        return Advertising.find("landlord_id = ?1", id).list();
    }

    @Transactional
    public Advertising addAdvertising(Advertising newAdvertising){
        try {
            newAdvertising.persistAndFlush();
        }catch (ValidationException e){
            throw new RentException(e.getCause().getCause().getMessage());
        }
        catch (PersistenceException e){
            if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new RentException(e.getCause().getCause().getMessage());
            }
            throw e;
        }
        return newAdvertising;
    }

    @Transactional
    public Response deleteAdvertising(Long id){
        Advertising.deleteById(id);
        return Response.status(OK).build();
    }

    @Transactional
    public Advertising updateAdvertising(long id, Advertising newAdvertising){
        Advertising advertising = Advertising.findById(id);
        try {
            advertising.setDescription(newAdvertising.getDescription());
            advertising.setMaxTerm(newAdvertising.getMaxTerm());
        }
        catch (PersistenceException e){
            if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new RentException(e.getCause().getCause().getMessage());
            }
            throw e;
        }
        return advertising;
    }
}
