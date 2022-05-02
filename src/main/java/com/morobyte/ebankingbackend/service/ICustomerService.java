package com.morobyte.ebankingbackend.service;

import com.morobyte.ebankingbackend.entity.Customer;
import com.morobyte.ebankingbackend.exception.ResourceNotFoundException;

import java.util.List;

public interface ICustomerService {
    List<Customer> getCustomers();
    Customer getCustomer(long id) throws ResourceNotFoundException;
    Customer createCustomer(Customer customer) throws ResourceNotFoundException;
    Customer updateCustomer(Customer customer, long id) throws ResourceNotFoundException;
    void deleteCustomer(long id) throws ResourceNotFoundException;
}
