package com.amigoscode.customer;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final RestTemplate restTemplate;

	public CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {
		this.customerRepository = customerRepository;
		this.restTemplate = restTemplate;
	}

	public void registerCustomer(CustomerRegistrationRequest request) {
		Customer customer = Customer.builder()
				.firstName(request.firstName())
				.lastName(request.lastName())
				.email(request.email())
				.build();

		//todo: check if email is valid
		//todo: check if email not taken
		customerRepository.saveAndFlush(customer);

		//todo: check if fraudster
		FraudCheckResponse response = restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}",
				FraudCheckResponse.class, customer.getId());
		if (response.isFraudster()) {
			throw new IllegalStateException("fraudster");
		}

		//todo: send notification

	}
}
