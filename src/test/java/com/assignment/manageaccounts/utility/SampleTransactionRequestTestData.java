package com.assignment.manageaccounts.utility;

import com.assignment.manageaccounts.model.TransactionRequest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SampleTransactionRequestTestData {

    public static Map<String, TransactionRequest> insertTxnRequestDataForGetService() {
        Map<String, TransactionRequest> data = new HashMap<>();
        TransactionRequest transaction_request_test_data_1 = new TransactionRequest(
                100012378l,
                "NL26BANK0100012378",
                "EUR",
                BigDecimal.valueOf(100));
        data.put("transaction_request_test_data_1", transaction_request_test_data_1);

        TransactionRequest transaction_request_test_data_2 = new TransactionRequest(
                100012378l,
                "NL26BANK0100012378",
                "EUR",
                BigDecimal.valueOf(200));
        data.put("transaction_request_test_data_2", transaction_request_test_data_2);
        return data;
    }

    public static Map<String, TransactionRequest> insertTransactionRequestTestData() {
        Map<String, TransactionRequest> data = new HashMap<>();
        TransactionRequest transaction_request_test_data_1 = new TransactionRequest(
                100012378l,
                "NL26BANK0100012378",
                "EUR",
                BigDecimal.valueOf(100));
        data.put("transaction_request_test_data_1", transaction_request_test_data_1);

        TransactionRequest transaction_request_test_data_2 = new TransactionRequest(
                100012378l,
                "NL26BANK0100012378",
                "EUR",
                BigDecimal.valueOf(200));
        data.put("transaction_request_test_data_2", transaction_request_test_data_2);

        TransactionRequest transaction_Account_not_found_test_data = new TransactionRequest(
                100012378l,
                "NL26BANK0100012900",
                "EUR",
                BigDecimal.valueOf(200));
        data.put("transaction_Account_not_found_test_data", transaction_Account_not_found_test_data);

        TransactionRequest transaction_Account_And_Customer_found_test_data = new TransactionRequest(
                10001237l,
                "NL26BANK0100012900",
                "EUR",
                BigDecimal.valueOf(100.05));
        data.put("transaction_Account_And_Customer_found_test_data", transaction_Account_And_Customer_found_test_data);

        TransactionRequest zero_Amount_txn_in_createTxn_test_data = new TransactionRequest(
                100012378l,
                "NL26BANK0100012378",
                "EUR",
                BigDecimal.valueOf(0));
        data.put("zero_Amount_txn_in_createTxn_test_data", zero_Amount_txn_in_createTxn_test_data);

        TransactionRequest txn_create_http400_bad_request = new TransactionRequest(
                null,
                "NL26BANK0100012378",
                "EUR",
                BigDecimal.valueOf(0));
        data.put("txn_create_http400_bad_request", txn_create_http400_bad_request);

        return data;
    }
}
