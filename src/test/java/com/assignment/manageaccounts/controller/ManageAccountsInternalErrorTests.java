package com.assignment.manageaccounts.controller;

import com.assignment.manageaccounts.dao.Customer;
import com.assignment.manageaccounts.exception.ManageAccountsException;
import com.assignment.manageaccounts.model.CustomerRequest;
import com.assignment.manageaccounts.model.TransactionRequest;
import com.assignment.manageaccounts.repository.ManageCustomerRepository;
import com.assignment.manageaccounts.services.ManageAccountsService;
import com.assignment.manageaccounts.utility.SampleCustomerRequestTestData;
import com.assignment.manageaccounts.utility.SampleCustomerResponseTestData;
import com.assignment.manageaccounts.utility.SampleTransactionRequestTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ManageAccountsInternalErrorTests {

    ObjectMapper om = new ObjectMapper();

    @Autowired
    ManageCustomerRepository manageCustomerRepository;

    @MockBean
    ManageAccountsService service;

    @Autowired
    MockMvc mockMvc;

    Map<String, Customer> customerTestData;
    Map<String, CustomerRequest> customerRequestTestData;
    Map<String, TransactionRequest> transactionRequestTestData;

    @Before
    public void setup() {
        customerTestData = SampleCustomerRequestTestData.insertCustomerTestData();
        manageCustomerRepository.save(customerTestData.get("customer_test_data_1"));
    }

    @Test
    public void httpInternalServerErrorInCreateAccount() throws Exception {
        //test new creation
        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecord = customerRequestTestData.get("customer_request_test_data_1");
        Mockito.when(this.service.createAccount(Mockito.any(CustomerRequest.class))).thenThrow(ManageAccountsException.class);
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        String expectedResponse = SampleCustomerResponseTestData.gethttp500InCreateAccountResponse();
        MvcResult mvcResult = mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void httpInternalServerErrorInCreateTransaction() throws Exception {

        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest customerRequest = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(customerRequest)))
                .andDo(print())
                .andExpect(status().isCreated());

        transactionRequestTestData = SampleTransactionRequestTestData.insertTransactionRequestTestData();
        TransactionRequest expectedRecord = transactionRequestTestData.get("transaction_request_test_data_1");
        Mockito.when(this.service.createTransaction(Mockito.any(TransactionRequest.class))).thenThrow(ManageAccountsException.class);
        mockMvc.perform(post("/app/v1/accounts/transactions")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(status().isInternalServerError());

        String expectedResponse = SampleCustomerResponseTestData.gethttp500InCreateAccountResponse();
        MvcResult mvcResult = mockMvc.perform(post("/app/v1/accounts/transactions")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

}
