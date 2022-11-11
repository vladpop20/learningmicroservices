package com.coodru.customer;

public record CustomerRegistrationRequest(
		String firstName,
		String lastName,
		String email
) {

}
