package com.assignment.manageaccounts.constants;

public enum BusinessErrors {

    CUSTOMER_NOT_FOUND(10001, "Customer not found"),
    CURRENT_ACCOUNT_ALREADY_EXISTS(10002, "Current current account already exist"),
    CUSTOMER_ACCOUNT_NOT_FOUND(10003, "Customer account not found"),
    TRANSACTION_FOR_ZERO_AMOUNT_NOT_POSSIBLE(10004, "Transaction for zero amount not possible"),
    CUSTOMER_ACCOUNT_IS_NOT_LINKED(10005, "Customer not linked with given Account");
    private long errorCode;
    private String errorMessage;

    private BusinessErrors(long errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}