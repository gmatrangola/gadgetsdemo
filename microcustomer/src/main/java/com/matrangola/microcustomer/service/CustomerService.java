package com.matrangola.microcustomer.service;


import com.matrangola.microcustomer.data.model.Customer;

import java.util.List;

public interface CustomerService {
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    List<Customer> getCustomers();

    Customer getCustomer(Long id);
}

