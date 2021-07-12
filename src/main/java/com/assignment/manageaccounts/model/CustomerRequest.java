package com.assignment.manageaccounts.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * CustomerRequest to accept request of account creation REST API
 */

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Valid
public class CustomerRequest {
    @NotNull
    private Long customerID;
    @NotNull
    private BigDecimal initialCredit;

}
