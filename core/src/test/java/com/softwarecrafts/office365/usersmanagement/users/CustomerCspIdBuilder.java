package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.CustomerCspId;

final class CustomerCspIdBuilder {
	static CustomerCspId aCustomerCspIdOf(String value) {
		return new CustomerCspId(value);
	}

	private CustomerCspIdBuilder() {}
}