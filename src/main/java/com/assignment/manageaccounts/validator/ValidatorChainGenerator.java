package com.assignment.manageaccounts.validator;

import com.assignment.manageaccounts.constants.ValidationChainType;
import com.assignment.manageaccounts.constants.ValidatorType;
import com.assignment.manageaccounts.exception.ManageAccountsException;

public class ValidatorChainGenerator {

    public static Validator getValidatorChain(ValidationChainType validationChainType) {
        Validator validator;
        Validator validatorNext;
        Validator validatorZero;
        if (validationChainType == ValidationChainType.ACCOUNTCREATION) {
            validator = ValidatorFactory.getValidator(ValidatorType.CUSTOMERNOTFOUND);
            validator.setNext(ValidatorFactory.getValidator(ValidatorType.ACCOUNTEXISTS));
            return validator;
        } else if (validationChainType == ValidationChainType.CUSTOMERINFORMATION) {
            validator = ValidatorFactory.getValidator(ValidatorType.CUSTOMERNOTFOUND);
            validatorNext = ValidatorFactory.getValidator(ValidatorType.CUSTOMERACCOUNTLINK);
            validator.setNext(validatorNext);
            return validator;
        } else if (validationChainType == ValidationChainType.TRANSACTIONCREATION) {
            validator = ValidatorFactory.getValidator(ValidatorType.CUSTOMERNOTFOUND);
            validatorNext = ValidatorFactory.getValidator(ValidatorType.ACCOUNTNOTFOUND);
            validatorZero = ValidatorFactory.getValidator(ValidatorType.ZEROAMOUNTCREATION);
            validatorNext.setNext(validatorZero);
            validator.setNext(validatorNext);

            return validator;
        } else
            throw new ManageAccountsException("Not Supported validation chain type");
    }
}
