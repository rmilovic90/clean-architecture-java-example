package com.softwarecrafts.office365.usersmanagement.customers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Tag("Unit-Test")
class CustomerTests {
	@Test
	void cannotBeCreatedWithoutNumber() {
		var thrown = catchThrowable(() -> new Customer(null,
			new CustomerCspId("2f17fde1-0f13-4b73-b4ec-c7b48a9ef1a7"), CustomerLicensingMode.AUTOMATIC));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void cannotBeCreatedWithoutCspId() {
		var thrown = catchThrowable(() -> new Customer(new CustomerNumber("1234"), null,
			CustomerLicensingMode.AUTOMATIC));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void cannotBeCreatedWithoutLicensingMode() {
		var thrown = catchThrowable(() -> new Customer(new CustomerNumber("1234"),
			new CustomerCspId("2f17fde1-0f13-4b73-b4ec-c7b48a9ef1a7"), null));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}
}