package com.assignment.manageaccounts.services;

import com.assignment.manageaccounts.constants.ValidationChainType;
import com.assignment.manageaccounts.dao.Account;
import com.assignment.manageaccounts.dao.Customer;
import com.assignment.manageaccounts.dao.Transaction;
import com.assignment.manageaccounts.exception.ManageAccountsException;
import com.assignment.manageaccounts.model.*;
import com.assignment.manageaccounts.repository.ManageAccountsRepository;
import com.assignment.manageaccounts.repository.ManageCustomerRepository;
import com.assignment.manageaccounts.repository.ManageTransactionRepository;
import com.assignment.manageaccounts.util.Utility;
import com.assignment.manageaccounts.validator.Validator;
import com.assignment.manageaccounts.validator.ValidatorChainGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * Service class to handle validations and REST API business handling
 */

@Service
public class ManageAccountsService {
    public static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    ManageAccountsRepository manageAccountsRepository;
    @Autowired
    ManageCustomerRepository manageCustomerRepository;
    @Autowired
    ManageTransactionRepository manageTransactionRepository;


    /**
     * Method for account creation and validation
     *
     * @return CustomerResponse for REST API response
     * @requestParam Customer Request from REST API request
     */

    public CustomerResponse createAccount(CustomerRequest customerRequest) {
        try {

            ValidationData validationData = new ValidationData();
            validationData.setCustomerID(customerRequest.getCustomerID());
            List<ErrorRecord> errorRecords = validate(validationData, ValidationChainType.ACCOUNTCREATION);
            if (!CollectionUtils.isEmpty(errorRecords))
                return Utility.getErrorResponse(validationData, errorRecords);
            else
                return create(customerRequest);
        } catch (RuntimeException ex) {
            logger.error(ex.getMessage());
            throw new ManageAccountsException(ex.getMessage());
        }
    }


    /**
     * Method to fetch customer Info and transactions in REST API
     *
     * @return AccountTransactionResponse for REST API response
     * @requestParam ID (mandatory) and IBAN(optional) in endpoint query parameter
     */

    public AccountTransactionResponse getCustomerInformation(Long customerId, String iban) {

        try {
            ValidationData validationData = new ValidationData();
            validationData.setCustomerID(customerId);
            if (iban != null)
                validationData.setIban(iban);
            List<ErrorRecord> errorRecords = validate(validationData, ValidationChainType.CUSTOMERINFORMATION);
            if (!CollectionUtils.isEmpty(errorRecords))
                return Utility.getErrorResponseForFetch(errorRecords);
            else
                return fetch(customerId, iban);
        } catch (RuntimeException ex) {
            logger.error(ex.getMessage());
            throw new ManageAccountsException(ex.getMessage());
        }
    }


    /**
     * Method to create transactions in REST API
     *
     * @return TransactionResponse for REST API response
     * @requestParam as from rest API request
     */

    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {

        try {
            ValidationData validationData = new ValidationData();
            Transaction transaction;
            validationData.setCustomerID(transactionRequest.getCustomerID());
            validationData.setIban(transactionRequest.getIban());
            validationData.setInitialCredit(transactionRequest.getTxnAmount());
            List<ErrorRecord> errorRecords = validate(validationData, ValidationChainType.TRANSACTIONCREATION);
            if (!CollectionUtils.isEmpty(errorRecords)) {
                return Utility.getErrorResponseForTransaction(errorRecords);
            } else {
                transaction = createTransactionData(transactionRequest);
                return new TransactionResponse("Success", transaction.getTransactionId(), transaction.getCustomerID(), transaction.getIban(), null);
            }
        } catch (RuntimeException ex) {
            logger.error(ex.getMessage());
            throw new ManageAccountsException(ex.getMessage());
        }
    }


    /**
     * Main Function to call ValidatorChain for all validations
     */

    public List<ErrorRecord> validate(ValidationData validationData, ValidationChainType validationChainType) {
        List<ErrorRecord> response;
        Validator validator = ValidatorChainGenerator.getValidatorChain(validationChainType);
        response = validator.validate(validationData);
        return response;
    }

    /**
     * Handler Function called from create account REST service method to
     * consolidate information from different handlers for response
     */
    public CustomerResponse create(CustomerRequest customerRequest) {
        Account account = createAccountData(customerRequest);
        Transaction transactionData = createTransactionData(customerRequest);
        CustomerResponse customersavedata = MergeAccountAndTransaction(account, transactionData);
        return customersavedata;
    }


    /**
     * Handler Function called from create account REST service method to
     * create and save data in account persistent table
     */

    public Account createAccountData(CustomerRequest customerRequest) {
        Account account = new Account();
        account.setCustomerId(customerRequest.getCustomerID());
        List<Account> accounts = manageAccountsRepository.findByCustomerId(customerRequest.getCustomerID());
        if (CollectionUtils.isEmpty(accounts))
            account.setIban(Utility.generateIBAN(customerRequest.getCustomerID()));
        else
            account.setIban(Utility.generateRandomIBAN(customerRequest.getCustomerID()));
        account.setCurrency(Utility.getDefaultCurrency());
        account.setAccountOpeningDate(Timestamp.valueOf(LocalDateTime.now()));
        manageAccountsRepository.save(account);
        return account;
    }


    /**
     * Handler Function called from create account REST service method to
     * create and save data in transaction persistent table if amount is greater than ZERO
     */

    public Transaction createTransactionData(CustomerRequest customerRequest) {
        Transaction transaction = new Transaction();

        if (customerRequest.getInitialCredit().compareTo(BigDecimal.valueOf(0.0)) > 0) {
            transaction.setCustomerID(customerRequest.getCustomerID());
            transaction.setIban(Utility.generateIBAN(customerRequest.getCustomerID()));
            transaction.setCurrency(Utility.getDefaultCurrency());
            transaction.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
            transaction.setTxnAmount(customerRequest.getInitialCredit());
            manageTransactionRepository.save(transaction);
        }
        return transaction;
    }


    /**
     * Function create and save data in transaction persistent table if amount is greater than ZERO
     * for create transaction API service
     */

    public Transaction createTransactionData(TransactionRequest transactionRequest) {
        Transaction transaction = new Transaction();

        if (transactionRequest.getTxnAmount().compareTo(BigDecimal.valueOf(0.0)) > 0) {
            transaction.setCustomerID(transactionRequest.getCustomerID());
            transaction.setIban(transactionRequest.getIban());
            transaction.setCurrency(transactionRequest.getCurrency());
            transaction.setTransactionDate(Timestamp.valueOf(LocalDateTime.now()));
            transaction.setTxnAmount(transactionRequest.getTxnAmount());
            manageTransactionRepository.save(transaction);
        }
        return transaction;
    }




    public CustomerResponse MergeAccountAndTransaction(Account account, Transaction transaction) {
        CustomerResponse customersavedata = new CustomerResponse();
        List<Account> accountData = manageAccountsRepository.findByCustomerId(account.getCustomerId());
        for (Account accountEntry : accountData) {
            customersavedata.setCustomerID(accountEntry.getCustomerId());
            customersavedata.setAccountNumber(accountEntry.getIban());
        }
        customersavedata.setInitialCredit(transaction.getTxnAmount());
        customersavedata.setResult("Success");

        return customersavedata;
    }

    public AccountTransactionResponse fetch(Long customerID, String iban) {
        BigDecimal balance = BigDecimal.valueOf(0.0);
        Customer customer = fetchCustomerData(customerID);
        List<Transaction> transactionList = fetchTransactionData(customerID, iban);
        if (!CollectionUtils.isEmpty(transactionList))
            balance = getBalance(transactionList);
        return MergeCustomerDataInformation(customer, transactionList, balance);
    }

    public Customer fetchCustomerData(Long customerID) {
        Customer customerInformation = null;
        Optional<Customer> customer = manageCustomerRepository.findById(customerID);
        if (customer.isPresent())
            customerInformation = customer.get();
        return customerInformation;
    }

    public List<Transaction> fetchTransactionData(long customerID, String iban) {
        Double balance = 0.0;
        List<Transaction> transactionList = new ArrayList<>();
        if (iban == null)
            transactionList = manageTransactionRepository.findAllByCustomerID(customerID);
        else
            transactionList = manageTransactionRepository.findAllByCustomerIDAndIban(customerID, iban);

            return transactionList;
    }

    public BigDecimal getBalance(List<Transaction> transactionList) {
        Double balance;
        balance = transactionList.parallelStream()
                .map(txnAmount -> txnAmount.getTxnAmount())
                .mapToDouble(txnAmount -> txnAmount.doubleValue()).sum();
        return BigDecimal.valueOf(balance);
    }

    public AccountTransactionResponse MergeCustomerDataInformation(Customer customer, List<Transaction> transactionList, BigDecimal balance) {
        AccountTransactionResponse accountTransactionResponse = new AccountTransactionResponse();
        accountTransactionResponse.setName(customer.getName());
        accountTransactionResponse.setSurname(customer.getSurname());
        accountTransactionResponse.setBalance(balance);
        accountTransactionResponse.setTransactions(transactionList);
        accountTransactionResponse.setResult("Success");
        return accountTransactionResponse;
    }
}

