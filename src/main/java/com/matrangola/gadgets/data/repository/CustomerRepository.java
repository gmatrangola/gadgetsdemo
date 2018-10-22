package com.matrangola.gadgets.data.repository;

import com.matrangola.gadgets.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
