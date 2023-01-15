package org.tamm.profit.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.tamm.profit.dao.CustomerDAO;
import org.tamm.profit.dao.CustomerDAOImpl;

/**
 * A helper class for validating dates and checking uniqueness of usernames
 * @author Urmas
 *
 */
public class FormValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void validate(Object o, Errors errors) {
		Customer customer = (Customer)o;
		
		if(!(customer.getDateOfBirth()!= null && customer.getDateOfBirth().isEmpty())) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			try
			{
				LocalDate date = LocalDate.parse(customer.getDateOfBirth(), formatter);
				if(date == null)
				{
					errors.rejectValue("dateOfBirth", "Date of birth not valid!", "Date of birth not valid!");
				}
			}
			catch(DateTimeParseException e)
			{
				errors.rejectValue("dateOfBirth", "Date of birth not valid!", "Date of birth not valid!");
			}
		}
		else
		{
			errors.rejectValue("dateOfBirth", "Date of birth left blank!", "Date of birth left blank!");
		}
		
		CustomerDAO dao = CustomerDAOImpl.getDbCon();
		Customer loaded = dao.getCustomerByUsername(customer.getUsername());
		if(loaded != null)
		{
			boolean sameCustomer = (customer.getId() == loaded.getId());
			if(!sameCustomer)
			{
				errors.rejectValue("username", "Username already in use!", "Username already in use!");
			}
		}
	}

}
