package com.matrangola.gadgets.data.repository;

import com.matrangola.gadgets.data.model.Gadget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GadgetRepository extends JpaRepository<Gadget, Long> {
    List<Gadget> findByName(String gadgetName);
}
