package com.saswat.autopay.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

import com.saswat.autopay.model.Transactionstatus;
import com.saswat.autopay.repository.AutopayApilogrepository;
import com.saswat.autopay.repository.TransactionRepository;

@RestController
@RequestMapping("/status")
public class EasebuzzStatusController {

	private static final Logger logger = LoggerFactory.getLogger(EasebuzzStatusController.class);

	@Autowired
	TransactionRepository transactionRepository;

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

		Transactionstatus transactionStatus = new Transactionstatus();

		transactionStatus.setTxnid(params.get("txnid"));
		transactionStatus.setDeductionPercentage(
				params.containsKey("deduction_percentage") ? Double.valueOf(params.get("deduction_percentage")) : null);
		transactionStatus.setNetAmountDebit(
				params.containsKey("net_amount_debit") ? Double.valueOf(params.get("net_amount_debit")) : null);
		transactionStatus.setCardCategory(params.get("cardCategory"));
		transactionStatus.setUnmappedStatus(params.get("unmappedstatus"));
		transactionStatus.setAddedOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)); // Set to current time
		transactionStatus.setAutoDebitAuthError(params.get("auto_debit_auth_error"));
		transactionStatus.setCashBackPercentage(
				params.containsKey("cash_back_percentage") ? Double.valueOf(params.get("cash_back_percentage")) : null);
		transactionStatus.setBankRefNum(params.get("bank_ref_num"));
		transactionStatus.setErrorMessage(params.get("error_Message"));
		transactionStatus.setAuthorizationStatus(params.get("authorization_status"));
		transactionStatus.setPhone(params.get("phone"));
		transactionStatus.setEasepayid(params.get("easepayid"));
		transactionStatus.setCardNum(params.get("cardnum"));
		transactionStatus.setAutoDebitAccessKey(params.get("auto_debit_access_key"));
		transactionStatus.setUpiVa(params.get("upi_va"));
		transactionStatus.setPaymentSource(params.get("payment_source"));
		transactionStatus.setCardType(params.get("card_type"));
		transactionStatus.setMode(params.get("mode"));
		transactionStatus.setError(params.get("error"));
		transactionStatus.setBankCode(params.get("bankcode"));
		transactionStatus.setNameOnCard(params.get("name_on_card"));
		transactionStatus.setBankName(params.get("bank_name"));
		transactionStatus.setIssuingBank(params.get("issuing_bank"));
		transactionStatus.setCustomerAuthenticationId(params.get("customer_authentication_id"));
		transactionStatus.setPgType(params.get("PG_TYPE"));
		transactionStatus.setAmount(params.containsKey("amount") ? Double.valueOf(params.get("amount")) : null);
		transactionStatus.setFailureUrl(params.get("furl"));
		transactionStatus.setProductInfo(params.get("productinfo"));
		transactionStatus.setAuthCode(params.get("auth_code"));
		transactionStatus.setEmail(params.get("email"));
		transactionStatus.setStatus(params.get("status"));
		transactionStatus.setHash(params.get("hash"));
		transactionStatus.setFirstname(params.get("firstname"));
		transactionStatus.setSuccessUrl(params.get("surl"));
		transactionStatus.setAutoDebitAuthMsg(params.get("auto_debit_auth_msg"));
		transactionStatus.setKey(params.get("key"));
		transactionStatus.setMerchantLogo(params.get("merchant_logo"));
		transactionStatus.setUdf1(params.get("udf1"));
		transactionStatus.setUdf2(params.get("udf2"));
		transactionStatus.setUdf3(params.get("udf3"));
		transactionStatus.setUdf4(params.get("udf4"));
		transactionStatus.setUdf5(params.containsKey("udf5") ? Double.valueOf(params.get("udf5")) : null);
		transactionStatus.setUdf6(params.get("udf6"));
		transactionStatus.setUdf7(params.get("udf7"));
		transactionStatus.setUdf8(params.get("udf8"));
		transactionStatus.setUdf9(params.get("udf9"));
		transactionStatus.setUdf10(params.get("udf10"));

		transactionRepository.save(transactionStatus);

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

		// saving transaction in table

		Transactionstatus transactionStatus = new Transactionstatus();

		// Mapping params to entity fields
		transactionStatus.setTxnid(params.get("txnid"));
		transactionStatus.setDeductionPercentage(
				params.containsKey("deduction_percentage") ? Double.valueOf(params.get("deduction_percentage")) : null);
		transactionStatus.setNetAmountDebit(
				params.containsKey("net_amount_debit") ? Double.valueOf(params.get("net_amount_debit")) : null);
		transactionStatus.setCardCategory(params.get("cardCategory"));
		transactionStatus.setUnmappedStatus(params.get("unmappedstatus"));
		transactionStatus.setAddedOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)); // Set to current time
		transactionStatus.setAutoDebitAuthError(params.get("auto_debit_auth_error"));
		transactionStatus.setCashBackPercentage(
				params.containsKey("cash_back_percentage") ? Double.valueOf(params.get("cash_back_percentage")) : null);
		transactionStatus.setBankRefNum(params.get("bank_ref_num"));
		transactionStatus.setErrorMessage(params.get("error_Message"));
		transactionStatus.setAuthorizationStatus(params.get("authorization_status"));
		transactionStatus.setPhone(params.get("phone"));
		transactionStatus.setEasepayid(params.get("easepayid"));
		transactionStatus.setCardNum(params.get("cardnum"));
		transactionStatus.setAutoDebitAccessKey(params.get("auto_debit_access_key"));
		transactionStatus.setUpiVa(params.get("upi_va"));
		transactionStatus.setPaymentSource(params.get("payment_source"));
		transactionStatus.setCardType(params.get("card_type"));
		transactionStatus.setMode(params.get("mode"));
		transactionStatus.setError(params.get("error"));
		transactionStatus.setBankCode(params.get("bankcode"));
		transactionStatus.setNameOnCard(params.get("name_on_card"));
		transactionStatus.setBankName(params.get("bank_name"));
		transactionStatus.setIssuingBank(params.get("issuing_bank"));
		transactionStatus.setCustomerAuthenticationId(params.get("customer_authentication_id"));
		transactionStatus.setPgType(params.get("PG_TYPE"));
		transactionStatus.setAmount(params.containsKey("amount") ? Double.valueOf(params.get("amount")) : null);
		transactionStatus.setFailureUrl(params.get("furl"));
		transactionStatus.setProductInfo(params.get("productinfo"));
		transactionStatus.setAuthCode(params.get("auth_code"));
		transactionStatus.setEmail(params.get("email"));
		transactionStatus.setStatus(params.get("status"));
		transactionStatus.setHash(params.get("hash"));
		transactionStatus.setFirstname(params.get("firstname"));
		transactionStatus.setSuccessUrl(params.get("surl"));
		transactionStatus.setAutoDebitAuthMsg(params.get("auto_debit_auth_msg"));
		transactionStatus.setKey(params.get("key"));
		transactionStatus.setMerchantLogo(params.get("merchant_logo"));
		transactionStatus.setUdf1(params.get("udf1"));
		transactionStatus.setUdf2(params.get("udf2"));
		transactionStatus.setUdf3(params.get("udf3"));
		transactionStatus.setUdf4(params.get("udf4"));
		transactionStatus.setUdf5(params.containsKey("udf5") ? Double.valueOf(params.get("udf5")) : null);
		transactionStatus.setUdf6(params.get("udf6"));
		transactionStatus.setUdf7(params.get("udf7"));
		transactionStatus.setUdf8(params.get("udf8"));
		transactionStatus.setUdf9(params.get("udf9"));
		transactionStatus.setUdf10(params.get("udf10"));

		transactionRepository.save(transactionStatus);

		return ResponseEntity.ok("Payment has failed");
	}

//	@PostMapping("/v1/transaction")
//	public ResponseEntity<Transactionstatus> getTransactionById(@RequestBody Map<String, String> requestBody) {
//
//		String txnid = requestBody.get("txnid");
//		Optional<Transactionstatus> transaction = transactionRepository.findByTxnid(txnid);
//		AutopayApiLog apiLog = new AutopayApiLog();
//		if (transaction.isPresent()) {
//
//			apiLog.setRequestBody(txnid);
//
//			apiLog.setUrl("autopay/api/status/v1/transaction");
//			apiLog.setResponseBody(transaction.toString());
//			apiLog.setApiType("Transaction");
//			apiLog.setCreated_date(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
//			apiLog.setStatus("Success");
//			apiLog.setCreated_by("Admin");
//			apiLog.setStatusCode(1);
//			apilogrepository.save(apiLog);
//			return ResponseEntity.ok(transaction.get());
//		} else {
//			apiLog.setRequestBody(txnid);
//			apiLog.setUrl("autopay/api/status/v1/transaction");
//			apiLog.setResponseBody(transaction.toString());
//			apiLog.setApiType("Transaction");
//			apiLog.setCreated_date(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
//			apiLog.setStatus("Failure");
//			apiLog.setCreated_by("Admin");
//			apiLog.setStatusCode(0);
//			apilogrepository.save(apiLog);
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//		}
//	}

	@PostMapping("/v1/callback")
	public String callbackURL(@RequestBody(required = false) String str) {
		logger.info("Callback received: {}", str);
		return "Callback processed";
	}

}
