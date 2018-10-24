package com.matrangola.microcustomer.data.repository;

import com.matrangola.microcustomer.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
