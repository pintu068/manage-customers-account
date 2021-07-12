package com.assignment.manageaccounts.repository;

import com.assignment.manageaccounts.dao.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository for persistent entity Customer
 */

@Repository
public interface ManageCustomerRepository extends JpaRepository<Customer, Long> {
    public Customer findByCustomerId(long customerId);
}
