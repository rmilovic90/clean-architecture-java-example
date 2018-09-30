package com.softwarecrafts.office365.usersmanagement.subscriptions;

import com.softwarecrafts.office365.usersmanagement.common.ValueObject;

import java.util.Objects;

public class SubscriptionLicenseQuantity extends ValueObject<SubscriptionLicenseQuantity> {
	private final int value;

	public SubscriptionLicenseQuantity(int value) {
		if (value < 1)
			throw new IllegalArgumentException("Subscription license quantity value can't be less than 1.");

		this.value = value;
	}

	@Override
	protected boolean equalsCore(SubscriptionLicenseQuantity otherValueObject) {
		return Objects.equals(value, otherValueObject.value);
	}

	@Override
	protected int hashCodeCore() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}