package org.acme.services;

import org.acme.RentException.RentException;
import org.acme.entity.Customer;

import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;


@Singleton
public class CustomerService {

    public List<Customer> getPerson(String login) {
        return login == null ?  getPerson() : getPersonByLogin(login);
    }
    private List<Customer> getPerson() {
        return Customer.listAll();
    }

    public List<Customer> getPersonByLogin(String login){
        return Customer.find("login = ?1", login).list();
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

    @Transactional
    public Response deleteCustomer(Long id){
        Customer.deleteById(id);
        return Response.status(OK).build();
    }

    @Transactional
    public Customer updateCustomer(long id, Customer newCustomer){
        Customer customer = Customer.findById(id);
        try {
            customer.setFirstName(newCustomer.getFirstName());
            customer.setSecondName(newCustomer.getSecondName());
            customer.setLogin(newCustomer.getLogin());
            customer.setPassword(newCustomer.getPassword());
            customer.setPhone(newCustomer.getPhone());
        }
        catch (ValidationException e){
            throw new RentException(e.getCause().getCause().getMessage());
        }
        catch (PersistenceException e){
            if(e.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
                throw new RentException(e.getCause().getCause().getMessage());
            }
            throw e;
        }
        return customer;
    }
}
