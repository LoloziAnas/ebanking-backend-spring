package com.morobyte.ebankingbackend.controller;

import com.morobyte.ebankingbackend.dto.CustomerDto;
import com.morobyte.ebankingbackend.entity.Customer;
import com.morobyte.ebankingbackend.exception.ResourceNotFoundException;
import com.morobyte.ebankingbackend.service.ICustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/customers")
public class CustomerController {
    private final ICustomerService customerService;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerController(ICustomerService customerService, ModelMapper modelMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<CustomerDto> getCustomers() {
        return customerService.getCustomers().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody @Valid CustomerDto customerDto) throws ResourceNotFoundException {
        // Convert to Customer Entity
        Customer customer = convertToEntity(customerDto);
        Customer customerCreated = customerService.createCustomer(customer);
        // Convert to Customer Dto to create a response of CustomerDto
        CustomerDto customerResponse = convertToDto(customerCreated);
        return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
    }

    private CustomerDto convertToDto(Customer customer) {
        return modelMapper.map(customer, CustomerDto.class);
    }

    private Customer convertToEntity(CustomerDto customerDto) {
        return modelMapper.map(customerDto, Customer.class);
    }
}
