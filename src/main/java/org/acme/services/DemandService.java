package org.acme.services;

import org.acme.RentException.RentException;
import org.acme.entity.Demand;

import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;


@Singleton
public class DemandService {

    public List<Demand> getDemand(String title) {
        return title == null ?  getDemand() : getDemandByTitle(title);
    }
    private List<Demand> getDemand() {
        return Demand.listAll();
    }

    public List<Demand> getDemandByTitle(String title){
        return Demand.find("title = ?1", title).list();
    }

    @Transactional
    public Demand addDemand(Demand newDemand){
        try {
            newDemand.persistAndFlush();
        }catch (ValidationException e){
            throw new RentException(e.getCause().getCause().getMessage());
        }
        catch (PersistenceException e){
            if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new RentException(e.getCause().getCause().getMessage());
            }
            throw e;
        }
        return newDemand;
    }

    @Transactional
    public Response deleteDemand(Long id){
        Demand.deleteById(id);
        return Response.status(OK).build();
    }

    @Transactional
    public Demand updateDemand(long id, Demand newDemand){
        Demand demand = Demand.findById(id);
        try {
            demand.setStatus(newDemand.getStatus());
            demand.setDescription(newDemand.getDescription());
            demand.setRentTime(newDemand.getRentTime());
        }
        catch (PersistenceException e){
            if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new RentException(e.getCause().getCause().getMessage());
            }
            throw e;
        }
        return demand;
    }
}
