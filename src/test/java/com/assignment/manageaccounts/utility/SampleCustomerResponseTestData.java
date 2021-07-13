package com.assignment.manageaccounts.utility;

import com.assignment.manageaccounts.model.CustomerResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SampleCustomerResponseTestData {
    public static String getCustomerNotFoundExpected() {
        ObjectMapper mapper = new ObjectMapper();

        String inputJson = "{\n" +
                "    \"result\": \"Failure\",\n" +
                "    \"errorRecords\": [\n" +
                "        {\n" +
                "            \"errorId\": 10001,\n" +
                "            \"errorMessage\": \"Customer not found\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        CustomerResponse customerResponse = null;
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

    public static String getAccountAlreadyExistsExpected() {
        ObjectMapper mapper = new ObjectMapper();

        String inputJson = "{\n" +
                "    \"result\": \"Failure\",\n" +
                "    \"errorRecords\": [\n" +
                "        {\n" +
                "            \"errorId\": 10002,\n" +
                "            \"errorMessage\": \"Customer current account already exist\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        CustomerResponse customerResponse = null;
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

    public static String gethttp400InCreateAccountResponse() {
        ObjectMapper mapper = new ObjectMapper();

        String inputJson = "{\n" +
                "    \"result\": \"Failure\",\n" +
                "    \"errorRecords\": [\n" +
                "        {\n" +
                "            \"errorId\": 400,\n" +
                "            \"errorMessage\": \"Bad Request\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        CustomerResponse customerResponse = null;
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

    public static String gethttp500InCreateAccountResponse() {
        ObjectMapper mapper = new ObjectMapper();

        String inputJson = "{\n" +
                "    \"result\": \"Failure\",\n" +
                "    \"errorRecords\": [\n" +
                "        {\n" +
                "            \"errorId\": 500,\n" +
                "            \"errorMessage\": \"Internal Server Error\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        CustomerResponse customerResponse = null;
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
