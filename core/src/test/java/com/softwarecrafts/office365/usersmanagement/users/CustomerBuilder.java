package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.Customer;
import com.softwarecrafts.office365.usersmanagement.customers.CustomerCspId;
import com.softwarecrafts.office365.usersmanagement.customers.CustomerLicensingMode;
import com.softwarecrafts.office365.usersmanagement.customers.CustomerNumber;

final class CustomerBuilder {
	static CustomerBuilder aCustomer() {
		return new CustomerBuilder();
	}

	private CustomerNumber number = new CustomerNumber("1234");
	private CustomerCspId cspId = new CustomerCspId("c885965f-9654-4aa9-9aef-5237e43f9a17");
	private CustomerLicensingMode licensingMode = CustomerLicensingMode.AUTOMATIC;

	private CustomerBuilder() {}

	CustomerBuilder withCspId(String cspId) {
		this.cspId = new CustomerCspId(cspId);

		return this;
	}

	CustomerBuilder withLicensingMode(CustomerLicensingMode licensingMode) {
		this.licensingMode = licensingMode;

		return this;
	}

	Customer build() {
		return new Customer(number, cspId, licensingMode);
	}
}