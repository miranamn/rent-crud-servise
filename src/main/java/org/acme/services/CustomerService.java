package org.acme.services;

import org.acme.entity.Customer;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;


@Singleton
public class CustomerService {

    public List<Customer> getPerson() {
        return Customer.listAll();
    }
    @Transactional
    public Customer addCustomer(Customer newPerson){
        newPerson.persistAndFlush();
        System.out.println(newPerson);
        return newPerson;
    }
}
