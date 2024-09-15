package com.saswat.autopay.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saswat.autopay.repository.AutopayApilogrepository;

@RestController
@RequestMapping("/status")
public class EasebuzzStatusController {

	private static final Logger logger = LoggerFactory.getLogger(EasebuzzStatusController.class);

	@Autowired
	AutopayApilogrepository apilogrepository;

	@PostMapping("/v1/success-redirect")
	public ResponseEntity<String> successRedirect(@RequestParam Map<String, String> params) {

		logger.info("Success: {}", params);

		// Extracting specific fields
		String transactionId = params.get("txnid");
		String amount = params.get("amount");
		String status = params.get("status");
		String productinfo = params.get("productinfo");
		String autoDebitAccessKey = params.get("auto_debit_access_key");
		String customerAuthenticationId = params.get("customer_authentication_id");
		String errorMessage = params.get("error_Message");
		String authorizationstatus = params.get("authorization_status");
		String upi_va = params.get("upi_va");
		String cardtype = params.get("card_type");
		String error = params.get("error");
		String auto_debit_auth_msg = params.get("auto_debit_auth_msg");
		String paymentsource = params.get("payment_source");
		String hash = params.get("hash");
		String firstname = params.get("firstname");
		String phone = params.get("phone");
		String email = params.get("email");
		String successUrl = params.get("surl");

		// Logging extracted fields for debugging
		logger.info("Transaction ID: {}", transactionId);
		logger.info("Amount: {}", amount);
		logger.info("Status: {}", status);
		logger.info("productinfo : {} ", productinfo);
		logger.info("Auto Debit Access Key: {}", autoDebitAccessKey);
		logger.info("customer_authentication_id: {}", customerAuthenticationId);
		logger.info("error_Message: {}", errorMessage);
		logger.info("authorizationstatus:{}", authorizationstatus);
		logger.info("upi_va :{}", upi_va);
		logger.info("card_type:{}", cardtype);
		logger.info("error :{}", error);
		logger.info("auto_debit_auth_msg :{}", auto_debit_auth_msg);
		logger.info("paymentsource :{}", paymentsource);
		logger.info("Hash: {}", hash);
		logger.info("First Name: {}", firstname);
		logger.info("phone :{}", phone);
		logger.info("email :{}", email);
		logger.info("Success URL: {}", successUrl);
		logger.info("easepayid:{}", params.get("easepayid"));

		return ResponseEntity.ok("Payment is successful");
	}

	@PostMapping("/v1/failure-redirect")
	public ResponseEntity<String> failureRedirect(@RequestParam Map<String, String> params) {
		logger.info("Failure: {}", params);

		// Extracting specific fields
		String transactionId = params.get("txnid");
		String amount = params.get("amount");
		String productinfo = params.get("productinfo");
		String status = params.get("status");
		String autoDebitAccessKey = params.get("auto_debit_access_key");
		String customerAuthenticationId = params.get("customer_authentication_id");
		String errorMessage = params.get("error_Message");
		String authorizationstatus = params.get("authorization_status");
		String upi_va = params.get("upi_va");
		String cardtype = params.get("card_type");
		String error = params.get("error");
		String auto_debit_auth_msg = params.get("auto_debit_auth_msg");
		String hash = params.get("hash");
		String firstname = params.get("firstname");
		String phone = params.get("phone");
		String email = params.get("email");
		String successUrl = params.get("surl");
		String paymentsource = params.get("payment_source");
		String failureUrl = params.get("furl");
		String autodebitautherror = params.get("auto_debit_auth_error");

		// Logging extracted fields for debugging
		logger.info("Transaction ID: {}", transactionId);
		logger.info("Amount: {}", amount);
		logger.info("Status: {}", status);
		logger.info("productinfo : {} ", productinfo);
		logger.info("Auto Debit Access Key: {}", autoDebitAccessKey);
		logger.info("customer_authentication_id: {}", customerAuthenticationId);
		logger.info("error_Message: {}", errorMessage);
		logger.info("authorizationstatus:{}", authorizationstatus);
		logger.info("auto_debit_auth_error :{} ", autodebitautherror);
		logger.info("upi_va :{}", upi_va);
		logger.info("card_type:{}", cardtype);
		logger.info("error :{}", error);
		logger.info("auto_debit_auth_msg :{}", auto_debit_auth_msg);
		logger.info("paymentsource :{}", paymentsource);
		logger.info("Hash: {}", hash);
		logger.info("First Name: {}", firstname);
		logger.info("phone :{}", phone);
		logger.info("email :{}", email);
		logger.info("Success URL: {}", successUrl);
		logger.info("furl :{}", failureUrl);

		

		return ResponseEntity.ok("Payment has failed");
	}

	@PostMapping("/v1/callback")
	public String callbackURL(@RequestBody(required = false) String str) {
		logger.info("Callback received: {}", str);
		return "Callback processed";
	}

}
