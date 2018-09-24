package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.CustomerNumber;

public class DeleteUserRequest {
	private final CustomerNumber customerNumber;
	private final UserName customersUserName;

	public DeleteUserRequest(CustomerNumber customerNumber, UserName customersUserName) {
		if (customerNumber == null)
			throw new IllegalArgumentException("Customer's number can't be null.");
		if (customersUserName == null)
			throw new IllegalArgumentException("Customer's user name can't be null.");

		this.customerNumber = customerNumber;
		this.customersUserName = customersUserName;
	}
}
