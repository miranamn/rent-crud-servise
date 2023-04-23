package org.acme.services;

import org.acme.RentException.RentException;
import org.acme.entity.Advertising;
import org.acme.entity.Landlord;

import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.Status.OK;


@Singleton
public class AdvertisingService {

    public List<Advertising> getAdvertising(Map<String, Object> parameters) {
        Map<String, Object> nonNullParams = parameters.entrySet().stream()
                .filter( entry -> entry.getValue() != null )
                .collect( Collectors.toMap( Map.Entry::getKey, Map.Entry::getValue ) );
        if (nonNullParams.isEmpty()) {
            return Advertising.listAll();
        }
        String query = nonNullParams.entrySet().stream()
                .map( entry -> entry.getKey() + "=:" + entry.getKey() )
                .collect( Collectors.joining(" and ") );
        return Advertising.find(query, parameters).list();
    }
    public List<Advertising> getAdvertising() {
        return Advertising.listAll();
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
    private static void addIfNotNull(Map<String, Object> map, String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }
}
