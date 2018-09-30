package com.softwarecrafts.office365.usersmanagement.subscriptions;

import com.softwarecrafts.office365.usersmanagement.customers.CustomerCspId;

public interface IOperateOnOffice365Subscriptions {
	CspSubscriptions getAllFor(CustomerCspId customerId);
	void changeSubscriptionQuantity(CustomerCspId customerId, SubscriptionCspId subscriptionId, SubscriptionLicenseQuantity quantity);
}