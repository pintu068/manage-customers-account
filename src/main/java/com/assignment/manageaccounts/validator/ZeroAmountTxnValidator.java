package com.assignment.manageaccounts.validator;
import com.assignment.manageaccounts.constants.BusinessErrors;
import com.assignment.manageaccounts.constants.ValidatorType;
import com.assignment.manageaccounts.model.ErrorRecord;
import com.assignment.manageaccounts.model.ValidationData;
import java.math.BigDecimal;
import java.util.List;


/**
 * Function to check validation of transaction amount if its greater than zero
 */

public class ZeroAmountTxnValidator extends Validator {

    protected ZeroAmountTxnValidator() {
        super(ValidatorType.ZEROAMOUNTCREATION);
    }

    @Override
    public List<ErrorRecord> handle(ValidationData validationData) {

        if (validationData.getInitialCredit().compareTo(BigDecimal.ZERO) == 0)
            result.add(new ErrorRecord(BusinessErrors.TRANSACTION_FOR_ZERO_AMOUNT_NOT_POSSIBLE.getErrorCode(), BusinessErrors.TRANSACTION_FOR_ZERO_AMOUNT_NOT_POSSIBLE.getErrorMessage()));
        return result;
    }
}
