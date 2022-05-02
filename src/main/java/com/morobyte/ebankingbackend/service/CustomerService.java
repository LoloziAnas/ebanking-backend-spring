package com.morobyte.ebankingbackend.service;

import com.morobyte.ebankingbackend.entity.Customer;
import com.morobyte.ebankingbackend.exception.ResourceNotFoundException;
import com.morobyte.ebankingbackend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CustomerService implements ICustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(long id) throws ResourceNotFoundException {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new ResourceNotFoundException("Customer not found with ID" + id);
        }
    }

    @Override
    public Customer createCustomer(Customer customer) throws ResourceNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findCustomerByEmail(customer.getEmail());
        if (optionalCustomer.isPresent()){
            throw new ResourceNotFoundException("This email is already taken");
        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer, long id) throws ResourceNotFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer updatedCustomer = optionalCustomer.get();
            updatedCustomer.setName(customer.getName());
            updatedCustomer.setEmail(customer.getEmail());
            return customerRepository.save(updatedCustomer);
        } else {
            throw new ResourceNotFoundException("Customer not found with ID" + id);
        }
    }

    @Override
    public void deleteCustomer(long id) throws ResourceNotFoundException {
        boolean exists = customerRepository.existsById(id);
        if (!exists) {
            throw new ResourceNotFoundException("Customer ID: " + id + " doesn't exists");
        }
        customerRepository.deleteById(id);
    }
}
