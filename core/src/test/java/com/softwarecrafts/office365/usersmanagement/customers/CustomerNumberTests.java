package com.softwarecrafts.office365.usersmanagement.customers;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Tag("Unit-Test")
class CustomerNumberTests {
	@ParameterizedTest
	@MethodSource("valueProvider")
	void cannotHaveEmptyValue(String value) {
		var thrown = catchThrowable(() -> new CustomerNumber(value));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	private static Stream<String> valueProvider() {
		return Stream.of(null, "", "   ");
	}

	@Test
	void isEqualToOtherCustomerNumberWithTheSameValue() {
		var firstCustomerNumber = new CustomerNumber("1234");
		var secondCustomerNumber = new CustomerNumber("1234");

		assertThat(firstCustomerNumber).isEqualTo(secondCustomerNumber);
	}

	@Test
	void hasHashCodeEqualToOtherCustomerNumbersHashCodeWithTheSameValue() {
		var firstCustomerNumbersHashCode = new CustomerNumber("1234").hashCode();
		var secondCustomerNumbersHashCode = new CustomerNumber("1234").hashCode();

		assertThat(firstCustomerNumbersHashCode).isEqualTo(secondCustomerNumbersHashCode);
	}

	@Test
	void isNotEqualToOtherCustomerNumberWithDifferentValue() {
		var firstCustomerNumber = new CustomerNumber("1234");
		var secondCustomerNumber = new CustomerNumber("5678");

		assertThat(firstCustomerNumber).isNotEqualTo(secondCustomerNumber);
	}

	@Test
	void doesNotHaveHashCodeEqualToOtherCustomerNumbersHashCodeWithDifferentValue() {
		var firstCustomerNumbersHashCode = new CustomerNumber("1234").hashCode();
		var secondCustomerNumbersHashCode = new CustomerNumber("5678").hashCode();

		assertThat(firstCustomerNumbersHashCode).isNotEqualTo(secondCustomerNumbersHashCode);
	}
}