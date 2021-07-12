package com.assignment.manageaccounts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


/**
 * CustomerResponse to fetch response of account creation REST API
 */

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Valid
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    private String result;
    private Long customerID;
    private BigDecimal initialCredit;
    private String accountNumber;
    private List<ErrorRecord> errorRecords;

}
