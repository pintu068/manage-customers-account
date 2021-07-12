package com.assignment.manageaccounts.exception;


/**
 * Exception handler class for all REST APIs
 */

public class ManageAccountsException extends RuntimeException{
    public ManageAccountsException(String errorMessage) {
        super(errorMessage);
    }
    public ManageAccountsException(String errorMessage, Throwable throwable) {
        super(errorMessage, throwable);
    }
}
