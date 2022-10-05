package com.amigoscode.fraud;

import com.amigoscode.clients.fraud.FraudCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/fraud-check")
@Slf4j
public class FraudController {

	private final FraudCheckService fraudCheckService;

	public FraudController(FraudCheckService fraudCheckService) {
		this.fraudCheckService = fraudCheckService;
	}

	@GetMapping(path = "{customerId}")
	public FraudCheckResponse isFraudster(@PathVariable Integer customerId) {
		boolean isFraudulentCustomer = fraudCheckService.isFraudulentCustomer(customerId);
		log.info("fraud check request for customer {}", customerId);

		return new FraudCheckResponse(isFraudulentCustomer);
	}
}
