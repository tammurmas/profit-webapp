package org.tamm.profit.dao.test;

import java.text.ParseException;
import java.util.Calendar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tamm.profit.model.Customer;
import org.tamm.profit.service.CustomerService;
import org.tamm.profit.service.dto.CustomerDto;

public class CustomerServiceTest {

	@Test
	public void testCreateDtoFromCustomer() throws ParseException {
		Customer customer = createTestCustomer();
		CustomerDto dto = CustomerDto.of(customer);
		Assertions.assertEquals(customer.getFirstname(), dto.getFirstname());
		Assertions.assertEquals(customer.getLastname(), dto.getLastname());
		Assertions.assertEquals(customer.getBirthDate(), CustomerService.DATE_FORMAT.parse(dto.getBirthDate()));
		Assertions.assertEquals(customer.getUsername(), dto.getUsername());
		Assertions.assertEquals(customer.getPassword(), dto.getPassword());
	}

	private Customer createTestCustomer() {
		Customer customer = new Customer();
		customer.setFirstname("John");
		customer.setLastname("Foo");
		customer.setUsername("foojohn");
		customer.setPassword("boo");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		customer.setBirthDate(calendar.getTime());
		return customer;
	}
}
