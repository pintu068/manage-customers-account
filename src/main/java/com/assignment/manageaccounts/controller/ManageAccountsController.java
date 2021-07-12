package com.assignment.manageaccounts.controller;

import com.assignment.manageaccounts.model.*;
import com.assignment.manageaccounts.services.ManageAccountsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.lang.invoke.MethodHandles;

/**
 * Class to handle Customer and Account related REST APIs
 */

@Transactional
@RestController
@RequestMapping("app/v1")
public class ManageAccountsController {
    public static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    ManageAccountsService manageAccountsService;


    /**
     * REST Endpoint to create current account of existing customer
     *
     * @Request Customer ID & Initial Amount
     * @Return Customer ID , IBAN and initial credit done by customer
     */

    @PostMapping(value = "/accounts", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CustomerResponse> createAccount(@Validated @RequestBody final CustomerRequest customerRequest, final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            logger.error("Error during parsing JSON and Validation");
            throw new HttpMessageConversionException("Bad request");
        }

        CustomerResponse response = manageAccountsService.createAccount(customerRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    /**
     * REST Endpoint to fetch Customer Info and transaction list
     *
     * @Request Query parameters are passed with mandatory Customer ID and optional IBAN
     * @Return customer date i.e. name , surname , balance and transaction list
     * Transaction list contains transaction id, Customer Id, IBAN, currency, transaction date & transaction Amount
     */

    @GetMapping(value = "/accounts/transactions", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AccountTransactionResponse> getAccountInfo(@Validated @RequestParam(required = true) Long customerid, @RequestParam(required = false) String iban) {

        AccountTransactionResponse response = manageAccountsService.getCustomerInformation(customerid, iban);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * REST Endpoint to create transaction against customer IBAN account
     *
     * @Request Customer ID, IBAN , Currency and transaction Amount
     * @Return Transaction Id, Customer ID & IBAN
     */

    @PostMapping(value = "/accounts/transactions", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TransactionResponse> createTransaction(@Validated @RequestBody final TransactionRequest transactionRequest, final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            logger.error("Error during parsing JSON and Validation");
            throw new HttpMessageConversionException("Bad request");
        }

        TransactionResponse response = manageAccountsService.createTransaction(transactionRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
