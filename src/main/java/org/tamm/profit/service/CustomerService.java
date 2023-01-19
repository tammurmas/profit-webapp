package org.tamm.profit.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.tamm.profit.service.dto.CustomerDto;

public interface CustomerService {

    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    List<CustomerDto> findAllCustomers();
    void save(CustomerDto customer);
    void delete(long id);
    Optional<CustomerDto> findById(long id);
    Optional<CustomerDto> findByUsername(String username);
}
