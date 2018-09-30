package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.CustomerCspId;
import com.softwarecrafts.office365.usersmanagement.customers.CustomerNumber;
import com.softwarecrafts.office365.usersmanagement.customers.IStoreCustomers;
import com.softwarecrafts.office365.usersmanagement.subscriptions.CspSubscription;
import com.softwarecrafts.office365.usersmanagement.subscriptions.IOperateOnOffice365Subscriptions;
import com.softwarecrafts.office365.usersmanagement.subscriptions.SubscriptionLicenseQuantity;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
		var customersCspId = customer.orElseThrow(customerDoesNotExist(request.customerNumber())).cspId();

		var idsOfTheAffectedSubscriptions = office365UsersOperations.getAssignedSubscriptionIdsFor(
			customersCspId, request.customersUserName());

		office365UsersOperations.deleteOne(customersCspId, request.customersUserName());

		var customersSubscriptions = office365SubscriptionsOperations.getAllFor(customersCspId);

		customersSubscriptions.onlyWithIdsOf(idsOfTheAffectedSubscriptions)
			.modifyNumberOfAvailableLicensesUsing(alignmentWithAssignedLicenses())
			.items().forEach(changeSubscriptionQuantityFor(customersCspId));
	}

	private Supplier<IllegalStateException> customerDoesNotExist(CustomerNumber customerNumber) {
		return () -> new IllegalStateException(String.format("Customer with number %s does not exist.", customerNumber));
	}

	private Function<CspSubscription, SubscriptionLicenseQuantity> alignmentWithAssignedLicenses() {
		return CspSubscription::numberOfAssignedLicenses;
	}

	private Consumer<CspSubscription> changeSubscriptionQuantityFor(CustomerCspId customerId) {
		return subscription -> office365SubscriptionsOperations.changeSubscriptionQuantity(
			customerId, subscription.id(), subscription.numberOfAvailableLicenses());
	}
}