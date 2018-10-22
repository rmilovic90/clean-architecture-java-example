package com.softwarecrafts.office365.usersmanagement.customers;

public class CustomerNotFoundException extends RuntimeException {
	private final CustomerNumber number;

	public CustomerNotFoundException(CustomerNumber number) {
		this.number = number;
	}

	@Override
	public String getMessage() {
		return String.format("Customer with number %s does not exist.", number);
	}
}