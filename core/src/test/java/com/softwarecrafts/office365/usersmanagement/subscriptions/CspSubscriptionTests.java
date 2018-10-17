package com.softwarecrafts.office365.usersmanagement.subscriptions;

import com.softwarecrafts.office365.usersmanagement.common.CspSubscriptionBuilder;
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
	void alignsTheNumberOfAvailableLicensesToTheNumberOfAssignedLicensesWhenItIsWithinTheAllowedRange() {
		var cspSubscription = CspSubscriptionBuilder.aCspSubscription()
			.withAvailableLicenses(3)
			.withAssignedLicenses(2)
			.withMinimumAllowedNumberOfAvailableLicenses(1)
			.withMaximumAllowedNumberOfAvailableLicenses(Integer.MAX_VALUE)
			.build();

		var optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses = cspSubscription.alignNumberOfAvailableLicenses();

		assertThat(optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses).hasValueSatisfying(
			subscription -> assertThat(subscription.numberOfAvailableLicenses())
				.isEqualTo(aSubscriptionLicenseQuantityOf(2)));
	}

	@Test
	void alignsTheNumberOfAvailableLicensesToTheMinimumAllowedNumberOfAssignedLicensesWhenTheNumberOfAssignedLicensesIsBellowThatValue() {
		var cspSubscription = CspSubscriptionBuilder.aCspSubscription()
			.withAvailableLicenses(3)
			.withAssignedLicenses(0)
			.withMinimumAllowedNumberOfAvailableLicenses(1)
			.build();

		var optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses = cspSubscription.alignNumberOfAvailableLicenses();

		assertThat(optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses).hasValueSatisfying(
			subscription -> assertThat(subscription.numberOfAvailableLicenses())
				.isEqualTo(aSubscriptionLicenseQuantityOf(1)));
	}

	@Test
	void alignsTheNumberOfAvailableLicensesToTheMaximumAllowedNumberOfAssignedLicensesWhenTheNumberOfAssignedLicensesIsAboveThatValue() {
		var cspSubscription = CspSubscriptionBuilder.aCspSubscription()
			.withAvailableLicenses(3)
			.withAssignedLicenses(11)
			.withMaximumAllowedNumberOfAvailableLicenses(10)
			.build();

		var optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses = cspSubscription.alignNumberOfAvailableLicenses();

		assertThat(optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses).hasValueSatisfying(
			subscription -> assertThat(subscription.numberOfAvailableLicenses())
				.isEqualTo(aSubscriptionLicenseQuantityOf(10)));
	}

	@Test
	void doesNotAlignTheNumberOfAvailableLicensesWhenItIsAlreadyAlignedWithTheNumberOfAssignedLicenses() {
		var cspSubscription = CspSubscriptionBuilder.aCspSubscription()
			.withAvailableLicenses(3)
			.withAssignedLicenses(3)
			.build();

		var optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses = cspSubscription.alignNumberOfAvailableLicenses();

		assertThat(optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses).isNotPresent();
	}

	@Test
	void doesNotAlignTheNumberOfAvailableLicensesWhenDecreaseIsRequiredAndItAlreadyHasMinimumAllowedValue() {
		var cspSubscription = CspSubscriptionBuilder.aCspSubscription()
			.withAvailableLicenses(1)
			.withAssignedLicenses(0)
			.withMinimumAllowedNumberOfAvailableLicenses(1)
			.build();

		var optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses = cspSubscription.alignNumberOfAvailableLicenses();

		assertThat(optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses).isNotPresent();
	}

	@Test
	void doesNotAlignTheNumberOfAvailableLicensesWhenIncreaseIsRequiredAndItAlreadyHasMaximumAllowedValue() {
		var cspSubscription = CspSubscriptionBuilder.aCspSubscription()
			.withAvailableLicenses(10)
			.withAssignedLicenses(11)
			.withMaximumAllowedNumberOfAvailableLicenses(10)
			.build();

		var optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses = cspSubscription.alignNumberOfAvailableLicenses();

		assertThat(optionallyCspSubscriptionWithAlignedNumberOfAvailableLicenses).isNotPresent();
	}
}