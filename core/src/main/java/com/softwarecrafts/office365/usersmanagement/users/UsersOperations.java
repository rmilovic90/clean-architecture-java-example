package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.IStoreCustomers;
import com.softwarecrafts.office365.usersmanagement.subscriptions.CspSubscription;
import com.softwarecrafts.office365.usersmanagement.subscriptions.IOperateOnOffice365Subscriptions;

public class UsersOperations {
	private final IStoreCustomers customersStore;
	private final IOperateOnOffice365Users office365UsersOperations;
	private final IOperateOnOffice365Subscriptions office365SubscriptionsOperations;

	public UsersOperations(IStoreCustomers customersStore, IOperateOnOffice365Users office365UsersOperations,
		IOperateOnOffice365Subscriptions office365SubscriptionsOperations) {
		this.customersStore = customersStore;
		this.office365UsersOperations = office365UsersOperations;
		this.office365SubscriptionsOperations = office365SubscriptionsOperations;
	}

	void deleteUser(DeleteUserRequest request) {
		var customer = customersStore.tryFindOneBy(request.customerNumber());

		var idsOfTheAffectedSubscriptions = office365UsersOperations.getAssignedSubscriptionIdsFor(
			customer.get().cspId(), request.customersUserName());

		office365UsersOperations.deleteOne(customer.get().cspId(), request.customersUserName());

		var customersSubscriptions = office365SubscriptionsOperations.getAllFor(customer.get().cspId());

		customersSubscriptions.stream()
			.filter(subscription -> idsOfTheAffectedSubscriptions.contains(subscription.id()))
			.map(subscription -> new CspSubscription(subscription.id(), subscription.numberOfAssignedLicenses(),
				subscription.numberOfAssignedLicenses(), subscription.minAllowedNumberOfAvailableLicenses(),
				subscription.maxAllowedNumberOfAvailableLicenses()))
			.forEach(subscription -> office365SubscriptionsOperations.changeSubscriptionQuantity(
				customer.get().cspId(), subscription.id(), subscription.numberOfAvailableLicenses()));
	}
}