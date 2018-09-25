package com.softwarecrafts.office365.usersmanagement.customers;

import com.softwarecrafts.office365.usersmanagement.common.ValueObject;

import java.util.Objects;

public final class CustomerCspId extends ValueObject<CustomerCspId> {
	private final String value;

	public CustomerCspId(String value) {
		if (value == null || value.trim().isEmpty())
			throw new IllegalArgumentException("Customer CSP ID's value must not be empty.");

		this.value = value;
	}

	@Override
	protected boolean equalsCore(CustomerCspId otherValueObject) {
		return value.equalsIgnoreCase(otherValueObject.value);
	}

	@Override
	protected int hashCodeCore() {
		return Objects.hash(value.toLowerCase());
	}

	@Override
	public String toString() {
		return value;
	}
}