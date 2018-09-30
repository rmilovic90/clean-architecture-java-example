package com.softwarecrafts.office365.usersmanagement.common;

import com.softwarecrafts.office365.usersmanagement.subscriptions.CspSubscriptions;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public final class CspSubscriptionsBuilder {
	public static CspSubscriptions emptyCspSubscriptions() {
		return new CspSubscriptions(Collections.emptyList());
	}

	public static CspSubscriptions cspSubscriptionsOf(CspSubscriptionBuilder... cspSubscriptions) {
		return new CspSubscriptions(Arrays.stream(cspSubscriptions).map(CspSubscriptionBuilder::build)
			.collect(Collectors.toList()));
	}
}