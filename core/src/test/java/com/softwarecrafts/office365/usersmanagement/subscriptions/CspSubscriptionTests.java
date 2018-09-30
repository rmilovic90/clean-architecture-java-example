package com.softwarecrafts.office365.usersmanagement.subscriptions;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.softwarecrafts.office365.usersmanagement.common.SubscriptionCspIdBuilder.aSubscriptionCspIdOf;
import static com.softwarecrafts.office365.usersmanagement.common.SubscriptionLicenseQuantityBuilder.aSubscriptionLicenseQuantityOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Tag("Unit-Test")
class CspSubscriptionTests {
	@Test
	void cannotBeCreatedWithoutId() {
		var thrown = catchThrowable(() -> new CspSubscription(null, aSubscriptionLicenseQuantityOf(1),
			aSubscriptionLicenseQuantityOf(1), aSubscriptionLicenseQuantityOf(1),
			aSubscriptionLicenseQuantityOf(Integer.MAX_VALUE)));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void cannotBeCreatedWithoutNumberOfAvailableLicenses() {
		var thrown = catchThrowable(() -> new CspSubscription(aSubscriptionCspIdOf("cb1c2347-7811-4c9e-871f-62c18c1cc22d"),
			null, aSubscriptionLicenseQuantityOf(1), aSubscriptionLicenseQuantityOf(1),
			aSubscriptionLicenseQuantityOf(Integer.MAX_VALUE)));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void cannotBeCreatedWithoutNumberOfAssignedLicenses() {
		var thrown = catchThrowable(() -> new CspSubscription(aSubscriptionCspIdOf("cb1c2347-7811-4c9e-871f-62c18c1cc22d"),
			aSubscriptionLicenseQuantityOf(1), null, aSubscriptionLicenseQuantityOf(1),
			aSubscriptionLicenseQuantityOf(Integer.MAX_VALUE)));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void cannotBeCreatedWithoutMinimumAllowedNumberOfAvailableLicenses() {
		var thrown = catchThrowable(() -> new CspSubscription(aSubscriptionCspIdOf("cb1c2347-7811-4c9e-871f-62c18c1cc22d"),
			aSubscriptionLicenseQuantityOf(1), aSubscriptionLicenseQuantityOf(1),
			null, aSubscriptionLicenseQuantityOf(Integer.MAX_VALUE)));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void cannotBeCreatedWithoutMaximumAllowedNumberOfAvailableLicenses() {
		var thrown = catchThrowable(() -> new CspSubscription(aSubscriptionCspIdOf("cb1c2347-7811-4c9e-871f-62c18c1cc22d"),
			aSubscriptionLicenseQuantityOf(1), aSubscriptionLicenseQuantityOf(1),
			aSubscriptionLicenseQuantityOf(1), null));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void createsANewCspSubscriptionWithASpecifiedNumberOfAvailableLicenses() {
		var cspSubscription = new CspSubscription(aSubscriptionCspIdOf("cb1c2347-7811-4c9e-871f-62c18c1cc22d"),
			aSubscriptionLicenseQuantityOf(1), aSubscriptionLicenseQuantityOf(1),
			aSubscriptionLicenseQuantityOf(1), aSubscriptionLicenseQuantityOf(Integer.MAX_VALUE));

		var cspSubscriptionWithANewNumberOfAvailableLicenses = cspSubscription.withAvailableLicensesOf(
			aSubscriptionLicenseQuantityOf(3));

		assertThat(cspSubscriptionWithANewNumberOfAvailableLicenses.numberOfAvailableLicenses())
			.isEqualTo(aSubscriptionLicenseQuantityOf(3));
	}
}