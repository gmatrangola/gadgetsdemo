package com.matrangola.gadgets.service;

import com.matrangola.gadgets.data.model.Customer;

import java.util.List;

public interface CustomerService {
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    List<Customer> getCustomers();
}
