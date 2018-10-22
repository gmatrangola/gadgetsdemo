package com.matrangola.gadgets.controller;

import com.matrangola.gadgets.data.model.Customer;
import com.matrangola.gadgets.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping
    public Customer makeCustomer(
            @RequestParam String firstName,
            @RequestParam String lastName) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customerService.addCustomer(customer);

        return customer;
    }

}
