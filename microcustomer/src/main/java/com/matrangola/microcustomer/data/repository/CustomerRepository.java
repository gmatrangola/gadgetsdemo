package com.matrangola.microcustomer.data.repository;

import com.matrangola.microcustomer.data.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    UserDetails findOneByUsername(String username);
}
