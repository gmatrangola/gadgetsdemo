package com.matrangola.gadgets.controller;

import com.matrangola.gadgets.client.CustomerClient;
import com.matrangola.gadgets.data.model.Color;
import com.matrangola.gadgets.data.model.Gadget;
import com.matrangola.gadgets.data.repository.ColorRepository;
import com.matrangola.gadgets.data.repository.GadgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/gadgets", produces = {"application/json"})
public class GadgetController {
    private final CustomerClient customerClient;
    private final GadgetRepository gadgetRepository;
    private final ColorRepository colorRepository;

    @Autowired
    public GadgetController(CustomerClient customerClient,
                            GadgetRepository gadgetRepository,
                            ColorRepository colorRepository) {
        this.customerClient = customerClient;
        this.gadgetRepository = gadgetRepository;
        this.colorRepository = colorRepository;
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
//        Customer customer = customerService.getCustomer(customerId);
//        if (customer != null) {
//            Optional<Gadget> gadget = gadgetRepository.findById(gadgetId);
//            if (gadget.isPresent()) {
//                gadget.get().setOwner(customer);
//                gadgetRepository.save(gadget.get());
//            }
//        }
    }

    @RequestMapping(path = "/emailWithGadget", method = RequestMethod.GET)
    public List<String> emailWithGadget(@RequestParam String gadgetName) {
        List<Gadget> gadgets = gadgetRepository.findByName(gadgetName);
        List<String> emails = new ArrayList<>();
        for (Gadget gadget : gadgets) {
            emails.add(customerClient.findEmailById(gadget.getCustomerId()));
        }
        return emails;
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
