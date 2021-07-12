package com.assignment.manageaccounts.repository;

import com.assignment.manageaccounts.dao.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository for persistent entity Account
 */

@Repository
public interface ManageAccountsRepository extends JpaRepository<Account, String> {

    public List<Account> findByCustomerId(long customerId);

    public List<Account> findByCustomerIdAndIban(long customerId, String iban);
}
