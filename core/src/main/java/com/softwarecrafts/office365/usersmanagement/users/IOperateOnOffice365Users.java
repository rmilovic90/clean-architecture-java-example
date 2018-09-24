package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.CustomerCspId;

public interface IOperateOnOffice365Users {
	void deleteOne(CustomerCspId customerId, UserName userName);
}