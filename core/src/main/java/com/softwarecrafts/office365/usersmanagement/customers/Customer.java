package com.softwarecrafts.office365.usersmanagement.customers;

public class Customer {
	private final CustomerNumber number;
	private final CustomerCspId cspId;

	public Customer(CustomerNumber number, CustomerCspId cspId) {
		if (number == null)
			throw new IllegalArgumentException("Customer's number can't be null.");
		if (cspId == null)
			throw new IllegalArgumentException("Customer's CSP ID can't be null.");

		this.number = number;
		this.cspId = cspId;
	}

	public CustomerCspId cspId() {
		return cspId;
	}

	@Override
	public String toString() {
		return "Customer { " +
			"number=" + number +
			", cspId=" + cspId +
			" }";
	}
}