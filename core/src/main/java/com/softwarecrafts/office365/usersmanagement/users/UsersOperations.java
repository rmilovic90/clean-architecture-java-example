package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.IStoreCustomers;
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

		office365UsersOperations.deleteOne(customer.get().cspId(), request.customersUserName());
	}
}