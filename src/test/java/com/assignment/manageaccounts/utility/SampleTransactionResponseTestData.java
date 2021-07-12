package com.assignment.manageaccounts.utility;

import com.assignment.manageaccounts.dao.Transaction;
import com.assignment.manageaccounts.model.AccountTransactionResponse;
import com.assignment.manageaccounts.model.CustomerResponse;
import com.assignment.manageaccounts.model.TransactionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleTransactionResponseTestData {

    public static AccountTransactionResponse getAllTransactiononCustomer() {

        Map<String, List<Transaction>> data = new HashMap<>();
        Transaction transaction1 = new Transaction(1,100012378l,"NL26BANK0100012378","EUR", Timestamp.valueOf("2021-07-11 10:21:41"), BigDecimal.valueOf(100.00));
        Transaction transaction2 = new Transaction(1,100012378l,"NL26BANK0100012378","EUR", Timestamp.valueOf("2021-07-11 10:21:41"),BigDecimal.valueOf(100.00));
        Transaction transaction3 = new Transaction(1,100012378l,"NL26BANK0100012378","EUR", Timestamp.valueOf("2021-07-11 10:21:41"),BigDecimal.valueOf(200.00));

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        transactionList.add(transaction3);


        AccountTransactionResponse transaction_response_test_data_1 = new AccountTransactionResponse(
                null,
                "Arjo",
                "Vliet",
                BigDecimal.valueOf(400.0),
                transactionList,null);


        return transaction_response_test_data_1;
    }

    public static String createTransactionSuccess() {
        ObjectMapper mapper = new ObjectMapper();

        String inputJson = "{\n" +
                "    \"result\": \"Success\",\n" +
                "    \"transactionId\": 2,\n" +
                "    \"customerId\": 100012378,\n" +
                "    \"iban\": \"NL26BANK0100012378\"\n" +
                "}";

        TransactionResponse transactionResponse=null ;
        try {
            transactionResponse = mapper.readValue(inputJson, TransactionResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (transactionResponse != null) {
            try {
                inputJson = mapper.writeValueAsString(transactionResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return inputJson;
    }

    public static String createTransactionAccountNotFound() {
        ObjectMapper mapper = new ObjectMapper();

        String inputJson="{\n" +
                "    \"result\": \"Failure\",\n" +
                "    \"errorRecords\": [\n" +
                "        {\n" +
                "            \"errorId\": 10003,\n" +
                "            \"errorMessage\": \"Customer account not found\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        TransactionResponse transactionResponse=null ;
        try {
            transactionResponse = mapper.readValue(inputJson, TransactionResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (transactionResponse != null) {
            try {
                inputJson = mapper.writeValueAsString(transactionResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return inputJson;
    }

    public static String createTransactionWithIncorrectAcntAndCustomer() {
        ObjectMapper mapper = new ObjectMapper();

        String inputJson="{\n" +
                "    \"result\": \"Failure\",\n" +
                "    \"errorRecords\": [\n" +
                "        {\n" +
                "            \"errorId\": 10001,\n" +
                "            \"errorMessage\": \"Customer not found\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"errorId\": 10003,\n" +
                "            \"errorMessage\": \"Customer account not found\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        TransactionResponse transactionResponse=null ;
        try {
            transactionResponse = mapper.readValue(inputJson, TransactionResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (transactionResponse != null) {
            try {
                inputJson = mapper.writeValueAsString(transactionResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return inputJson;
    }

    public static String createZeroAmountInCreateTxn() {
        ObjectMapper mapper = new ObjectMapper();

        String inputJson="{\n" +
                "    \"result\": \"Failure\",\n" +
                "    \"errorRecords\": [\n" +
                "        {\n" +
                "            \"errorId\": 10004,\n" +
                "            \"errorMessage\": \"Transaction for zero amount not possible\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        TransactionResponse transactionResponse=null ;
        try {
            transactionResponse = mapper.readValue(inputJson, TransactionResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (transactionResponse != null) {
            try {
                inputJson = mapper.writeValueAsString(transactionResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return inputJson;
    }

    public static String validateCustomerWithAccount() {
        ObjectMapper mapper = new ObjectMapper();

        String inputJson="{\n" +
                "    \"result\": \"Failure\",\n" +
                "    \"errorRecords\": [\n" +
                "        {\n" +
                "            \"errorId\": 10005,\n" +
                "            \"errorMessage\": \"Customer not linked with given Account\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        AccountTransactionResponse accountTransactionResponse=null ;
        try {
            accountTransactionResponse = mapper.readValue(inputJson, AccountTransactionResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (accountTransactionResponse != null) {
            try {
                inputJson = mapper.writeValueAsString(accountTransactionResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return inputJson;
    }


    public static String getParameterMissingInCustomerInfoResponse() {
        ObjectMapper mapper = new ObjectMapper();

        String inputJson = "{\n" +
                "    \"result\": \"Failure\",\n" +
                "    \"errorRecords\": [\n" +
                "        {\n" +
                "            \"errorId\": 400,\n" +
                "            \"errorMessage\": \"Parameter is missing\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        CustomerResponse customerResponse=null ;
        try {
            customerResponse = mapper.readValue(inputJson, CustomerResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (customerResponse != null) {
            try {
                inputJson = mapper.writeValueAsString(customerResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return inputJson;
    }


}
