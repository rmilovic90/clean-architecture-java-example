package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.CustomerNumber;

class DeleteUserRequest {
	private final CustomerNumber customerNumber;
	private final UserName customersUserName;

	DeleteUserRequest(CustomerNumber customerNumber, UserName customersUserName) {
		if (customerNumber == null)
			throw new IllegalArgumentException("Customer's number can't be null.");
		if (customersUserName == null)
			throw new IllegalArgumentException("Customer's user name can't be null.");

		this.customerNumber = customerNumber;
		this.customersUserName = customersUserName;
	}

	CustomerNumber customerNumber() {
		return customerNumber;
	}

	UserName customersUserName() {
		return customersUserName;
	}
}
