# Manage Customers Account
Manage customer accounts and transactions
## Assignment

The Assessment consist of an API to be used for opening a new "current account" of already existing customers.

The API will expose an endpoint which accepts the user information(customerID, initialCredit).
Once the endpoint is called, a new account will be opened connected to the user whose id is customerID.
Also, if initialCredit is not 0, a transaction will be sent to the new account.
Another Endpoint will output the user information showing Name,Surname,balance and transactions of the accounts.



## REST APIs and Functionalities
#### 1. REST API To Open Customer Account

This rest api will open customer current account for existing customers. It will fetch business errors in following cases.
1.


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
#### 1. Clone the repository
[GitHub Repository](https://github.com/pintu068/manage-customers-account.git)

#### 2. Go to project folder and run the below maven commands to clean and run the application
` cd manage-customers-account`

` mvn clean`

` mvn spring-boot:run`

##### Alternative
`Import it as maven project on your favourite IDE`

`Run it using IDE or maven`


#### 3. Endpoints to access the REST services 
http://localhost:8080/app/v1/accounts










