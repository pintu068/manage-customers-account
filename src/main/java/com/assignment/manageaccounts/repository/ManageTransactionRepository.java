package com.assignment.manageaccounts.repository;

import com.assignment.manageaccounts.dao.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository for persistent entity Transaction
 */

@Repository
public interface ManageTransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findAllByCustomerID(long customerId);

    public List<Transaction> findAllByCustomerIDAndIban(long customerId, String iban);
}
