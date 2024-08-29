//package com.saswat.autopay.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.saswat.autopay.Utils.CancelRequest;
//import com.saswat.autopay.Utils.DebitRequest;
//import com.saswat.autopay.Utils.InitiateRequest;
//import com.saswat.autopay.Utils.TransactionStatusRequest;
//import com.saswat.autopay.service.AutopayService;
//
//@RestController
//@RequestMapping("/saswat1")
//public class SignzyAutoPayController {
//
//	private static final Logger logger = LoggerFactory.getLogger(SignzyAutoPayController.class);
//	@Autowired
//	AutopayService autopayService;
//
//	@PostMapping("/initiate-payment1")
//	public ResponseEntity<String> getInitiate(@RequestBody InitiateRequest initiateRequest) throws Exception {
//
//		return autopayService.fetchInitiate(initiateRequest);
//
//	}
//
//	@PostMapping("/debitRequest1")
//	public ResponseEntity<String> getDebitrequest(@RequestBody DebitRequest debitRequest) throws Exception {
//
//		return autopayService.fetchDebitRequest(debitRequest);
//	}
//
//	@PostMapping("/Transactionstatus1")
//	public ResponseEntity<String> getTransactionStatus(@RequestBody TransactionStatusRequest request) throws Exception {
//
//		return autopayService.fetchTransactionstatus(request);
//	}
//
//	@PostMapping("/cancel-mandate1")
//	public ResponseEntity<String> getCancelMandate(@RequestBody CancelRequest cancelRequest) throws Exception {
//		return autopayService.fetchCancelrequest(cancelRequest);
//	}
//
////	@PostMapping("/callback")
////	public String getStatus(String str) {
////		logger.info("callback " + str);
////		return "callback processed";
////	}
//}
