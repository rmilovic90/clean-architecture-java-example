package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.*;
import com.softwarecrafts.office365.usersmanagement.subscriptions.CspSubscription;
import com.softwarecrafts.office365.usersmanagement.subscriptions.IOperateOnOffice365Subscriptions;
import com.softwarecrafts.office365.usersmanagement.subscriptions.SubscriptionCspId;
import com.softwarecrafts.office365.usersmanagement.subscriptions.SubscriptionLicenseQuantity;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.softwarecrafts.office365.usersmanagement.users.CspSubscriptionBuilder.aCspSubscription;
import static com.softwarecrafts.office365.usersmanagement.users.CustomerBuilder.aCustomer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@Tag("Acceptance-Test")
@ExtendWith(MockitoExtension.class)
class DeletingLicensedUserTests {
	static final String A_CUSTOMER_NUMBER = "2105";
	static final String A_CUSTOMER_CSP_ID = "0c07c0ff-cf08-40e4-a2a3-da2b80706811";
	static final String A_CUSTOMER_SUBSCRIPTION_CSP_ID = "c559ba50-e818-436f-bed1-a33abdaed83d";
	static final String A_CUSTOMER_USER = "testuser@testcustomer.onmicrosoft.com";

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

		usersOperations.deleteUser(deleteUserRequestFor(A_CUSTOMER_NUMBER, A_CUSTOMER_USER));

		verify(office365UsersOperations)
			.deleteOne(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER));
	}

	@Test
	void alignsAvailableAndAssignedQuantitiesOfTheAffectedSubscriptionsWhenCustomerUsesAutomaticLicensingMode() {
		when(customersStore.tryFindOneBy(aCustomerNumberOf(A_CUSTOMER_NUMBER)))
			.thenReturn(aCustomerWith(A_CUSTOMER_CSP_ID, CustomerLicensingMode.AUTOMATIC));

		when(office365UsersOperations.getAssignedSubscriptionIdsFor(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER)))
			.thenReturn(subscriptionCspIdsOf(A_CUSTOMER_SUBSCRIPTION_CSP_ID));

		when(office365SubscriptionsOperations.getAllFor(aCustomerCspIdOf(A_CUSTOMER_CSP_ID)))
			.thenReturn(cspSubscriptionsOf(
				aCspSubscription()
					.withId("7a423c76-e8d9-4818-9e3c-7bc69c7417dd"),
				aCspSubscription()
					.withId(A_CUSTOMER_SUBSCRIPTION_CSP_ID)
					.withAvailableLicenses(3)
					.withAssignedLicenses(2)));

		usersOperations.deleteUser(deleteUserRequestFor(A_CUSTOMER_NUMBER, A_CUSTOMER_USER));

		verify(office365SubscriptionsOperations)
			.changeSubscriptionQuantity(aCustomerCspIdOf(A_CUSTOMER_CSP_ID),
				aSubscriptionCspIdOf(A_CUSTOMER_SUBSCRIPTION_CSP_ID), aSubscriptionLicenseQuantityOf(2));
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

	private DeleteUserRequest deleteUserRequestFor(String customerNumber, String customersUser) {
		return new DeleteUserRequest(new CustomerNumber(customerNumber), new UserName(customersUser));
	}

	private CustomerCspId aCustomerCspIdOf(String value) {
		return new CustomerCspId(value);
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

	private List<SubscriptionCspId> subscriptionCspIdsOf(String... subscriptionIds) {
		return Arrays.stream(subscriptionIds).map(SubscriptionCspId::new)
			.collect(Collectors.toList());
	}

	private List<CspSubscription> cspSubscriptionsOf(CspSubscriptionBuilder... cspSubscriptions) {
		return Arrays.stream(cspSubscriptions).map(CspSubscriptionBuilder::build)
			.collect(Collectors.toList());
	}

	private SubscriptionCspId aSubscriptionCspIdOf(String value) {
		return new SubscriptionCspId(value);
	}

	private SubscriptionLicenseQuantity aSubscriptionLicenseQuantityOf(int value) {
		return new SubscriptionLicenseQuantity(value);
	}
}