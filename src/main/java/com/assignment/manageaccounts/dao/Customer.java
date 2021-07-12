package com.assignment.manageaccounts.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.Valid;


/**
 * Customer is persistent entity to store customer data created by REST APIs
 */

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Valid
@Entity
public class Customer {

    @Id
    private Long customerId;
    private String name;
    private String surname;

}
