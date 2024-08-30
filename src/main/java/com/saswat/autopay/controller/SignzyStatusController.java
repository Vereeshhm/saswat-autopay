package com.saswat.autopay.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class SignzyStatusController {

	private static final Logger logger = LoggerFactory.getLogger(SignzyStatusController.class);

//    @PostMapping("/v1/success-redirect")
//    public String successRedirect(@RequestBody(required = false) String str) {
//        logger.info("Success: {}", str);
//        return "Payment is successful"; // Simple response
//    }

	@PostMapping("/v1/success-redirect")
	public ResponseEntity<String> successRedirect(@RequestParam Map<String, String> params) {
		// Logging the entire parameters map
		logger.info("Success: {}", params);

		// Extracting specific fields
		String transactionId = params.get("txnid");
		String amount = params.get("amount");
		String status = params.get("status");
		String autoDebitAccessKey = params.get("auto_debit_access_key");

		String customerAuthenticationId = params.get("customer_authentication_id");

		String errorMessage = params.get("error_Message");

		String authorizationstatus = params.get("authorization_status");
		String upi_va = params.get("upi_va");
		String error = params.get("error");
		String auto_debit_auth_msg = params.get("auto_debit_auth_msg");

		String hash = params.get("hash");
		String firstname = params.get("firstname");
		String successUrl = params.get("surl");
		// Logging extracted fields for debugging
		logger.info("Transaction ID: {}", transactionId);
		logger.info("Amount: {}", amount);
		logger.info("Status: {}", status);
		logger.info("Auto Debit Access Key: {}", autoDebitAccessKey);
		logger.info("customer_authentication_id: {}", customerAuthenticationId);
		logger.info("error_Message: {}", errorMessage);
		logger.info("authorizationstatus:{}", authorizationstatus);
		logger.info("upi_va :{}", upi_va);
		logger.info("error :{}", error);
		logger.info("auto_debit_auth_msg :{}", auto_debit_auth_msg);

		logger.info("Hash: {}", hash);
		logger.info("First Name: {}", firstname);
		logger.info("Success URL: {}", successUrl);

		return ResponseEntity.ok("Payment is successful");
	}

	@PostMapping("/v1/failure-redirect")
	public String failureRedirect(@RequestBody(required = false) String str) {
		logger.info("Failure: {}", str);
		return "Payment has failed"; // Simple response
	}

	@PostMapping("/v1/callback")
	public String callbackURL(@RequestBody(required = false) String str) {
		logger.info("Callback received: {}", str);
		return "Callback processed"; // Simple response
	}
}
