package org.acme.services;

import org.acme.RentException.RentException;
import org.acme.entity.Landlord;

import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;


@Singleton
public class LandlordService {

    public List<Landlord> getLandlord(String login) {
        return login == null ?  getLandlord() : getLandlordByLogin(login);
    }
    private List<Landlord> getLandlord() {
        return Landlord.listAll();
    }

    public List<Landlord> getLandlordByLogin(String login){
        return Landlord.find("login = ?1", login).list();
    }

    @Transactional
    public Landlord addLandlord(Landlord newLandlord){
        try {
            newLandlord.persistAndFlush();
        }catch (ValidationException e){
            throw new RentException(e.getCause().getCause().getMessage());
        }
        catch (PersistenceException e){
            if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new RentException(e.getCause().getCause().getMessage());
            }
            throw e;
        }
        return newLandlord;
    }

    @Transactional
    public Response deleteLandlord(Long id){
        Landlord.deleteById(id);
        return Response.status(OK).build();
    }

    @Transactional
    public Landlord updateLandlord(long id, Landlord newLandlord){
        Landlord landlord = Landlord.findById(id);
        try {
            landlord.setFirstName(newLandlord.getFirstName());
            landlord.setSecondName(newLandlord.getSecondName());
            landlord.setLogin(newLandlord.getLogin());
            landlord.setPassword(newLandlord.getPassword());
            landlord.setPhone(newLandlord.getPhone());
        }
        catch (PersistenceException e){
            if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new RentException(e.getCause().getCause().getMessage());
            }
            throw e;
        }
        return landlord;
    }
}
