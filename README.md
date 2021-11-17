# Manage Customers Account
Manage customer accounts and transactions
## Assignment

The Assessment consist of an API to be used for opening a new "current account" of already existing customers.

The API will expose an endpoint which accepts the user information(customerID, initialCredit).
Once the endpoint is called, a new account will be opened connected to the user whose id is customerID.
Also, if initialCredit is not 0, a transaction will be sent to the new account.
Another Endpoint will output the user information showing Name,Surname,balance and transactions of the accounts.



### REST APIs and Functionalities

-- Test Change


#### 1. Prerequisite
Customers should already exist in system. Following customerID will be created in in-mem H2 database as configured in data.sql. Hence it is advised to do testing on this customerID.

100012378<br/>
100012379<br/>
100012380

#### 2. POST REST API To Open Customer Account

Rest End Point : http://localhost:8080/app/v1/accounts

This rest api will open customer current account for existing customers and create transaction if initial amount is not zero. It will fetch business errors in following cases.
1. If customer not found.
2. If customer has already existing account.
3. Request body validation will fetch Bad Request.
4. Application other exceptions will fetch internal server error.

#### SampleRequestMessage for create account REST API

{
"customerID": "100012378",
"initialCredit": "500"
}

#### SampleResponseMessage
{
"result": "Success",
"customerID": 100012378,
"initialCredit": 500,
"accountNumber": "NL26BANK0100012378"
}

#### 3. GET REST API To Fetch Customer Information & Transactions

This rest api will fetch customer information like name,surnmae,balance and all transactions.  It will fetch business errors in following cases.
1. If customer not found.
2. Customer not linked with given account
3. Request body validation will fetch Bad Request.
4. Application other exceptions will fetch internal server error.

#### SampleRequestMessage

REST endpoint is with query parameters (customerid & iban). customerid is mandatory but iban is optional.

Example 1: http://localhost:8080/app/v1/accounts/transactions?customerid=100012378

Example 2: http://localhost:8080/app/v1/accounts/transactions?customerid=100012378&iban=NL26BANK0100012378

#### SampleResponseMessage
{
"result": "Success",
"name": "Arjo",
"surname": "Vliet",
"balance": 500.0,
"transactions": [
{
"transactionId": 1,
"customerID": 100012378,
"iban": "NL26BANK0100012378",
"currency": "EUR",
"transactionDate": "2021-07-13 16:33:04",
"txnAmount": 500.00
}
]
}


#### 4. POST REST API To Create Transactions

Rest End Point :http://localhost:8080/app/v1/accounts/transactions

This rest api will create transaction for the customer.  It will fetch business errors in following cases.
1. If customer not found.
2. Customer not linked with given account.
3. If transaction amount is zero.
3. Request body validation will fetch Bad Request.
4. Application other exceptions will fetch internal server error.

#### SampleRequestMessage

{
"customerID": "100012378",
"iban": "NL26BANK0100012378",
"currency": "EUR",
"txnAmount": 400.05
}

#### SampleResponseMessage

{
"result": "Success",
"transactionId": 3,
"customerId": 100012378,
"iban": "NL26BANK0100012378"
}

#### CI/CD Pipeline

CI/CD pipline has been configured on Azure Devops. This will be part of interview discussion.

### Technology Stack
* Spring Boot
* Java 8
* Maven
* Junit/Mockito
* Lombok
* slf4j
* H2 Database

### Tools for CI/CD
* Azure Devops
* Oracle VM Virtual Box
* putty

### Project Setup
#### 1. Clone the below repository
[GitHub Repository](https://github.com/pintu068/manage-customers-account.git)

#### 2. Go to project folder and run the below maven commands to clean and run the application
` cd manage-customers-account`

` mvn clean`

` mvn spring-boot:run`

##### Alternative
`Import it as maven project on your favourite IDE`

`Run it using IDE or maven`


#### 3. Endpoints to access the REST services
`Create Customer Account`     POST http://localhost:8080/app/v1/accounts

`Create Customer Information` GET http://localhost:8080/app/v1/accounts/transactions?customerid=<"CustomerId of customer">&iban=<"Account number of customer">

`Create Transactions`         POST http://localhost:8080/app/v1/accounts/transactions


#### 4. Test Execution
* `JUnit test` : Covering all test cases for service , controller and dao layers

##### Step to run test cases
`mvn clean test`

#### 5. Design Pattern
1. `Chain Of Responsibility` design pattern to implement multiple validations
2. `Factory` design pattern for creating the objects of different validators on demand

#### 6. Performance
1. Selection of Collection Classes to achive O(1) time complexity
2. Parallel Stream to perform to parallel processing on statement records

#### 7. Scope for improvement
1. This project can be fit in microservices architecture.
2. Secure service endpoint by implementing security and rate limit.
