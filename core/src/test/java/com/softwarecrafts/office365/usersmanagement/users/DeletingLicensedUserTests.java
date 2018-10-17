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
	void deletesTheUser() {
		when(customersStore.tryFindOneBy(aCustomerNumberOf(A_CUSTOMER_NUMBER)))
			.thenReturn(aCustomerWith(A_CUSTOMER_CSP_ID));

		when(office365SubscriptionsOperations.getAllFor(aCustomerCspIdOf(A_CUSTOMER_CSP_ID)))
			.thenReturn(emptyCspSubscriptions());

		usersOperations.deleteUser(deleteUserRequestFor(A_CUSTOMER_NUMBER, A_CUSTOMER_USER));

		verify(office365UsersOperations)
			.deleteOne(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER));
	}

	@Test
	void alignsAvailableAndAssignedQuantitiesOfTheAffectedSubscriptionsWhenCustomerUsesAutomaticLicensingMode() {
		when(customersStore.tryFindOneBy(aCustomerNumberOf(A_CUSTOMER_NUMBER)))
			.thenReturn(aCustomerWith(A_CUSTOMER_CSP_ID, CustomerLicensingMode.AUTOMATIC));

		when(office365UsersOperations.getAssignedSubscriptionIdsFor(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER)))
			.thenReturn(subscriptionCspIdsOf(
				"c559ba50-e818-436f-bed1-a33abdaed83d",
				"3c7e5308-b3df-4765-8f48-f4041d41082e",
				"c0fd7d87-1273-4e9e-b322-6fe89ff42b90"));

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

	private CustomerNumber aCustomerNumberOf(String value) {
		return new CustomerNumber(value);
	}

	private Optional<Customer> aCustomerWith(String cspId) {
		return Optional.of(
			aCustomer()
				.withCspId(cspId)
				.build());
	}

	private CustomerCspId aCustomerCspIdOf(String value) {
		return new CustomerCspId(value);
	}

	private DeleteUserRequest deleteUserRequestFor(String customerNumber, String customersUser) {
		return new DeleteUserRequest(new CustomerNumber(customerNumber), new UserName(customersUser));
	}

	private UserName aUserNameOf(String value) {
		return new UserName(value);
	}

	private Optional<Customer> aCustomerWith(String cspId, CustomerLicensingMode licensingMode) {
		return Optional.of(
			aCustomer()
				.withCspId(cspId)
				.withLicensingMode(licensingMode)
				.build());
	}
}