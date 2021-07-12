package com.assignment.manageaccounts.model;

import com.assignment.manageaccounts.dao.Transaction;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;


/**
 * AccountTransactionResponse to return response of Customer information
 *  and transaction list REST API
 */

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Valid
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountTransactionResponse {
    private String result;
    private String name;
    private String surname;
    private BigDecimal balance;
    private List<Transaction> transactions;
    private List<ErrorRecord> errorRecords;

}
