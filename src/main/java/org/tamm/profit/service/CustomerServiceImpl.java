package org.tamm.profit.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tamm.profit.model.Customer;
import org.tamm.profit.repository.CustomerRepository;
import org.tamm.profit.service.dto.CustomerDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public List<CustomerDto> findAllCustomers() {
        List<CustomerDto> dtos = new ArrayList<>();
        Iterable<Customer> iterable = customerRepository.findAll();
        iterable.forEach(customer -> dtos.add(CustomerDto.of(customer)));
        return dtos;
    }

    @Override
    public void save(CustomerDto dto) {
        Customer customer;
        if (dto.getId() == null) {
            customer = new Customer();
        } else {
            customer = customerRepository.findById(dto.getId()).orElseThrow(() -> new IllegalArgumentException("Customer not found=" + dto.getId()));
        }
        try {
            customer.setId(dto.getId());
            customer.setFirstname(dto.getFirstname());
            customer.setLastname(dto.getLastname());
            customer.setUsername(dto.getUsername());
            customer.setBirthDate(CustomerService.DATE_FORMAT.parse(dto.getBirthDate()));
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            customer.setPassword(encoder.encode(dto.getPassword()));
            customerRepository.save(customer);
        } catch (ParseException e) {
            throw new RuntimeException("Failed to save customer=[" + customer.getId() + "], invalid date value=[" + dto.getBirthDate() + "]", e);
        }
    }

    @Override
    public void delete(long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Optional<CustomerDto> findById(long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return getCustomerDto(optionalCustomer);
    }

    @Override
    public Optional<CustomerDto> findByUsername(String username) {
        Optional<Customer> optionalCustomer = customerRepository.findOneByUsername(username);
        return getCustomerDto(optionalCustomer);
    }

    private static Optional<CustomerDto> getCustomerDto(Optional<Customer> optionalCustomer) {
        Optional<CustomerDto> optionalDto = Optional.empty();
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            CustomerDto dto = CustomerDto.of(customer);
            optionalDto = Optional.of(dto);
        }
        return optionalDto;
    }

}
