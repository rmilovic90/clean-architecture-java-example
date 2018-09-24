package com.softwarecrafts.office365.usersmanagement.customers;

import java.util.Objects;

public final class CustomerNumber {
	private final String value;

	public CustomerNumber(String value) {
		if (value.trim().isEmpty())
			throw new IllegalArgumentException("Customer number's value must not be empty.");

		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CustomerNumber that = (CustomerNumber) o;
		return value.equalsIgnoreCase(that.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value.toLowerCase());
	}

	@Override
	public String toString() {
		return value;
	}
}
