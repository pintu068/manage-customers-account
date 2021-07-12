package com.assignment.manageaccounts.validator;

import com.assignment.manageaccounts.constants.BusinessErrors;
import com.assignment.manageaccounts.constants.ValidatorType;
import com.assignment.manageaccounts.dao.Account;
import com.assignment.manageaccounts.model.CustomerRequest;
import com.assignment.manageaccounts.model.ErrorRecord;
import com.assignment.manageaccounts.model.ValidationData;
import com.assignment.manageaccounts.repository.ManageAccountsRepository;
import com.assignment.manageaccounts.services.BeanUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class AccountCheckValidator extends Validator {

    ManageAccountsRepository manageAccountsRepository;

    protected AccountCheckValidator() {
        super(ValidatorType.ACCOUNTEXISTS);
        manageAccountsRepository = BeanUtil.getBean(ManageAccountsRepository.class);
    }

    @Override
    public List<ErrorRecord> handle(ValidationData validationData) {
       List<Account> account= manageAccountsRepository.findByCustomerId(validationData.getCustomerID());

           if(!CollectionUtils.isEmpty(account))
           result.add(new ErrorRecord(BusinessErrors.CURRENT_ACCOUNT_ALREADY_EXISTS.getErrorCode(), BusinessErrors.CURRENT_ACCOUNT_ALREADY_EXISTS.getErrorMessage()));
        return result;
    }
}
