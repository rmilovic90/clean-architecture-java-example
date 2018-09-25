package com.softwarecrafts.office365.usersmanagement.customers;

import com.softwarecrafts.office365.usersmanagement.common.ValueObject;

import java.util.Objects;

public final class CustomerNumber extends ValueObject<CustomerNumber> {
	private final String value;

	public CustomerNumber(String value) {
		if (value == null || value.trim().isEmpty())
			throw new IllegalArgumentException("Customer number's value must not be empty.");

		this.value = value;
	}

	@Override
	protected boolean equalsCore(CustomerNumber otherValueObject) {
		return Objects.equals(value, otherValueObject.value);
	}

	@Override
	protected int hashCodeCore() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return value;
	}
}