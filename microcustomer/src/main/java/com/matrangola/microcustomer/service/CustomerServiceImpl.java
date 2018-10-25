package com.matrangola.microcustomer.service;

import com.matrangola.microcustomer.data.model.Customer;
import com.matrangola.microcustomer.data.repository.CustomerRepository;
import com.matrangola.microcustomer.exception.ResourceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(Long id) throws ResourceException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) return customer.get();
        else throw new ResourceException(Customer.class, id);
    }
}
