package com.assignment.manageaccounts.validator;

import com.assignment.manageaccounts.constants.ValidatorType;
import com.assignment.manageaccounts.model.CustomerRequest;
import com.assignment.manageaccounts.model.ErrorRecord;
import com.assignment.manageaccounts.model.ValidationData;
import com.assignment.manageaccounts.repository.ManageCustomerRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class Validator implements ValidatorInterface{

    ManageCustomerRepository manageCustomerRepository;



    private Validator nextValidator;
    protected List<ErrorRecord> result;
    private final ValidatorType type;


    protected Validator(ValidatorType type) {
        this.type = type;
        nextValidator = null;
        result = new ArrayList<>();
    }

    @Override
    public List<ErrorRecord> validate(ValidationData validationData) {
        List<ErrorRecord> errorRecords = this.handle(validationData);
        if (nextValidator != null)
            nextValidator.validate(validationData)
                    .forEach((value) -> errorRecords.add( value));
        return errorRecords;
    }

    public void setNext(Validator nextValidator) {
        this.nextValidator = nextValidator;
    }

    protected ValidatorType getType() {
        return this.type;
    }

    public abstract List<ErrorRecord> handle(ValidationData validationData);


}
