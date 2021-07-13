package com.assignment.manageaccounts.validator;
import com.assignment.manageaccounts.constants.BusinessErrors;
import com.assignment.manageaccounts.constants.ValidatorType;
import com.assignment.manageaccounts.dao.Account;
import com.assignment.manageaccounts.model.ErrorRecord;
import com.assignment.manageaccounts.model.ValidationData;
import com.assignment.manageaccounts.repository.ManageAccountsRepository;
import com.assignment.manageaccounts.services.BeanUtil;
import org.springframework.util.CollectionUtils;
import java.util.List;


/**
 * Class for Account to check if account and customer linking is correct or not
 * If current account not linked with customer fetch business error
 */

public class CustomerAccountLinkValidator extends Validator {

    ManageAccountsRepository manageAccountsRepository;

    protected CustomerAccountLinkValidator() {
        super(ValidatorType.CUSTOMERACCOUNTLINK);
        manageAccountsRepository = BeanUtil.getBean(ManageAccountsRepository.class);
    }

    @Override
    public List<ErrorRecord> handle(ValidationData validationData) {
        List<Account> account = null;
        if (validationData.getIban() != null) {
            account = manageAccountsRepository.findByCustomerIdAndIban(validationData.getCustomerID(), validationData.getIban());
        }
        if (CollectionUtils.isEmpty(account) && validationData.getIban() != null)
            result.add(new ErrorRecord(BusinessErrors.CUSTOMER_ACCOUNT_IS_NOT_LINKED.getErrorCode(), BusinessErrors.CUSTOMER_ACCOUNT_IS_NOT_LINKED.getErrorMessage()));
        return result;
    }
}
