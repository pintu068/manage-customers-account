package com.assignment.manageaccounts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * Validation model class to accept input for validation of all REST APIs
 */

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Valid
public class ValidationData {

    private Long customerID;
    private BigDecimal initialCredit;
    private String iban;

}
