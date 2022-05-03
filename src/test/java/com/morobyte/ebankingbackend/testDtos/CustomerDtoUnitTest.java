package com.morobyte.ebankingbackend.testDtos;


import com.morobyte.ebankingbackend.dto.CustomerDto;
import com.morobyte.ebankingbackend.entity.Customer;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerDtoUnitTest {
    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void whenConvertCustomerEntityToCustomerDto_thenCorrect() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Salah");
        customer.setEmail("salah@gmail.com");
        customer.setCity("Cairo");
        customer.setCountry("Egypt");
        CustomerDto customerDto = modelMapper.map(customer, CustomerDto.class);
        assertEquals(customer.getId(), customerDto.getId());
        assertEquals(customer.getName(), customerDto.getName());
        assertEquals(customer.getEmail(), customerDto.getEmail());
    }
    @Test
    public void whenConvertCustomerDtoToCustomerEntity_thenCorrect(){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(1L);
        customerDto.setName("Salah");
        customerDto.setEmail("salah@gmail.com");
        customerDto.setCity("Cairo");
        customerDto.setCountry("Egypt");
        Customer customer = modelMapper.map(customerDto, Customer.class);
        assertEquals(customer.getId(), customerDto.getId());
        assertEquals(customer.getName(), customerDto.getName());
        assertEquals(customer.getEmail(), customerDto.getEmail());
    }
}
