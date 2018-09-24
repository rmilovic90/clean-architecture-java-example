package com.softwarecrafts.office365.usersmanagement.customers;

import java.util.Optional;

public interface IStoreCustomers {
	Optional<Customer> tryFindOneBy(CustomerNumber number);
}