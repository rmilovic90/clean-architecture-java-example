package com.softwarecrafts.office365.usersmanagement.subscriptions;

import com.softwarecrafts.office365.usersmanagement.common.ValueObject;

import java.util.Objects;

public class SubscriptionCspId extends ValueObject<SubscriptionCspId> {
	private final String value;

	public SubscriptionCspId(String value) {
		if (value == null || value.trim().isEmpty())
			throw new IllegalArgumentException("Subscription CSP ID's value must not be empty.");

		this.value = value;
	}

	@Override
	protected boolean equalsCore(SubscriptionCspId otherValueObject) {
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