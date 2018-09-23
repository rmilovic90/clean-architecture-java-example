# Requirements Specification

## Use Case: Customer's admin user deletes licensed user

### Scenario: Customer uses Automatic Licensing Mode

```
Given I am a customer's admin user
When I delete licensed user
Then the user should be deleted
  And the quantity of the affected subscriptions
  should be aligned with the number of assigned
  licenses
```

### Scenario: Customer uses Manual Licensing Mode

```
Given I am a customer's admin user
When I delete licensed user
Then the user should be deleted
  And the quantity of the affected subscriptions
  should be unaffected
```

## Use Case: Customer's user views other user's details

### Scenario: Presenting other user's contact information

```
Given I am a customer's user
When I view other user's details
Then I should be presented with its contact information
```
