package com.matrangola.gadgets.controller;

import com.matrangola.gadgets.data.model.Customer;
import com.matrangola.gadgets.data.model.Gadget;
import com.matrangola.gadgets.data.repository.GadgetRepository;
import com.matrangola.gadgets.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/gadgets", produces = {"application/json"})
public class GadgetController {
    private GadgetRepository gadgetRepository;
    private CustomerService customerService;

    @Autowired
    public GadgetController(GadgetRepository gadgetRepository, CustomerService customerService) {
        this.gadgetRepository = gadgetRepository;
        this.customerService = customerService;
    }

    @RequestMapping
    public List<Gadget> get() {
        return gadgetRepository.findAll();
    }

    @RequestMapping(path = "/add", method = RequestMethod.PUT)
    public Gadget add(@RequestBody Gadget gadget) {
        return gadgetRepository.save(gadget);
    }

    // http://localhost:8080/123/user?customerId= 5
    @RequestMapping(path = "/{gadgetId}/user", method = RequestMethod.POST)
    public void assignUser(@PathVariable Long gadgetId, @RequestParam Long customerId){
        Customer customer = customerService.getCustomer(customerId);
        if (customer != null) {
            Optional<Gadget> gadget = gadgetRepository.findById(gadgetId);
            if (gadget.isPresent()) {
                gadget.get().setOwner(customer);
                gadgetRepository.save(gadget.get());
            }
        }
    }
}
