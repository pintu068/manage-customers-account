package com.assignment.manageaccounts.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ErrorRecord model class to return error in REST API
 */

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class ErrorRecord {

    private long errorId;
    private String errorMessage;


}
