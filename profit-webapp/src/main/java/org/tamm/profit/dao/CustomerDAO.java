package org.tamm.profit.dao;

import java.util.List;

import org.tamm.profit.model.Customer;

public interface CustomerDAO {
	public void addCustomer(Customer customer);
	public void updateCustomer(Customer customer);
	public List<Customer> listCustomers();
	public Customer getCustomerById(int id);
	public Customer getCustomerByUsername(String username);
	public void removeCustomer(int id);
}
