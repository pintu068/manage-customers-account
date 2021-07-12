package com.assignment.manageaccounts.util;


import com.assignment.manageaccounts.model.*;
import org.springframework.http.server.DelegatingServerHttpResponse;

import java.util.Currency;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Utility {

    public static String generateIBAN(Long CustomerID)
    {
        return "NL26BANK"+String.format("%1$" + 10 + "s", CustomerID).replace(' ', '0');
    }
    public static String generateRandomIBAN(Long CustomerID)
    {
        Random random = new Random();
        CustomerID= Long.valueOf(random.nextInt(100000));
        return "NL26BANK"+String.format("%1$" + 10 + "s", CustomerID).replace(' ', '0');
    }

    public static CustomerResponse getErrorResponse(ValidationData validationData, List<ErrorRecord> errorRecords)
    {
            return new CustomerResponse("Failure",null,null,null,errorRecords);
    }

    public static AccountTransactionResponse getErrorResponseForFetch(List<ErrorRecord> errorRecords)
    {
            return new AccountTransactionResponse("Failure",null,null,null,null,errorRecords);
    }

    public static TransactionResponse getErrorResponseForTransaction(List<ErrorRecord> errorRecords)
    {
        return new TransactionResponse("Failure",null,null,null,errorRecords);
    }

    public static String getDefaultCurrency()
    {
        String currencyVal="EUR";
        Set<Currency> currencySet =  Currency.getAvailableCurrencies();

        for(Currency currency : currencySet)
        {
            if(currency.getCurrencyCode() == "EUR")
                currencyVal = currency.getCurrencyCode();
        }
        return currencyVal;
    }
}
