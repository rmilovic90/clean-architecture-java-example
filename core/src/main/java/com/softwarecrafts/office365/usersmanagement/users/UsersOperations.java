package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.IStoreCustomers;

public class UsersOperations {
	private final IStoreCustomers customersStore;
	private final IOperateOnOffice365Users office365UsersOperations;

	public UsersOperations(IStoreCustomers customersStore, IOperateOnOffice365Users office365UsersOperations) {
		this.customersStore = customersStore;
		this.office365UsersOperations = office365UsersOperations;
	}

	public void deleteUser(DeleteUserRequest request) {}
}