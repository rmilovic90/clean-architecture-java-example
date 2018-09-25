package com.softwarecrafts.office365.usersmanagement.common;

public abstract class ValueObject<T extends ValueObject<?>> {
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		T otherValueObject = (T) o;
		return equalsCore(otherValueObject);
	}

	protected abstract boolean equalsCore(T otherValueObject);

	@Override
	public int hashCode() {
		return hashCodeCore();
	}

	protected abstract int hashCodeCore();
}
