package com.softwarecrafts.office365.usersmanagement.subscriptions;

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
class SubscriptionCspIdTest {
	@ParameterizedTest
	@MethodSource("valueProvider")
	void cannotHaveEmptyValue(String value) {
		var thrown = catchThrowable(() -> new SubscriptionCspId(value));

		assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
	}

	private static Stream<String> valueProvider() {
		return Stream.of(null, "", "   ");
	}

	@ParameterizedTest
	@MethodSource("equalValuesProvider")
	void isEqualToOtherSubscriptionCspIdWithTheSameValue(String firstValue, String secondValue) {
		var firstSubscriptionCspId = new SubscriptionCspId(firstValue);
		var secondSubscriptionCspId = new SubscriptionCspId(secondValue);

		assertThat(firstSubscriptionCspId).isEqualTo(secondSubscriptionCspId);
	}

	@ParameterizedTest
	@MethodSource("equalValuesProvider")
	void hasHashCodeEqualToOtherSubscriptionCspIdsHashCodeWithTheSameValue(String firstValue, String secondValue) {
		var firstSubscriptionCspIdsHashCode = new SubscriptionCspId(firstValue).hashCode();
		var secondSubscriptionCspIdsHashCode = new SubscriptionCspId(secondValue).hashCode();

		assertThat(firstSubscriptionCspIdsHashCode).isEqualTo(secondSubscriptionCspIdsHashCode);
	}

	private static Stream<Arguments> equalValuesProvider() {
		return Stream.of(
			arguments("7b22f4db-edb1-4a34-8944-194df8c6f1fe", "7b22f4db-edb1-4a34-8944-194df8c6f1fe"),
			arguments("7B22F4DB-EDB1-4A34-8944-194DF8C6F1FE", "7B22F4DB-EDB1-4A34-8944-194DF8C6F1FE"),
			arguments("7b22f4db-edb1-4a34-8944-194df8c6f1fe", "7B22F4DB-EDB1-4A34-8944-194DF8C6F1FE")
		);
	}

	@Test
	void isNotEqualToOtherSubscriptionCspIdWithDifferentValue() {
		var firstSubscriptionCspId = new SubscriptionCspId("7b22f4db-edb1-4a34-8944-194df8c6f1fe");
		var secondSubscriptionCspId = new SubscriptionCspId("b9c12b38-d324-482f-a8c6-7e5d56627c38");

		assertThat(firstSubscriptionCspId).isNotEqualTo(secondSubscriptionCspId);
	}

	@Test
	void doesNotHaveHashCodeEqualToOtherSubscriptionCspIdsHashCodeWithDifferentValue() {
		var firstSubscriptionCspIdsHashCode = new SubscriptionCspId("7b22f4db-edb1-4a34-8944-194df8c6f1fe").hashCode();
		var secondSubscriptionCspIdsHashCode = new SubscriptionCspId("b9c12b38-d324-482f-a8c6-7e5d56627c38").hashCode();

		assertThat(firstSubscriptionCspIdsHashCode).isNotEqualTo(secondSubscriptionCspIdsHashCode);
	}
}