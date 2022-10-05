package com.amigoscode.customer;

import com.amigoscode.clients.fraud.FraudCheckResponse;
import com.amigoscode.clients.fraud.FraudClient;
import com.amigoscode.clients.notification.NotificationClient;
import com.amigoscode.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;
	private final FraudClient fraudClient;

	private final NotificationClient notificationClient;

	public CustomerService(CustomerRepository customerRepository, FraudClient fraudClient, NotificationClient notificationClient) {
		this.customerRepository = customerRepository;
		this.fraudClient = fraudClient;
		this.notificationClient = notificationClient;
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
		FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

		if (fraudCheckResponse.isFraudster()) {
			throw new IllegalStateException("fraudster");
		}

		//todo: send notification, and make it async. i.e add to queue
		notificationClient.sendNotification(
				new NotificationRequest(
						customer.getId(),
						customer.getEmail(),
						String.format("Hi %s, welcome to the microservices world", customer.getFirstName())
				)
		);

	}
}
