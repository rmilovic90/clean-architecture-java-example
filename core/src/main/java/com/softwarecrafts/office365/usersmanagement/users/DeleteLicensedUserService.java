package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.CustomerCspId;
import com.softwarecrafts.office365.usersmanagement.customers.CustomerLicensingMode;
import com.softwarecrafts.office365.usersmanagement.subscriptions.CspSubscription;
import com.softwarecrafts.office365.usersmanagement.subscriptions.IOperateOnOffice365Subscriptions;
import com.softwarecrafts.office365.usersmanagement.subscriptions.SubscriptionCspId;

import java.util.List;
import java.util.function.Consumer;

import static com.softwarecrafts.office365.usersmanagement.customers.CustomerLicensingMode.AUTOMATIC;
import static java.util.Collections.emptyList;

class DeleteLicensedUserService {
	private final IOperateOnOffice365Users office365UsersOperations;
	private final IOperateOnOffice365Subscriptions office365SubscriptionsOperations;
	private final CustomerLicensingMode customerLicensingMode;

	DeleteLicensedUserService(IOperateOnOffice365Users office365UsersOperations, IOperateOnOffice365Subscriptions office365SubscriptionsOperations,
	  	CustomerLicensingMode customerLicensingMode) {
		this.office365UsersOperations = office365UsersOperations;
		this.office365SubscriptionsOperations = office365SubscriptionsOperations;
		this.customerLicensingMode = customerLicensingMode;
	}

	void delete(CustomerCspId customersCspId, UserName userName) {
		var idsOfTheAffectedSubscriptions = getIdsOfTheAffectedSubscriptionsFor(customersCspId, userName);

		office365UsersOperations.deleteOne(customersCspId, userName);

		if (customerLicensingMode == AUTOMATIC) {
			alignNumberOfAvailableSubscriptionsFor(customersCspId, idsOfTheAffectedSubscriptions);
		}
	}

	private List<SubscriptionCspId> getIdsOfTheAffectedSubscriptionsFor(CustomerCspId customersCspId, UserName userName) {
		return customerLicensingMode == AUTOMATIC
			? office365UsersOperations.getAssignedSubscriptionIdsFor(customersCspId, userName)
			: emptyList();
	}

	private void alignNumberOfAvailableSubscriptionsFor(CustomerCspId customersCspId, List<SubscriptionCspId> idsOfTheAffectedSubscriptions) {
		var customersSubscriptions = office365SubscriptionsOperations.getAllFor(customersCspId);
		customersSubscriptions.onlyWithIdsOf(idsOfTheAffectedSubscriptions)
			.withAlignedNumberOfAvailableLicenses()
			.items().forEach(changeSubscriptionQuantityFor(customersCspId));
	}

	private Consumer<CspSubscription> changeSubscriptionQuantityFor(CustomerCspId customerId) {
		return subscription -> office365SubscriptionsOperations.changeSubscriptionQuantity(
			customerId, subscription.id(), subscription.numberOfAvailableLicenses());
	}
}