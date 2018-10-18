package com.softwarecrafts.office365.usersmanagement.users;

final class UserNameBuilder {
	static UserName aUserNameOf(String value) {
		return new UserName(value);
	}

	private UserNameBuilder() {}
}