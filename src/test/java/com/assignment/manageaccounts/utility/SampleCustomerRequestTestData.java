package com.assignment.manageaccounts.utility;

import com.assignment.manageaccounts.dao.Customer;
import com.assignment.manageaccounts.model.CustomerRequest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class SampleCustomerRequestTestData {

    public static Map<String, Customer> insertCustomerTestData() {
        Map<String, Customer> data = new HashMap<>();

        Customer customer_test_data_1 = new Customer(
                100012378l,
                "Arjo",
                "Vliet");
        data.put("customer_test_data_1", customer_test_data_1);

        Customer customer_test_data_2 = new Customer(
                100012700l,
                "Pintu",
                "Kumar");
        data.put("customer_test_data_2", customer_test_data_2);

        return data;
    }

    public static Map<String, CustomerRequest> insertCustomerRequestTestData() {
        Map<String, CustomerRequest> data = new HashMap<>();
        CustomerRequest customer_request_test_data_1 = new CustomerRequest(
                100012378l,
                BigDecimal.valueOf(100));
        data.put("customer_request_test_data_1", customer_request_test_data_1);

        CustomerRequest customer_request_http400_data_1 = new CustomerRequest(
                null,
                BigDecimal.valueOf(100));
        data.put("customer_request_http400_data_1", customer_request_http400_data_1);

        CustomerRequest customer_not_found_request = new CustomerRequest(
                10012378l,
                BigDecimal.valueOf(100));
        data.put("customer_not_found_request", customer_not_found_request);

        CustomerRequest customer_with_zero_balance = new CustomerRequest(
                100012700l,
                BigDecimal.valueOf(0));
        data.put("customer_with_zero_balance", customer_with_zero_balance);
        return data;
    }

}
