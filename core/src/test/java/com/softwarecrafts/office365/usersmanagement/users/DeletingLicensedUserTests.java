package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.*;
import com.softwarecrafts.office365.usersmanagement.subscriptions.IOperateOnOffice365Subscriptions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.softwarecrafts.office365.usersmanagement.common.CspSubscriptionBuilder.aCspSubscription;
import static com.softwarecrafts.office365.usersmanagement.common.CspSubscriptionsBuilder.cspSubscriptionsOf;
import static com.softwarecrafts.office365.usersmanagement.common.CspSubscriptionsBuilder.emptyCspSubscriptions;
import static com.softwarecrafts.office365.usersmanagement.common.SubscriptionCspIdBuilder.aSubscriptionCspIdOf;
import static com.softwarecrafts.office365.usersmanagement.common.SubscriptionCspIdBuilder.subscriptionCspIdsOf;
import static com.softwarecrafts.office365.usersmanagement.common.SubscriptionLicenseQuantityBuilder.aSubscriptionLicenseQuantityOf;
import static com.softwarecrafts.office365.usersmanagement.users.CustomerBuilder.aCustomer;
import static com.softwarecrafts.office365.usersmanagement.users.CustomerCspIdBuilder.aCustomerCspIdOf;
import static com.softwarecrafts.office365.usersmanagement.users.UserNameBuilder.aUserNameOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@Tag("Acceptance-Test")
@ExtendWith(MockitoExtension.class)
class DeletingLicensedUserTests {
	private static final String A_CUSTOMER_NUMBER = "2105";
	private static final String A_CUSTOMER_CSP_ID = "0c07c0ff-cf08-40e4-a2a3-da2b80706811";
	private static final String A_CUSTOMER_USER = "testuser@testcustomer.onmicrosoft.com";

	@Mock
	IStoreCustomers customersStore;

	@Mock
	IOperateOnOffice365Users office365UsersOperations;

	@Mock
	IOperateOnOffice365Subscriptions office365SubscriptionsOperations;

	@InjectMocks
	UsersOperations usersOperations;

	@Test
	void throwsErrorWhenCustomerIsNotFound() {
		when(customersStore.tryFindOneBy(aCustomerNumberOf(A_CUSTOMER_NUMBER)))
			.thenReturn(noCustomer());

		var thrown = catchThrowable(() -> usersOperations.deleteUser(deleteUserRequestFor(A_CUSTOMER_NUMBER, A_CUSTOMER_USER)));

		assertThat(thrown).isInstanceOf(CustomerNotFoundException.class);
	}

	@Test
	@SuppressWarnings("ThrowableNotThrown")
	void doesNotDeleteUserOrAlignTheNumberOfAvailableLicensesOfTheAffectedSubscriptionsWhenCustomerIsNotFound() {
		when(customersStore.tryFindOneBy(aCustomerNumberOf(A_CUSTOMER_NUMBER)))
			.thenReturn(noCustomer());

		catchThrowable(() -> usersOperations.deleteUser(deleteUserRequestFor(A_CUSTOMER_NUMBER, A_CUSTOMER_USER)));

		verifyZeroInteractions(office365UsersOperations);
		verifyZeroInteractions(office365SubscriptionsOperations);
	}

	@Test
	void deletesTheUserWhenCustomerUsesAutomaticLicensingMode() {
		when(customersStore.tryFindOneBy(aCustomerNumberOf(A_CUSTOMER_NUMBER)))
			.thenReturn(aCustomerWith(A_CUSTOMER_CSP_ID, CustomerLicensingMode.AUTOMATIC));

		when(office365SubscriptionsOperations.getAllFor(aCustomerCspIdOf(A_CUSTOMER_CSP_ID)))
			.thenReturn(emptyCspSubscriptions());

		usersOperations.deleteUser(deleteUserRequestFor(A_CUSTOMER_NUMBER, A_CUSTOMER_USER));

		verify(office365UsersOperations)
			.deleteOne(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER));
	}

	@Test
	void alignsAvailableAndAssignedNumberOfLicensesOfTheAffectedSubscriptionsWhenCustomerUsesAutomaticLicensingMode() {
		when(customersStore.tryFindOneBy(aCustomerNumberOf(A_CUSTOMER_NUMBER)))
			.thenReturn(aCustomerWith(A_CUSTOMER_CSP_ID, CustomerLicensingMode.AUTOMATIC));

		when(office365UsersOperations.getAssignedSubscriptionIdsFor(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER)))
			.thenReturn(subscriptionCspIdsOf(
				"c559ba50-e818-436f-bed1-a33abdaed83d",
				"3c7e5308-b3df-4765-8f48-f4041d41082e",
				"c0fd7d87-1273-4e9e-b322-6fe89ff42b90",
				"72349e5f-2152-4804-876b-7863a1fe0891",
				"0c4aa532-b5dd-4214-be74-6d13063b0a22",
				"0bc5db69-43a1-4c4b-8626-5a48c50346dd"));

		when(office365SubscriptionsOperations.getAllFor(aCustomerCspIdOf(A_CUSTOMER_CSP_ID)))
			.thenReturn(cspSubscriptionsOf(
				aCspSubscription()
					.withId("7a423c76-e8d9-4818-9e3c-7bc69c7417dd"),
				aCspSubscription()
					.withId("c559ba50-e818-436f-bed1-a33abdaed83d")
					.withAvailableLicenses(3)
					.withAssignedLicenses(2),
				aCspSubscription()
					.withId("3c7e5308-b3df-4765-8f48-f4041d41082e")
					.withAvailableLicenses(2)
					.withAssignedLicenses(0)
					.withMinimumAllowedNumberOfAvailableLicenses(1),
				aCspSubscription()
					.withId("c0fd7d87-1273-4e9e-b322-6fe89ff42b90")
					.withAvailableLicenses(2)
					.withAssignedLicenses(4)
					.withMaximumAllowedNumberOfAvailableLicenses(3),
				aCspSubscription()
					.withId("72349e5f-2152-4804-876b-7863a1fe0891")
					.withAvailableLicenses(3)
					.withAssignedLicenses(3),
				aCspSubscription()
					.withId("0c4aa532-b5dd-4214-be74-6d13063b0a22")
					.withAvailableLicenses(1)
					.withAssignedLicenses(0)
					.withMinimumAllowedNumberOfAvailableLicenses(1),
				aCspSubscription()
					.withId("0bc5db69-43a1-4c4b-8626-5a48c50346dd")
					.withAvailableLicenses(3)
					.withAssignedLicenses(4)
					.withMaximumAllowedNumberOfAvailableLicenses(3)));

		usersOperations.deleteUser(deleteUserRequestFor(A_CUSTOMER_NUMBER, A_CUSTOMER_USER));

		verify(office365SubscriptionsOperations)
			.changeSubscriptionQuantity(aCustomerCspIdOf(A_CUSTOMER_CSP_ID),
				aSubscriptionCspIdOf("c559ba50-e818-436f-bed1-a33abdaed83d"), aSubscriptionLicenseQuantityOf(2));
		verify(office365SubscriptionsOperations)
			.changeSubscriptionQuantity(aCustomerCspIdOf(A_CUSTOMER_CSP_ID),
				aSubscriptionCspIdOf("3c7e5308-b3df-4765-8f48-f4041d41082e"), aSubscriptionLicenseQuantityOf(1));
		verify(office365SubscriptionsOperations)
			.changeSubscriptionQuantity(aCustomerCspIdOf(A_CUSTOMER_CSP_ID),
				aSubscriptionCspIdOf("c0fd7d87-1273-4e9e-b322-6fe89ff42b90"), aSubscriptionLicenseQuantityOf(3));
		verifyNoMoreInteractions(office365SubscriptionsOperations);
	}

	@Test
	void deletesTheUserWhenCustomerUsesManualLicensingMode() {
		when(customersStore.tryFindOneBy(aCustomerNumberOf(A_CUSTOMER_NUMBER)))
			.thenReturn(aCustomerWith(A_CUSTOMER_CSP_ID, CustomerLicensingMode.MANUAL));

		usersOperations.deleteUser(deleteUserRequestFor(A_CUSTOMER_NUMBER, A_CUSTOMER_USER));

		verify(office365UsersOperations)
			.deleteOne(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER));
	}

	@Test
	void doesNotChangeTheNumberOfAvailableLicensesOfTheAffectedSubscriptionsWhenCustomerUsesManualLicensingMode() {
		when(customersStore.tryFindOneBy(aCustomerNumberOf(A_CUSTOMER_NUMBER)))
			.thenReturn(aCustomerWith(A_CUSTOMER_CSP_ID, CustomerLicensingMode.MANUAL));

		usersOperations.deleteUser(deleteUserRequestFor(A_CUSTOMER_NUMBER, A_CUSTOMER_USER));

		verifyZeroInteractions(office365SubscriptionsOperations);
	}

	private CustomerNumber aCustomerNumberOf(String value) {
		return new CustomerNumber(value);
	}

	private Optional<Customer> noCustomer() {
		return Optional.empty();
	}

	private Optional<Customer> aCustomerWith(String cspId, CustomerLicensingMode licensingMode) {
		return Optional.of(
			aCustomer()
				.withCspId(cspId)
				.withLicensingMode(licensingMode)
				.build());

	}

	private DeleteUserRequest deleteUserRequestFor(String customerNumber, String customersUser) {
		return new DeleteUserRequest(new CustomerNumber(customerNumber), new UserName(customersUser));
	}
}