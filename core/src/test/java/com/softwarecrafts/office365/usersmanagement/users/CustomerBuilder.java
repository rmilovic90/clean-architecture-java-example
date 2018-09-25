package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.Customer;
import com.softwarecrafts.office365.usersmanagement.customers.CustomerCspId;
import com.softwarecrafts.office365.usersmanagement.customers.CustomerNumber;

final class CustomerBuilder {
	static CustomerBuilder aCustomer() {
		return new CustomerBuilder();
	}

	private CustomerNumber number = new CustomerNumber("1234");
	private CustomerCspId cspId = new CustomerCspId("c885965f-9654-4aa9-9aef-5237e43f9a17");

	private CustomerBuilder() {}

	CustomerBuilder withNumber(String number) {
		this.number = new CustomerNumber(number);

		return this;
	}

	CustomerBuilder withCspId(String cspId) {
		this.cspId = new CustomerCspId(cspId);

		return this;
	}

	Customer build() {
		return new Customer(number, cspId);
	}
}