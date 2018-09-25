package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.Customer;
import com.softwarecrafts.office365.usersmanagement.customers.CustomerCspId;
import com.softwarecrafts.office365.usersmanagement.customers.CustomerNumber;
import com.softwarecrafts.office365.usersmanagement.customers.IStoreCustomers;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.softwarecrafts.office365.usersmanagement.users.CustomerBuilder.aCustomer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("Acceptance-Test")
@ExtendWith(MockitoExtension.class)
class DeletingLicensedUserTests {
	static final String A_CUSTOMER_NUMBER = "2105";
	static final String A_CUSTOMER_CSP_ID = "0c07c0ff-cf08-40e4-a2a3-da2b80706811";
	static final String A_CUSTOMER_USER = "testuser@testcustomer.onmicrosoft.com";

	@Mock
	IStoreCustomers customersStore;

	@Mock
	IOperateOnOffice365Users office365UsersOperations;

	@InjectMocks
	UsersOperations usersOperations;

	@Test
	void deletesTheUser() {
		when(customersStore.tryFindOneBy(aCustomerNumberOf(A_CUSTOMER_NUMBER)))
			.thenReturn(aCustomerWith(A_CUSTOMER_NUMBER, A_CUSTOMER_CSP_ID));

		usersOperations.deleteUser(deleteUserRequestFor(A_CUSTOMER_NUMBER, A_CUSTOMER_USER));

		verify(office365UsersOperations)
			.deleteOne(aCustomerCspIdOf(A_CUSTOMER_CSP_ID), aUserNameOf(A_CUSTOMER_USER));
	}

	private CustomerNumber aCustomerNumberOf(String value) {
		return new CustomerNumber(value);
	}

	private Optional<Customer> aCustomerWith(String number, String cspId) {
		return Optional.of(
			aCustomer()
				.withNumber(number)
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
}