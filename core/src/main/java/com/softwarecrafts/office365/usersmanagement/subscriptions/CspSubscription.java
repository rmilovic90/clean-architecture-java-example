package com.softwarecrafts.office365.usersmanagement.subscriptions;

public class CspSubscription {
	private final SubscriptionCspId id;
	private final SubscriptionLicenseQuantity numberOfAvailableLicenses;
	private final SubscriptionLicenseQuantity numberOfAssignedLicenses;
	private final SubscriptionLicenseQuantity minAllowedNumberOfAvailableLicenses;
	private final SubscriptionLicenseQuantity maxAllowedNumberOfAvailableLicenses;

	public CspSubscription(SubscriptionCspId id,
		SubscriptionLicenseQuantity numberOfAvailableLicenses, SubscriptionLicenseQuantity numberOfAssignedLicenses,
		SubscriptionLicenseQuantity minAllowedNumberOfAvailableLicenses, SubscriptionLicenseQuantity maxAllowedNumberOfAvailableLicenses) {
		if (id == null)
			throw new IllegalArgumentException("CSP subscriptions's id can't be null.");
		if (numberOfAvailableLicenses == null)
			throw new IllegalArgumentException("CSP subscriptions's number Of available licenses can't be null.");
		if (numberOfAssignedLicenses == null)
			throw new IllegalArgumentException("CSP subscriptions's number Of assigned licenses can't be null.");
		if (minAllowedNumberOfAvailableLicenses == null)
			throw new IllegalArgumentException("CSP subscriptions's minimum allowed number Of available licenses can't be null.");
		if (maxAllowedNumberOfAvailableLicenses == null)
			throw new IllegalArgumentException("CSP subscriptions's maximum allowed number Of available licenses can't be null.");

		this.id = id;
		this.numberOfAvailableLicenses = numberOfAvailableLicenses;
		this.numberOfAssignedLicenses = numberOfAssignedLicenses;
		this.minAllowedNumberOfAvailableLicenses = minAllowedNumberOfAvailableLicenses;
		this.maxAllowedNumberOfAvailableLicenses = maxAllowedNumberOfAvailableLicenses;
	}

	public SubscriptionCspId id() {
		return id;
	}

	public SubscriptionLicenseQuantity numberOfAvailableLicenses() {
		return numberOfAvailableLicenses;
	}

	public SubscriptionLicenseQuantity numberOfAssignedLicenses() {
		return numberOfAssignedLicenses;
	}

	public SubscriptionLicenseQuantity minAllowedNumberOfAvailableLicenses() {
		return minAllowedNumberOfAvailableLicenses;
	}

	public SubscriptionLicenseQuantity maxAllowedNumberOfAvailableLicenses() {
		return maxAllowedNumberOfAvailableLicenses;
	}

	@Override
	public String toString() {
		return "CspSubscription {" +
			"id=" + id +
			", numberOfAvailableLicenses=" + numberOfAvailableLicenses +
			", numberOfAssignedLicenses=" + numberOfAssignedLicenses +
			", minAllowedNumberOfAvailableLicenses=" + minAllowedNumberOfAvailableLicenses +
			", maxAllowedNumberOfAvailableLicenses=" + maxAllowedNumberOfAvailableLicenses +
			" }";
	}
}