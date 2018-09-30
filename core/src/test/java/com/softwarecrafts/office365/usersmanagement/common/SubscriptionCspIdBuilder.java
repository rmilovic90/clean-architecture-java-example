package com.softwarecrafts.office365.usersmanagement.common;

import com.softwarecrafts.office365.usersmanagement.subscriptions.SubscriptionCspId;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class SubscriptionCspIdBuilder {
	public static List<SubscriptionCspId> subscriptionCspIdsOf(String... values) {
		return Arrays.stream(values).map(SubscriptionCspId::new)
			.collect(Collectors.toList());
	}

	public static SubscriptionCspId aSubscriptionCspIdOf(String value) {
		return new SubscriptionCspId(value);
	}
}