package com.softwarecrafts.office365.usersmanagement.common;

import com.softwarecrafts.office365.usersmanagement.subscriptions.SubscriptionLicenseQuantity;

public final class SubscriptionLicenseQuantityBuilder {
	public static SubscriptionLicenseQuantity aSubscriptionLicenseQuantityOf(int value) {
		return new SubscriptionLicenseQuantity(value);
	}
}