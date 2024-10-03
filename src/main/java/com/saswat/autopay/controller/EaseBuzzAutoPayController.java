package com.saswat.autopay.controller;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.saswat.autopay.dto.CancelMandateDto;
import com.saswat.autopay.dto.DebitAutopayRequestDto;
import com.saswat.autopay.dto.RegisterAutopayRequestDto;
import com.saswat.autopay.dto.TransactionStatusDto;
import com.saswat.autopay.service.EasebuzzAutopayRegisterService;

@RestController
@RequestMapping("/saswat")
public class EaseBuzzAutoPayController {

	private static final Logger logger = LoggerFactory.getLogger(EaseBuzzAutoPayController.class);

	@Autowired
	EasebuzzAutopayRegisterService easebuzzautopayservice;

	@PostMapping(value = "/v1/payment/initiate", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> InitiatePay(@RequestBody RegisterAutopayRequestDto registerAutopayRequestDto)
			throws Exception {

		logger.info("Autopay Register Request : " + registerAutopayRequestDto.toString());
		return easebuzzautopayservice.registerAutopay(registerAutopayRequestDto);
	}

	@PostMapping(value = "/v1/payment/debitRequest", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> DebitRequest(@RequestBody DebitAutopayRequestDto debitAutopayRequestDto)
			throws Exception {
		logger.info("Autopay Register Request : " + debitAutopayRequestDto.toString());
		return easebuzzautopayservice.debitRequest(debitAutopayRequestDto);
	}

	@PostMapping(value = "/v1/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTransactionstatus(@RequestBody TransactionStatusDto statusrequest)
			throws Exception {

		return easebuzzautopayservice.getTransactionstatus(statusrequest);
	}

	@PostMapping(value = "/v1/payment/autodebit/mandatecancellation", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> CancelMandate(@RequestBody CancelMandateDto cancelMandateDto)
			throws NoSuchAlgorithmException, JsonProcessingException {

		return easebuzzautopayservice.cancelMandate(cancelMandateDto);
	}

}
