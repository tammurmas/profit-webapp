package org.tamm.profit.repository;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.tamm.profit.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Optional<Customer> findOneByUsername(String username);
}
