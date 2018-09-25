package com.softwarecrafts.office365.usersmanagement.users;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@Tag("Unit-Test")
class UserNameTests {
	@ParameterizedTest
	@MethodSource("valueProvider")
	void cannotHaveEmptyValue(String value) {
		var thrown = catchThrowable(() -> new UserName(value));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	private static Stream<String> valueProvider() {
		return Stream.of(null, "", "   ");
	}

	@ParameterizedTest
	@MethodSource("equalValuesProvider")
	void isEqualToOtherUserNameWithTheSameValue(String firstValue, String secondValue) {
		var firstUserName = new UserName(firstValue);
		var secondUserName = new UserName(secondValue);

		assertThat(firstUserName).isEqualTo(secondUserName);
	}

	@ParameterizedTest
	@MethodSource("equalValuesProvider")
	void hasHashCodeEqualToOtherUserNamesHashCodeWithTheSameValue(String firstValue, String secondValue) {
		var firstUserNamesHashCode = new UserName(firstValue).hashCode();
		var secondUserNamesHashCode = new UserName(secondValue).hashCode();

		assertThat(firstUserNamesHashCode).isEqualTo(secondUserNamesHashCode);
	}

	private static Stream<Arguments> equalValuesProvider() {
		return Stream.of(
			arguments("testuser@testcustomer.onmicrosoft.com", "testuser@testcustomer.onmicrosoft.com"),
			arguments("TESTUSER@TESTCUSTOMER.ONMICROSOFT.COM", "TESTUSER@TESTCUSTOMER.ONMICROSOFT.COM"),
			arguments("testuser@testcustomer.onmicrosoft.com", "TESTUSER@TESTCUSTOMER.ONMICROSOFT.COM")
		);
	}

	@Test
	void isNotEqualToOtherUserNameWithDifferentValue() {
		var firstUserName = new UserName("testuser1@testcustomer.onmicrosoft.com");
		var secondUserName = new UserName("testuser2@testcustomer.onmicrosoft.com");

		assertThat(firstUserName).isNotEqualTo(secondUserName);
	}

	@Test
	void doesNotHaveHashCodeEqualToOtherUserNamesHashCodeWithDifferentValue() {
		var firstUserNamesHashCode = new UserName("testuser1@testcustomer.onmicrosoft.com").hashCode();
		var secondUserNamesHashCode = new UserName("testuser2@testcustomer.onmicrosoft.com").hashCode();

		assertThat(firstUserNamesHashCode).isNotEqualTo(secondUserNamesHashCode);
	}
}