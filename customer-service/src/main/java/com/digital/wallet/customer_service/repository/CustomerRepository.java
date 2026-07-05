package com.digital.wallet.customer_service.repository;

import com.digital.wallet.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    List<Customer> findByUserIdIn(String... userId);
}
