package com.assignment.manageaccounts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;



/**
 * TransactionRequest class to accept request
 * for transaction creation REST API
 */

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Valid
public class TransactionRequest {
    @NotNull
    private Long customerID;
    @NotNull
    private String iban;
    @NotNull
    private String currency;
    @NotNull
    private BigDecimal txnAmount;
}
