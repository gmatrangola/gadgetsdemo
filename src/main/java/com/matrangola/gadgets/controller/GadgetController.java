package com.matrangola.gadgets.controller;

import com.matrangola.gadgets.data.model.Color;
import com.matrangola.gadgets.data.model.Customer;
import com.matrangola.gadgets.data.model.Gadget;
import com.matrangola.gadgets.data.repository.ColorRepository;
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
    private ColorRepository colorRepository;
    private CustomerService customerService;

    @Autowired
    public GadgetController(GadgetRepository gadgetRepository, ColorRepository colorRepository,
                            CustomerService customerService) {
        this.gadgetRepository = gadgetRepository;
        this.colorRepository = colorRepository;
        this.customerService = customerService;
    }

    @RequestMapping
    public List<Gadget> get() {
        return gadgetRepository.findAll();
    }

    @RequestMapping(path = "/{id}")
    public Gadget get(@PathVariable Long id) {
        Optional<Gadget> gadget = gadgetRepository.findById(id);
        if (gadget.isPresent()) return gadget.get();
        else return null;
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

    // PUT http://localhost:8080/123/color <-- json(Color)
    @RequestMapping(path = "/{gadgetId}/color", method = RequestMethod.PUT)
    public void assignColor(@PathVariable Long gadgetId, @RequestBody Color color) {
        Optional<Gadget> gadget = gadgetRepository.findById(gadgetId);
        if (gadget.isPresent()) {
            colorRepository.save(color);
            gadget.get().setColor(color);
        }
    }

}
