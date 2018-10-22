package com.matrangola.gadgets.controller;

import com.matrangola.gadgets.data.model.Customer;
import com.matrangola.gadgets.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/customers", produces = {"application/json"})
public class CustomerController {
    private static final SimpleDateFormat BIRTHDAY_TEXT_FORMAT = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private CustomerService customerService;

    @RequestMapping(path = "/new", method = RequestMethod.GET)
    public Customer add(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(name="birthday", required = false) String birthday) {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        if (birthday != null) {
            try {
                Date bd = BIRTHDAY_TEXT_FORMAT.parse(birthday);
                customer.setBirthday(bd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        customerService.addCustomer(customer);

        return customer;
    }

    @RequestMapping(path = "/older/{age}", method = RequestMethod.GET)
    public List<Customer> older(@PathVariable int age) {
        List<Customer> customers = customerService.getCustomers();
        Calendar old = Calendar.getInstance();
        old.setTime(new Date());
        old.add(Calendar.YEAR, age * -1);

        return customers.stream().filter(customer ->
                customer.getBirthday().before(old.getTime())).collect(Collectors.toList());
    }

}
