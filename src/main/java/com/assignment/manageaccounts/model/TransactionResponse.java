package com.assignment.manageaccounts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.util.List;

/**
 * TransactionResponse class to fetch response
 * for transaction creation REST API
 */

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Valid
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {
    private String result;
    private Long transactionId;
    private Long customerId;
    private String iban;
    private List<ErrorRecord> errorRecords;
}
