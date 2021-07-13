package com.assignment.manageaccounts.constants;


/**
 * Different Technical errors configured
 */

public enum TechnicalErrors {
    BAD_REQUEST(400,"Bad Request"),
    INTERNAL_SERVER_ERROR(500,"Internal Server Error"),
    PARAMETER_MISSING(400,"Parameter is missing");
    private long errorCode;
    private String errorMessage;

    private TechnicalErrors(long errorCode,String errorMessage) {

        this.errorCode = errorCode;
        this.errorMessage =errorMessage;
    }

    public long getErrorCode() {
        return errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
}
