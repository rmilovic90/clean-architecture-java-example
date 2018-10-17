package com.softwarecrafts.office365.usersmanagement.customers;

public class Customer {
	private final CustomerNumber number;
	private final CustomerCspId cspId;
	private final CustomerLicensingMode licensingMode;

	public Customer(CustomerNumber number, CustomerCspId cspId, CustomerLicensingMode licensingMode) {
		if (number == null)
			throw new IllegalArgumentException("Customer's number can't be null.");
		if (cspId == null)
			throw new IllegalArgumentException("Customer's CSP ID can't be null.");
		if (licensingMode == null)
			throw new IllegalArgumentException("Customer's licensing mode can't be null.");

		this.number = number;
		this.cspId = cspId;
		this.licensingMode = licensingMode;
	}

	public CustomerCspId cspId() {
		return cspId;
	}

	public CustomerLicensingMode licensingMode() {
		return  licensingMode;
	}

	@Override
	public String toString() {
		return "Customer {" +
			"number=" + number +
			", cspId=" + cspId +
			", licensingMode=" + licensingMode +
			" }";
	}
}