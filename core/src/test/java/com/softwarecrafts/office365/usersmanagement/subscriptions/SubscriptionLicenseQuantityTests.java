package com.softwarecrafts.office365.usersmanagement.subscriptions;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Tag("Unit-Test")
class SubscriptionLicenseQuantityTests {
	@Test
	void cannotHaveValueLessThanOne() {
		var thrown = catchThrowable(() -> new SubscriptionLicenseQuantity(0));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void isEqualToOtherSubscriptionLicenseQuantityWithTheSameValue() {
		var firstSubscriptionLicenseQuantity = new SubscriptionLicenseQuantity(1);
		var secondSubscriptionLicenseQuantity = new SubscriptionLicenseQuantity(1);

		assertThat(firstSubscriptionLicenseQuantity).isEqualTo(secondSubscriptionLicenseQuantity);
	}

	@Test
	void hasHashCodeEqualToOtherSubscriptionLicenseQuantitysHashCodeWithTheSameValue() {
		var firstSubscriptionLicenseQuantitysHashCode = new SubscriptionLicenseQuantity(1).hashCode();
		var secondSubscriptionLicenseQuantitysHashCode = new SubscriptionLicenseQuantity(1).hashCode();

		assertThat(firstSubscriptionLicenseQuantitysHashCode).isEqualTo(secondSubscriptionLicenseQuantitysHashCode);
	}

	@Test
	void isNotEqualToOtherSubscriptionLicenseQuantityWithDifferentValue() {
		var firstSubscriptionLicenseQuantity = new SubscriptionLicenseQuantity(1);
		var secondSubscriptionLicenseQuantity = new SubscriptionLicenseQuantity(2);

		assertThat(firstSubscriptionLicenseQuantity).isNotEqualTo(secondSubscriptionLicenseQuantity);
	}

	@Test
	void doesNotHaveHashCodeEqualToOtherSubscriptionLicenseQuantitysHashCodeWithDifferentValue() {
		var firstSubscriptionLicenseQuantitysHashCode = new SubscriptionLicenseQuantity(1).hashCode();
		var secondSubscriptionLicenseQuantitysHashCode = new SubscriptionLicenseQuantity(2).hashCode();

		assertThat(firstSubscriptionLicenseQuantitysHashCode).isNotEqualTo(secondSubscriptionLicenseQuantitysHashCode);
	}
}