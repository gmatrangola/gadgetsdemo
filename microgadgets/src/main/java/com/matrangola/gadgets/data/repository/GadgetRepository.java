package com.matrangola.gadgets.data.repository;

import com.matrangola.gadgets.data.model.Gadget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GadgetRepository extends JpaRepository<Gadget, Long> {
}
