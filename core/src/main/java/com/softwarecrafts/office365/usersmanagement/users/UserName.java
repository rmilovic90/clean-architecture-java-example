package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.common.ValueObject;

import java.util.Objects;

public final class UserName extends ValueObject<UserName> {
	private final String value;

	UserName(String value) {
		if (value == null || value.trim().isEmpty())
			throw new IllegalArgumentException("User name's value must not be empty.");

		this.value = value;
	}

	@Override
	protected boolean equalsCore(UserName otherValueObject) {
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

