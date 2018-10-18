package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.CustomerLicensingMode;
import com.softwarecrafts.office365.usersmanagement.subscriptions.IOperateOnOffice365Subscriptions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.softwarecrafts.office365.usersmanagement.common.CspSubscriptionBuilder.aCspSubscription;
import static com.softwarecrafts.office365.usersmanagement.common.CspSubscriptionsBuilder.cspSubscriptionsOf;
import static com.softwarecrafts.office365.usersmanagement.common.CspSubscriptionsBuilder.emptyCspSubscriptions;
import static com.softwarecrafts.office365.usersmanagement.common.SubscriptionCspIdBuilder.aSubscriptionCspIdOf;
import static com.softwarecrafts.office365.usersmanagement.common.SubscriptionCspIdBuilder.subscriptionCspIdsOf;
import static com.softwarecrafts.office365.usersmanagement.common.SubscriptionLicenseQuantityBuilder.aSubscriptionLicenseQuantityOf;
import static com.softwarecrafts.office365.usersmanagement.users.CustomerCspIdBuilder.aCustomerCspIdOf;
import static com.softwarecrafts.office365.usersmanagement.users.UserNameBuilder.aUserNameOf;
import static org.mockito.Mockito.*;

@Tag("Unit-Test")
@ExtendWith(MockitoExtension.class)
class DeleteLicensedUserServiceTests {
	private static final String A_CUSTOMER_CSP_ID = "5f0f1c61-b7b3-4883-a889-994e528ab10f";
	private static final String A_CUSTOMER_USER = "testuser@testcustomer.onmicrosoft.com";

	@Mock
	IOperateOnOffice365Users office365UsersOperations;

	@Mock
	IOperateOnOffice365Subscriptions office365SubscriptionsOperations;

	@Test
	void deletesTheUserWhenCustomerUsesManualLicensingMode() {
		var service = new DeleteLicensedUserService(office365UsersOperations, office365SubscriptionsOperations, CustomerLicensingMode.MANUAL);

		service.delete(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER));

		verify(office365UsersOperations).deleteOne(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER));
	}

	@Test
	void doesNotAlignTheNumberOfAvailableLicensesOfTheAffectedSubscriptionsWhenCustomerUsesManualLicensingMode() {
		var service = new DeleteLicensedUserService(office365UsersOperations, office365SubscriptionsOperations, CustomerLicensingMode.MANUAL);

		service.delete(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER));

		verifyZeroInteractions(office365SubscriptionsOperations);
	}

	@Test
	void deletesTheUserWhenCustomerUsesAutomaticLicensingMode() {
		when(office365SubscriptionsOperations.getAllFor(aCustomerCspIdOf(A_CUSTOMER_CSP_ID)))
			.thenReturn(emptyCspSubscriptions());

		var service = new DeleteLicensedUserService(office365UsersOperations, office365SubscriptionsOperations, CustomerLicensingMode.AUTOMATIC);

		service.delete(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER));

		verify(office365UsersOperations).deleteOne(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER));
	}

	@Test
	void alignsTheNumberOfAvailableLicensesOfTheAffectedSubscriptionsWhenCustomerUsesAutomaticLicensingMode() {
		when(office365UsersOperations.getAssignedSubscriptionIdsFor(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER)))
			.thenReturn(subscriptionCspIdsOf("c559ba50-e818-436f-bed1-a33abdaed83d"));

		when(office365SubscriptionsOperations.getAllFor(aCustomerCspIdOf(A_CUSTOMER_CSP_ID)))
			.thenReturn(cspSubscriptionsOf(
				aCspSubscription()
					.withId("c559ba50-e818-436f-bed1-a33abdaed83d")
					.withAvailableLicenses(3)
					.withAssignedLicenses(2)));

		var service = new DeleteLicensedUserService(office365UsersOperations, office365SubscriptionsOperations, CustomerLicensingMode.AUTOMATIC);

		service.delete(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER));

		verify(office365SubscriptionsOperations).changeSubscriptionQuantity(aCustomerCspIdOf(A_CUSTOMER_CSP_ID),
			aSubscriptionCspIdOf("c559ba50-e818-436f-bed1-a33abdaed83d"), aSubscriptionLicenseQuantityOf(2));
	}
}