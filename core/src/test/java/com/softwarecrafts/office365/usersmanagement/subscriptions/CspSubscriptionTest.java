package com.softwarecrafts.office365.usersmanagement.subscriptions;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Tag("Unit-Test")
class CspSubscriptionTest {
	@Test
	void cannotBeCreatedWithoutId() {
		var thrown = catchThrowable(() -> new CspSubscription(null, new SubscriptionLicenseQuantity(1),
			new SubscriptionLicenseQuantity(1), new SubscriptionLicenseQuantity(1),
			new SubscriptionLicenseQuantity(Integer.MAX_VALUE)));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void cannotBeCreatedWithoutNumberOfAvailableLicenses() {
		var thrown = catchThrowable(() -> new CspSubscription(new SubscriptionCspId("cb1c2347-7811-4c9e-871f-62c18c1cc22d"),
			null, new SubscriptionLicenseQuantity(1), new SubscriptionLicenseQuantity(1),
			new SubscriptionLicenseQuantity(Integer.MAX_VALUE)));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void cannotBeCreatedWithoutNumberOfAssignedLicenses() {
		var thrown = catchThrowable(() -> new CspSubscription(new SubscriptionCspId("cb1c2347-7811-4c9e-871f-62c18c1cc22d"),
			new SubscriptionLicenseQuantity(1), null, new SubscriptionLicenseQuantity(1),
			new SubscriptionLicenseQuantity(Integer.MAX_VALUE)));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void cannotBeCreatedWithoutMinimumAllowedNumberOfAvailableLicenses() {
		var thrown = catchThrowable(() -> new CspSubscription(new SubscriptionCspId("cb1c2347-7811-4c9e-871f-62c18c1cc22d"),
			new SubscriptionLicenseQuantity(1), new SubscriptionLicenseQuantity(1),
			null, new SubscriptionLicenseQuantity(Integer.MAX_VALUE)));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void cannotBeCreatedWithoutMaximumAllowedNumberOfAvailableLicenses() {
		var thrown = catchThrowable(() -> new CspSubscription(new SubscriptionCspId("cb1c2347-7811-4c9e-871f-62c18c1cc22d"),
			new SubscriptionLicenseQuantity(1), new SubscriptionLicenseQuantity(1),
			new SubscriptionLicenseQuantity(Integer.MAX_VALUE), null));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
}