package com.amigoscode.fraud;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Service
public class FraudCheckService {

	private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

	public FraudCheckService(FraudCheckHistoryRepository fraudCheckHistoryRepository) {
		this.fraudCheckHistoryRepository = fraudCheckHistoryRepository;
	}

	public boolean isFraudulentCustomer(Integer customerId) {
		fraudCheckHistoryRepository.save(
				FraudCheckHistory.builder()
						.customerId(customerId)
						.isFraudster(false)
						.createdAt(LocalDateTime.now())
						.build()
		);

		return false;
	}

}
