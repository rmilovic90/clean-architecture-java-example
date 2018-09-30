package com.softwarecrafts.office365.usersmanagement.subscriptions;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CspSubscriptions {
	private final Collection<CspSubscription> items;

	public CspSubscriptions(Collection<CspSubscription> items) {
		if (items == null)
			throw new IllegalArgumentException("CSP subscription items can't be null.");

		this.items = items;
	}

	public List<CspSubscription> items() {
		return new ArrayList<>(items);
	}

	public CspSubscriptions onlyWithIdsOf(Collection<SubscriptionCspId> subscriptionIds) {
		var subscriptionIdsSet = new HashSet<>(subscriptionIds);
		var filteredItems = items.stream()
			.filter(subscription -> subscriptionIdsSet.contains(subscription.id()))
			.collect(Collectors.toList());

		return new CspSubscriptions(filteredItems);
	}

	public CspSubscriptions modifyNumberOfAvailableLicensesUsing(Function<CspSubscription, SubscriptionLicenseQuantity> modifier) {
		var itemsWithModifiedNumberOfAssignedLicenses = items.stream()
			.map(item -> item.withAvailableLicensesOf(modifier.apply(item)))
			.collect(Collectors.toList());

		return new CspSubscriptions(itemsWithModifiedNumberOfAssignedLicenses);
	}
}