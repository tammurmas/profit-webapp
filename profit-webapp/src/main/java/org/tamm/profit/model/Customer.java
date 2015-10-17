package org.tamm.profit.model;

import org.hibernate.validator.constraints.NotBlank;

public class Customer {
	private int id;
	
	@NotBlank(message = "Firstname left blank!")
	private String firstname;
	
	@NotBlank(message = "Lastname left blank!")
	private String lastname; 
	
	private String dateOfBirth;
	
	@NotBlank(message = "Username left blank!")
	private String username;
	
	@NotBlank(message = "Password left blank!")
	private String password;
	
	public Customer(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString()
	{
		return firstname+"; "+lastname+"; "+dateOfBirth+"; "+username;
	}
	
}
