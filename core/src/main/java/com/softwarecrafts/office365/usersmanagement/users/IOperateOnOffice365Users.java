package com.softwarecrafts.office365.usersmanagement.users;

import com.softwarecrafts.office365.usersmanagement.customers.CustomerCspId;
import com.softwarecrafts.office365.usersmanagement.subscriptions.SubscriptionCspId;

import java.util.List;

public interface IOperateOnOffice365Users {
	List<SubscriptionCspId> getAssignedSubscriptionIdsFor(CustomerCspId customerId, UserName userName);
	void deleteOne(CustomerCspId customerId, UserName userName);
}