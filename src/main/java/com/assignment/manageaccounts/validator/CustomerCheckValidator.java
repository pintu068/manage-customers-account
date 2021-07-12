package com.assignment.manageaccounts.validator;

import com.assignment.manageaccounts.constants.BusinessErrors;
import com.assignment.manageaccounts.constants.ValidatorType;
import com.assignment.manageaccounts.dao.Customer;
import com.assignment.manageaccounts.model.CustomerRequest;
import com.assignment.manageaccounts.model.ErrorRecord;
import com.assignment.manageaccounts.model.ValidationData;
import com.assignment.manageaccounts.repository.ManageCustomerRepository;
import com.assignment.manageaccounts.services.BeanUtil;

import java.util.List;
import java.util.Optional;

public class CustomerCheckValidator extends Validator{

    protected CustomerCheckValidator() {
        super( ValidatorType.CUSTOMERNOTFOUND);
        manageCustomerRepository = BeanUtil.getBean(ManageCustomerRepository.class);


    }

    @Override
    public List<ErrorRecord> handle(ValidationData validationData) {

      Optional<Customer> optionalCustomer= manageCustomerRepository.findById(validationData.getCustomerID());

      if(!optionalCustomer.isPresent())
          result.add(new ErrorRecord(BusinessErrors.CUSTOMER_NOT_FOUND.getErrorCode(), BusinessErrors.CUSTOMER_NOT_FOUND.getErrorMessage()));
        return result;
    }


}

