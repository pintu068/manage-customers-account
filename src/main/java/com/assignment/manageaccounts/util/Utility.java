package com.assignment.manageaccounts.util;


import com.assignment.manageaccounts.model.*;

import java.util.Currency;
import java.util.List;
import java.util.Random;
import java.util.Set;


/**
 * Utility class for project specific utility functions
 */

public class Utility {


    /**
     * Method to generate IBAN for the customer
     */

    public static String generateIBAN(Long CustomerID) {
        return "NL26BANK" + String.format("%1$" + 10 + "s", CustomerID).replace(' ', '0');
    }


    /**
     * Method to generate Random IBAN for the customer if customer have multiple accounts
     */

    public static String generateRandomIBAN(Long CustomerID) {
        Random random = new Random();
        CustomerID = Long.valueOf(random.nextInt(100000));
        return "NL26BANK" + String.format("%1$" + 10 + "s", CustomerID).replace(' ', '0');
    }


    /**
     * Generic Error response function for Customer REST API
     */

    public static CustomerResponse getErrorResponse(ValidationData validationData, List<ErrorRecord> errorRecords) {
        return new CustomerResponse("Failure", null, null, null, errorRecords);
    }


    /**
     * Generic Error response function for Account REST API
     */

    public static AccountTransactionResponse getErrorResponseForFetch(List<ErrorRecord> errorRecords) {
        return new AccountTransactionResponse("Failure", null, null, null, null, errorRecords);
    }


    /**
     * Generic Error response function for Transaction REST API
     */

    public static TransactionResponse getErrorResponseForTransaction(List<ErrorRecord> errorRecords) {
        return new TransactionResponse("Failure", null, null, null, errorRecords);
    }


    /**
     * Function to check valid account currency
     */

    public static String getDefaultCurrency() {
        String currencyVal = "EUR";
        Set<Currency> currencySet = Currency.getAvailableCurrencies();

        for (Currency currency : currencySet) {
            if (currency.getCurrencyCode() == "EUR")
                currencyVal = currency.getCurrencyCode();
        }
        return currencyVal;
    }
}
