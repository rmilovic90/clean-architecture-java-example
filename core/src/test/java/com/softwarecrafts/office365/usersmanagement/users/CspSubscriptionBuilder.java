package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.subscriptions.CspSubscription;
import com.softwarecrafts.office365.usersmanagement.subscriptions.SubscriptionCspId;
import com.softwarecrafts.office365.usersmanagement.subscriptions.SubscriptionLicenseQuantity;

final class CspSubscriptionBuilder {
	static CspSubscriptionBuilder aCspSubscription() {
		return new CspSubscriptionBuilder();
	}

	private SubscriptionCspId id = new SubscriptionCspId("3338777d-3bc6-4eda-82a4-e02fef75ecbd");
	private SubscriptionLicenseQuantity numberOfAvailableLicenses = new SubscriptionLicenseQuantity(1);
	private SubscriptionLicenseQuantity numberOfAssignedLicenses = new SubscriptionLicenseQuantity(1);
	private final SubscriptionLicenseQuantity minAllowedNumberOfAvailableLicenses = new SubscriptionLicenseQuantity(1);
	private final SubscriptionLicenseQuantity maxAllowedNumberOfAvailableLicenses = new SubscriptionLicenseQuantity(Integer.MAX_VALUE);

	private CspSubscriptionBuilder() {}

	CspSubscriptionBuilder withId(String id) {
		this.id = new SubscriptionCspId(id);

		return this;
	}

	CspSubscriptionBuilder withAvailableLicenses(int quantity) {
		numberOfAvailableLicenses = new SubscriptionLicenseQuantity(quantity);

		return this;
	}

	CspSubscriptionBuilder withAssignedLicenses(int quantity) {
		numberOfAssignedLicenses = new SubscriptionLicenseQuantity(quantity);

		return this;
	}

	CspSubscription build() {
		return new CspSubscription(id, numberOfAvailableLicenses, numberOfAssignedLicenses,
			minAllowedNumberOfAvailableLicenses, maxAllowedNumberOfAvailableLicenses);
	}
}