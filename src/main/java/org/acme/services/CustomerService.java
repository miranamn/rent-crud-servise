package org.acme.services;

import org.acme.RentException.RentException;
import org.acme.entity.Customer;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.List;


@Singleton
public class CustomerService {
    @Inject
    Validator validator;
    public List<Customer> getPerson() {
        return Customer.listAll();
    }
    @Transactional
    public Customer addCustomer(Customer newPerson){
        try {
            newPerson.persistAndFlush();
        }catch (ValidationException e){
            throw new RentException(e.getCause().getCause().getMessage());
        }
        catch (PersistenceException e){
            if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new RentException(e.getCause().getCause().getMessage());
            }
            throw e;
        }
        return newPerson;
    }
}
