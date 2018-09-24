package com.softwarecrafts.office365.usersmanagement.customers;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Customer customer = (Customer) o;
		return Objects.equals(number, customer.number);
	}

	@Override
	public int hashCode() {
		return number.hashCode();
	}

	@Override
	public String toString() {
		return "Customer { " +
			"number=" + number +
			", cspId=" + cspId +
			" }";
	}
}