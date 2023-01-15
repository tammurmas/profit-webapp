package org.tamm.profit.dao.test;

import java.sql.SQLException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.tamm.profit.dao.CustomerDAOImpl;
import org.tamm.profit.model.Customer;

public class CustomerDAOImplTest {
	
	private static CustomerDAOImpl dao = CustomerDAOImpl.getDbCon();
	
	@BeforeClass 
	public static void buildUp()
	{
		dao.setTblName();
		try {
			dao.init();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAddCustomer()
	{
		Customer c = createTestCustomer();
		dao.addCustomer(c);
		Customer loadedByName = dao.getCustomerByUsername(c.getUsername());
		Customer loadedById = dao.getCustomerById(loadedByName.getId());
		
		Assert.assertEquals(c.getUsername(), loadedByName.getUsername());
		Assert.assertEquals(c.getUsername(), loadedById.getUsername());
		dao.removeCustomer(loadedByName.getId());
	}
	
	@Test
	public void testRemoveCustomer()
	{
		Customer c = createTestCustomer();
		dao.addCustomer(c);
		Customer loaded = dao.getCustomerByUsername(c.getUsername());
		dao.removeCustomer(loaded.getId());
		loaded = dao.getCustomerByUsername(c.getUsername());
		Assert.assertNull(loaded);
	}
	
	@Test
	public void testEditCustomer()
	{
		Customer c = createTestCustomer();
		dao.addCustomer(c);
		
		//retrieve the same customer from db and update it
		Customer loaded = dao.getCustomerByUsername(c.getUsername());
		loaded.setLastname("Doe");
		dao.updateCustomer(loaded);
		//retrieve the updated customer object from db
		Customer updated = dao.getCustomerById(loaded.getId());
		
		Assert.assertEquals(updated.getLastname(), "Doe");
		dao.removeCustomer(loaded.getId());
	}
	
	
	/**
	 * Helper
	 * @return
	 */
	private Customer createTestCustomer()
	{
		Customer customer = new Customer();
		customer.setFirstname("John");
		customer.setLastname("Foo");
		customer.setUsername("foojohn");
		customer.setPassword("boo");
		customer.setDateOfBirth("2007-03-03");
		return customer;
	}
}
