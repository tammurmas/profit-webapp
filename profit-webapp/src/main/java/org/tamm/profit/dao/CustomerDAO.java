package org.tamm.profit.dao;

import java.util.List;

import org.tamm.profit.model.Customer;

public interface CustomerDAO {
	void addCustomer(Customer customer);
	void updateCustomer(Customer customer);
	List<Customer> listCustomers();
	Customer getCustomerById(int id);
	Customer getCustomerByUsername(String username);
	void removeCustomer(int id);
}
