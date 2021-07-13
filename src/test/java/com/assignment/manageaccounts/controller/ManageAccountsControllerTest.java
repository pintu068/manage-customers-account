package com.assignment.manageaccounts.controller;

import com.assignment.manageaccounts.dao.Account;
import com.assignment.manageaccounts.dao.Customer;
import com.assignment.manageaccounts.dao.Transaction;
import com.assignment.manageaccounts.exception.ManageAccountsException;
import com.assignment.manageaccounts.model.AccountTransactionResponse;
import com.assignment.manageaccounts.model.CustomerRequest;
import com.assignment.manageaccounts.model.TransactionRequest;
import com.assignment.manageaccounts.repository.ManageAccountsRepository;
import com.assignment.manageaccounts.repository.ManageCustomerRepository;
import com.assignment.manageaccounts.repository.ManageTransactionRepository;
import com.assignment.manageaccounts.services.ManageAccountsService;
import com.assignment.manageaccounts.utility.SampleCustomerRequestTestData;
import com.assignment.manageaccounts.utility.SampleCustomerResponseTestData;
import com.assignment.manageaccounts.utility.SampleTransactionRequestTestData;
import com.assignment.manageaccounts.utility.SampleTransactionResponseTestData;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ManageAccountsControllerTest {

    ObjectMapper om = new ObjectMapper();

    @Autowired
    ManageCustomerRepository manageCustomerRepository;

    @Autowired
    ManageAccountsRepository manageAccountsRepository;

    @Autowired
    ManageTransactionRepository manageTransactionRepository;

//    @MockBean
//    ManageAccountsService service;

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
    public void testCreateAccount() throws Exception {
        //test new creation
        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecord = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(status().isCreated());

        List<Account> actualRecord = manageAccountsRepository.findByCustomerId(expectedRecord.getCustomerID());
        List<Transaction> actualRecordTxn = manageTransactionRepository.findAllByCustomerID(expectedRecord.getCustomerID());
        BigDecimal intialCredit = BigDecimal.valueOf(0);
        for (Transaction transaction : actualRecordTxn) {
            intialCredit = transaction.getTxnAmount();
        }
        for (Account account : actualRecord) {
            Assert.assertTrue(new ReflectionEquals(expectedRecord.getCustomerID()).matches(account.getCustomerId()));
            Assert.assertTrue(new ReflectionEquals(expectedRecord.getInitialCredit().doubleValue()).matches(intialCredit.doubleValue()));
        }
    }

    @Test
    public void testDuplicateCreateAccount() throws Exception {
        //test new creation
        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecord = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(status().isCreated());

        String expectedResponse = SampleCustomerResponseTestData.getAccountAlreadyExistsExpected();
        MvcResult mvcResult = mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testCustomerNotFoundInCreate() throws Exception {
        //test new creation
        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecord = customerRequestTestData.get("customer_not_found_request");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(status().isCreated());

        String expectedResponse = SampleCustomerResponseTestData.getCustomerNotFoundExpected();
        MvcResult mvcResult = mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    public void testCreateAccountWithZeroBalance() throws Exception {
        //test new creation
        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecord = customerRequestTestData.get("customer_with_zero_balance");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(status().isCreated());

        List<Account> actualRecord = manageAccountsRepository.findByCustomerId(expectedRecord.getCustomerID());
        List<Transaction> actualRecordTxn = manageTransactionRepository.findAllByCustomerID(expectedRecord.getCustomerID());
        BigDecimal intialCredit = BigDecimal.valueOf(0);
        Boolean dataInTransaction = false;
        if (actualRecordTxn.size() == 0) {
            dataInTransaction = true;
        }
        for (Account account : actualRecord) {
            Assert.assertTrue(new ReflectionEquals(expectedRecord.getCustomerID()).matches(account.getCustomerId()));
            Assert.assertTrue(new ReflectionEquals(true).matches(dataInTransaction));
        }
    }

    @Test
    public void testCreateTransactions() throws Exception {

        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecordCustomer = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecordCustomer)))
                .andDo(print())
                .andExpect(status().isCreated());

        transactionRequestTestData = SampleTransactionRequestTestData.insertTransactionRequestTestData();
        TransactionRequest expectedRecord = transactionRequestTestData.get("transaction_request_test_data_1");
        MvcResult mvcResult = mockMvc.perform(post("/app/v1/accounts/transactions")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andReturn();

        List<Transaction> actualRecordTxn = manageTransactionRepository.findAllByCustomerID(expectedRecord.getCustomerID());
        BigDecimal intialCredit = BigDecimal.valueOf(0);
        for (Transaction transaction : actualRecordTxn) {
            intialCredit = transaction.getTxnAmount();
        }
        for (Transaction txn : actualRecordTxn) {
            Assert.assertTrue(new ReflectionEquals(expectedRecord.getCustomerID()).matches(txn.getCustomerID()));
            Assert.assertTrue(new ReflectionEquals(expectedRecord.getTxnAmount().doubleValue()).matches(intialCredit.doubleValue()));
        }
        String expectedResponse = SampleTransactionResponseTestData.createTransactionSuccess();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testAccountNotFoundInCreateTxn() throws Exception {

        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecordCustomer = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecordCustomer)))
                .andDo(print())
                .andExpect(status().isCreated());

        transactionRequestTestData = SampleTransactionRequestTestData.insertTransactionRequestTestData();
        TransactionRequest expectedRecord = transactionRequestTestData.get("transaction_Account_not_found_test_data");
        MvcResult mvcResult = mockMvc.perform(post("/app/v1/accounts/transactions")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andReturn();

        String expectedResponse = SampleTransactionResponseTestData.createTransactionAccountNotFound();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testAccountAndCustomerNotFoundInCreateTxn() throws Exception {

        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecordCustomer = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecordCustomer)))
                .andDo(print())
                .andExpect(status().isCreated());

        transactionRequestTestData = SampleTransactionRequestTestData.insertTransactionRequestTestData();
        TransactionRequest expectedRecord = transactionRequestTestData.get("transaction_Account_And_Customer_found_test_data");
        MvcResult mvcResult = mockMvc.perform(post("/app/v1/accounts/transactions")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andReturn();

        String expectedResponse = SampleTransactionResponseTestData.createTransactionWithIncorrectAcntAndCustomer();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void testZeroTxnInCreateTxn() throws Exception {

        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecordCustomer = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecordCustomer)))
                .andDo(print())
                .andExpect(status().isCreated());

        transactionRequestTestData = SampleTransactionRequestTestData.insertTransactionRequestTestData();
        TransactionRequest expectedRecord = transactionRequestTestData.get("zero_Amount_txn_in_createTxn_test_data");
        MvcResult mvcResult = mockMvc.perform(post("/app/v1/accounts/transactions")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andReturn();

        String expectedResponse = SampleTransactionResponseTestData.createZeroAmountInCreateTxn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    public void testGetCustomerDataByCustomerId() throws Exception {

        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecordCustomer = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecordCustomer)))
                .andDo(print())
                .andExpect(status().isCreated());

        transactionRequestTestData = SampleTransactionRequestTestData.insertTxnRequestDataForGetService();
        for (TransactionRequest transactionRequest : transactionRequestTestData.values()) {
            mockMvc.perform(post("/app/v1/accounts/transactions")
                    .contentType("application/json")
                    .content(om.writeValueAsString(transactionRequest)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        AccountTransactionResponse accountTransactionResponse = SampleTransactionResponseTestData.getAllTransactiononCustomer();
        AccountTransactionResponse actualTransactionWithCustomerId = om.readValue(mockMvc.perform(get("/app/v1/accounts/transactions?customerid=100012378").contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(), new TypeReference<AccountTransactionResponse>() {
        });

        Assert.assertTrue(new ReflectionEquals(accountTransactionResponse.getName()).matches(actualTransactionWithCustomerId.getName()));
        Assert.assertTrue(new ReflectionEquals(accountTransactionResponse.getSurname()).matches(actualTransactionWithCustomerId.getSurname()));
        Assert.assertTrue(new ReflectionEquals(accountTransactionResponse.getBalance().doubleValue()).matches(actualTransactionWithCustomerId.getBalance().doubleValue()));
        Assert.assertTrue(new ReflectionEquals(accountTransactionResponse.getTransactions().size()).matches(actualTransactionWithCustomerId.getTransactions().size()));

        for (int i = 0; i < accountTransactionResponse.getTransactions().size(); i++) {
            Assert.assertTrue(new ReflectionEquals(accountTransactionResponse.getTransactions().get(i).getCustomerID()).matches(actualTransactionWithCustomerId.getTransactions().get(i).getCustomerID()));
            Assert.assertTrue(new ReflectionEquals(accountTransactionResponse.getTransactions().get(i).getIban()).matches(actualTransactionWithCustomerId.getTransactions().get(i).getIban()));
            Assert.assertTrue(new ReflectionEquals(accountTransactionResponse.getTransactions().get(i).getTxnAmount().doubleValue()).matches(actualTransactionWithCustomerId.getTransactions().get(i).getTxnAmount().doubleValue()));
            Assert.assertTrue((accountTransactionResponse.getTransactions().get(i).getCurrency()).equals(actualTransactionWithCustomerId.getTransactions().get(i).getCurrency()));
        }
    }

    @Test
    public void validateCustomerDataWithIncorrectLinkedAccount() throws Exception {

        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecordCustomer = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecordCustomer)))
                .andDo(print())
                .andExpect(status().isCreated());

        transactionRequestTestData = SampleTransactionRequestTestData.insertTxnRequestDataForGetService();
        for (TransactionRequest transactionRequest : transactionRequestTestData.values()) {
            mockMvc.perform(post("/app/v1/accounts/transactions")
                    .contentType("application/json")
                    .content(om.writeValueAsString(transactionRequest)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        AccountTransactionResponse accountTransactionResponse = SampleTransactionResponseTestData.getAllTransactiononCustomer();
        MvcResult mvcResult = mockMvc.perform(get("/app/v1/accounts/transactions?customerid=100012378&iban=1234")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        String expectedResponse = SampleTransactionResponseTestData.validateCustomerWithAccount();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void validateCustomerInGetTransaction() throws Exception {

        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecordCustomer = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecordCustomer)))
                .andDo(print())
                .andExpect(status().isCreated());

        transactionRequestTestData = SampleTransactionRequestTestData.insertTxnRequestDataForGetService();
        for (TransactionRequest transactionRequest : transactionRequestTestData.values()) {
            mockMvc.perform(post("/app/v1/accounts/transactions")
                    .contentType("application/json")
                    .content(om.writeValueAsString(transactionRequest)))
                    .andDo(print())
                    .andExpect(status().isCreated());
        }

        AccountTransactionResponse accountTransactionResponse = SampleTransactionResponseTestData.getAllTransactiononCustomer();
        MvcResult mvcResult = mockMvc.perform(get("/app/v1/accounts/transactions?customerid=10001237")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        String expectedResponse = SampleCustomerResponseTestData.getCustomerNotFoundExpected();
        assertEquals(expectedResponse, actualResponse);
    }


    @Test
    public void httpBadRequestErrorInCreateAccount() throws Exception {
        //test new creation
        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecord = customerRequestTestData.get("customer_request_http400_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(status().isBadRequest());

        String expectedResponse = SampleCustomerResponseTestData.gethttp400InCreateAccountResponse();
        MvcResult mvcResult = mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void httpBadRequestErrorInCreateTxn() throws Exception {
        //test new creation
        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecord = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(status().isCreated());

        transactionRequestTestData = SampleTransactionRequestTestData.insertTransactionRequestTestData();
        TransactionRequest txnExpectedRecord = transactionRequestTestData.get("txn_create_http400_bad_request");
        MvcResult mvcResult = mockMvc.perform(post("/app/v1/accounts/transactions")
                .contentType("application/json")
                .content(om.writeValueAsString(txnExpectedRecord)))
                .andDo(print())
                .andReturn();

        String expectedResponse = SampleCustomerResponseTestData.gethttp400InCreateAccountResponse();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void httpParameterMissingErrorInCustomerInfo() throws Exception {
        //test new creation
        customerRequestTestData = SampleCustomerRequestTestData.insertCustomerRequestTestData();
        CustomerRequest expectedRecord = customerRequestTestData.get("customer_request_test_data_1");
        mockMvc.perform(post("/app/v1/accounts")
                .contentType("application/json")
                .content(om.writeValueAsString(expectedRecord)))
                .andDo(print())
                .andExpect(status().isCreated());

        AccountTransactionResponse accountTransactionResponse = SampleTransactionResponseTestData.getAllTransactiononCustomer();
        MvcResult mvcResult = mockMvc.perform(get("/app/v1/accounts/transactions?customerid=")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        String expectedResponse = SampleTransactionResponseTestData.getParameterMissingInCustomerInfoResponse();
        assertEquals(expectedResponse, actualResponse);
    }
}
