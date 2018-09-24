package com.softwarecrafts.office365.usersmanagement.users;

import java.util.Objects;

public final class UserName {
	private final String value;

	public UserName(String value) {
		if (value.trim().isEmpty())
			throw new IllegalArgumentException("User name's value must not be empty.");

		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserName that = (UserName) o;
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
