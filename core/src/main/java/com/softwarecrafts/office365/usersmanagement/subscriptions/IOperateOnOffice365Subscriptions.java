package com.softwarecrafts.office365.usersmanagement.subscriptions;

import com.softwarecrafts.office365.usersmanagement.customers.CustomerCspId;

import java.util.List;

public interface IOperateOnOffice365Subscriptions {
	List<CspSubscription> getAllFor(CustomerCspId customerId);
	void changeSubscriptionQuantity(CustomerCspId customerId, SubscriptionCspId subscriptionId, SubscriptionLicenseQuantity quantity);
}