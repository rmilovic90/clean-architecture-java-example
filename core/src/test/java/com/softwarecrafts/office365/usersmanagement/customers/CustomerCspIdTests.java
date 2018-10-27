package com.softwarecrafts.office365.usersmanagement.customers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Tag("Unit-Test")
class CustomerCspIdTests {
	@ParameterizedTest
	@MethodSource("valueProvider")
	void cannotHaveEmptyValue(String value) {
		var thrown = catchThrowable(() -> new CustomerCspId(value));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	private static Stream<String> valueProvider() {
		return Stream.of(null, "", "   ");
	}

	@ParameterizedTest
	@MethodSource("equalValuesProvider")
	void isEqualToOtherCustomerCspIdWithTheSameValue(String firstValue, String secondValue) {
		var firstCustomerCspId = new CustomerCspId(firstValue);
		var secondCustomerCspId = new CustomerCspId(secondValue);

		assertThat(firstCustomerCspId).isEqualTo(secondCustomerCspId);
	}

	@ParameterizedTest
	@MethodSource("equalValuesProvider")
	void hasHashCodeEqualToOtherCustomerCspIdsHashCodeWithTheSameValue(String firstValue, String secondValue) {
		var firstCustomerCspIdsHashCode = new CustomerCspId(firstValue).hashCode();
		var secondCustomerCspIdsHashCode = new CustomerCspId(secondValue).hashCode();

		assertThat(firstCustomerCspIdsHashCode).isEqualTo(secondCustomerCspIdsHashCode);
	}

	private static Stream<Arguments> equalValuesProvider() {
		return Stream.of(
			Arguments.of("2841ea17-1e7e-4084-827e-18876807e689", "2841ea17-1e7e-4084-827e-18876807e689"),
			Arguments.of("2841EA17-1E7E-4084-827E-18876807E689", "2841EA17-1E7E-4084-827E-18876807E689"),
			Arguments.of("2841ea17-1e7e-4084-827e-18876807e689", "2841EA17-1E7E-4084-827E-18876807E689")
		);
	}

	@Test
	void isNotEqualToOtherCustomerCspIdWithDifferentValue() {
		var firstCustomerCspId = new CustomerCspId("2841ea17-1e7e-4084-827e-18876807e689");
		var secondCustomerCspId = new CustomerCspId("6ef80cd6-e637-46aa-8749-c47a9b14e939");

		assertThat(firstCustomerCspId).isNotEqualTo(secondCustomerCspId);
	}

	@Test
	void doesNotHaveHashCodeEqualToOtherCustomerCspIdsHashCodeWithDifferentValue() {
		var firstCustomerCspIdsHashCode = new CustomerCspId("2841ea17-1e7e-4084-827e-18876807e689").hashCode();
		var secondCustomerCspIdsHashCode = new CustomerCspId("6ef80cd6-e637-46aa-8749-c47a9b14e939").hashCode();

		assertThat(firstCustomerCspIdsHashCode).isNotEqualTo(secondCustomerCspIdsHashCode);
	}
}