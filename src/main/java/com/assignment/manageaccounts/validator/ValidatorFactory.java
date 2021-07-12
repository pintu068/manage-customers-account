package com.assignment.manageaccounts.validator;

import com.assignment.manageaccounts.constants.ValidatorType;
import com.assignment.manageaccounts.exception.ManageAccountsException;

public class ValidatorFactory {

    public static Validator getValidator(ValidatorType type) {
        if (type == ValidatorType.CUSTOMERNOTFOUND)
            return new CustomerCheckValidator();
        else if (type == ValidatorType.ACCOUNTEXISTS)
            return new AccountCheckValidator();
        else if (type == ValidatorType.ACCOUNTNOTFOUND)
            return new AccountPresentValidator();
        else if (type == ValidatorType.CUSTOMERACCOUNTLINK)
            return new CustomerAccountLinkValidator();
        else if (type == ValidatorType.ZEROAMOUNTCREATION)
            return new ZeroAmountTxnValidator();
        throw new ManageAccountsException("Not Supported validation type");
    }
}
