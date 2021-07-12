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

public class AccountPresentValidator extends Validator {
    ManageAccountsRepository manageAccountsRepository;

    protected AccountPresentValidator() {
        super(ValidatorType.ACCOUNTNOTFOUND);
        manageAccountsRepository = BeanUtil.getBean(ManageAccountsRepository.class);
    }

    @Override
    public List<ErrorRecord> handle(ValidationData validationData) {
        List<Account> account= manageAccountsRepository.findByCustomerIdAndIban(validationData.getCustomerID(),validationData.getIban());

        if(CollectionUtils.isEmpty(account))
            result.add(new ErrorRecord(BusinessErrors.CUSTOMER_ACCOUNT_NOT_FOUND.getErrorCode(), BusinessErrors.CUSTOMER_ACCOUNT_NOT_FOUND.getErrorMessage()));
        return result;
    }
}
