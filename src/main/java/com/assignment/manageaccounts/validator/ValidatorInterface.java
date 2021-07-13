package com.assignment.manageaccounts.validator;
import com.assignment.manageaccounts.model.ErrorRecord;
import com.assignment.manageaccounts.model.ValidationData;
import java.util.List;


/**
 * Validator Interface
 */

public interface ValidatorInterface {
    List<ErrorRecord> validate(ValidationData validationData);
}
