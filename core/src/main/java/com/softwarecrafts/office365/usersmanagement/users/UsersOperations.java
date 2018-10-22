package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.CustomerNotFoundException;
import com.softwarecrafts.office365.usersmanagement.customers.CustomerNumber;
import com.softwarecrafts.office365.usersmanagement.customers.IStoreCustomers;
import com.softwarecrafts.office365.usersmanagement.subscriptions.IOperateOnOffice365Subscriptions;

import java.util.function.Supplier;

public class UsersOperations implements IOperateOnUsers {
	private final IStoreCustomers customersStore;
	private final IOperateOnOffice365Users office365UsersOperations;
	private final IOperateOnOffice365Subscriptions office365SubscriptionsOperations;

	public UsersOperations(IStoreCustomers customersStore, IOperateOnOffice365Users office365UsersOperations,
		IOperateOnOffice365Subscriptions office365SubscriptionsOperations) {
		this.customersStore = customersStore;
		this.office365UsersOperations = office365UsersOperations;
		this.office365SubscriptionsOperations = office365SubscriptionsOperations;
	}

	public void deleteUser(DeleteUserRequest request) {
		var customer = customersStore.tryFindOneBy(request.customerNumber());
		var customersCspId = customer.orElseThrow(customerDoesNotExist(request.customerNumber())).cspId();
		var customerLicensingMode = customer.orElseThrow(customerDoesNotExist(request.customerNumber())).licensingMode();

		var deleteLicensedUser = new DeleteLicensedUserService(office365UsersOperations, office365SubscriptionsOperations, customerLicensingMode);
		deleteLicensedUser.delete(customersCspId, request.customersUserName());
	}

	private Supplier<CustomerNotFoundException> customerDoesNotExist(CustomerNumber customerNumber) {
		return () -> new CustomerNotFoundException(customerNumber);
	}
}