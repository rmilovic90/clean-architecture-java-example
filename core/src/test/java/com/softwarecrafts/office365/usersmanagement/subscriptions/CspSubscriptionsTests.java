package com.softwarecrafts.office365.usersmanagement.subscriptions;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.softwarecrafts.office365.usersmanagement.common.CspSubscriptionBuilder.aCspSubscription;
import static com.softwarecrafts.office365.usersmanagement.common.CspSubscriptionsBuilder.cspSubscriptionsOf;
import static com.softwarecrafts.office365.usersmanagement.common.SubscriptionCspIdBuilder.aSubscriptionCspIdOf;
import static com.softwarecrafts.office365.usersmanagement.common.SubscriptionCspIdBuilder.subscriptionCspIdsOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Tag("Unit-Test")
class CspSubscriptionsTests {
	@Test
	void cannotBeCreatedWithoutId() {
		var thrown = catchThrowable(() -> new CspSubscriptions(null));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void filtersCspSubscriptionsWithGivenIdsOnly() {
		var cspSubscriptions = cspSubscriptionsOf(
			aCspSubscription()
				.withId("96bc56af-d144-450c-9d84-93cd4693811b"),
			aCspSubscription()
				.withId("e9be0d5c-e4e2-4446-acf6-d6bf9b81672a"),
			aCspSubscription()
				.withId("5076c442-1c00-4026-b8ad-b89e6651b0d6"));

		var cspSubscriptionsFilteredById = cspSubscriptions.onlyWithIdsOf(
			subscriptionCspIdsOf("96bc56af-d144-450c-9d84-93cd4693811b", "e9be0d5c-e4e2-4446-acf6-d6bf9b81672a"));

		assertThat(cspSubscriptionsFilteredById.items()).extracting(CspSubscription::id)
			.containsOnly(aSubscriptionCspIdOf("96bc56af-d144-450c-9d84-93cd4693811b"),
				aSubscriptionCspIdOf("e9be0d5c-e4e2-4446-acf6-d6bf9b81672a"));
	}
}