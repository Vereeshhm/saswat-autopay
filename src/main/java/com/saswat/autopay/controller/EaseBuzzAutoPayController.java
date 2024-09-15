package com.saswat.autopay.controller;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saswat.autopay.dto.CancelMandateDto;
import com.saswat.autopay.dto.DebitAutopayRequestDto;
import com.saswat.autopay.dto.InitiateEnachRequestDto;
import com.saswat.autopay.dto.RegisterAutopayRequestDto;
import com.saswat.autopay.dto.TransactionStatusDto;
import com.saswat.autopay.service.EasebuzzAutopayRegisterService;
import com.saswat.autopay.service.EasebuzzEnachRegisterService;

@Controller
@RequestMapping("/saswat")
public class EaseBuzzAutoPayController {

	private static final Logger logger = LoggerFactory.getLogger(EaseBuzzAutoPayController.class);

	@Autowired
	EasebuzzAutopayRegisterService easebuzzautopayservice;

	@Autowired
	EasebuzzEnachRegisterService easebuzzEnachRegisterService;

	/*
	 * @PostMapping("/v1/payment/initiate") public ResponseEntity<String>
	 * InitiatePay(@RequestParam("amount") Float amount,
	 * 
	 * @RequestParam("productinfo") String productinfo,
	 * 
	 * @RequestParam("firstname") String firstname,
	 * 
	 * @RequestParam("phone") String phone,
	 * 
	 * @RequestParam("email") String email,
	 * 
	 * @RequestParam("udf1") String udf1,
	 * 
	 * @RequestParam("maxAmount") Float maxAmount,
	 * 
	 * @RequestParam("address1") String address1,
	 * 
	 * @RequestParam("address2") String address2,
	 * 
	 * @RequestParam("city") String city,
	 * 
	 * @RequestParam("state") String state,
	 * 
	 * @RequestParam("country") String country,
	 * 
	 * @RequestParam("zipcode") String zipcode,
	 * 
	 * @RequestParam("final_collection_date") String finalCollectionDate,
	 * 
	 * @RequestParam("show_payment_mode") String showPaymentMode) throws Exception {
	 * 
	 * 
	 * RegisterAutopayRequestDto registerAutopayRequestDto = new
	 * RegisterAutopayRequestDto(); registerAutopayRequestDto.setAmount(amount);
	 * registerAutopayRequestDto.setProductinfo(productinfo);
	 * registerAutopayRequestDto.setFirstname(firstname);
	 * registerAutopayRequestDto.setPhone(phone);
	 * registerAutopayRequestDto.setEmail(email);
	 * registerAutopayRequestDto.setUdf1(udf1);
	 * registerAutopayRequestDto.setMaxAmount(maxAmount);
	 * registerAutopayRequestDto.setAddress1(address1);
	 * registerAutopayRequestDto.setAddress2(address2);
	 * registerAutopayRequestDto.setCity(city);
	 * registerAutopayRequestDto.setState(state);
	 * registerAutopayRequestDto.setCountry(country);
	 * registerAutopayRequestDto.setZipcode(zipcode);
	 * registerAutopayRequestDto.setFinal_collection_date(finalCollectionDate);
	 * registerAutopayRequestDto.setShow_payment_mode(showPaymentMode);
	 * 
	 * logger.info("Autopay Register Request : ",registerAutopayRequestDto);
	 * 
	 * return easebuzzautopayservice.registerAutopay(registerAutopayRequestDto); }
	 */

	@PostMapping("/v1/payment/initiate")
	public ResponseEntity<String> InitiatePay(@RequestBody RegisterAutopayRequestDto registerAutopayRequestDto)
			throws Exception {

		logger.info("Autopay Register Request : " + registerAutopayRequestDto.toString());

		return easebuzzautopayservice.registerAutopay(registerAutopayRequestDto);
	}

	@PostMapping("/v1/payment/debitRequest")
	public ResponseEntity<String> DebitRequest(@RequestBody DebitAutopayRequestDto debitAutopayRequestDto)
			throws Exception {

		return easebuzzautopayservice.debitRequest(debitAutopayRequestDto);
	}

	@PostMapping("/v1/payment/autodebit/mandatecancellation")
	public ResponseEntity<String> CancelMandate(@RequestBody CancelMandateDto cancelMandateDto)
			throws NoSuchAlgorithmException, JsonProcessingException {

		return easebuzzautopayservice.cancelMandate(cancelMandateDto);
	}

	@PostMapping("/v1/transaction")
	public ResponseEntity<String> getTransactionstatus(@RequestBody TransactionStatusDto statusrequest)
			throws Exception {

		return easebuzzautopayservice.getTransactionstatus(statusrequest);
	}

	@PostMapping("/v1/payment/enach/initiate")
	public ResponseEntity<String> InitiatePay(@ModelAttribute InitiateEnachRequestDto initiateEnachRequestDto)
			throws Exception {

		return easebuzzEnachRegisterService.registerEnach(initiateEnachRequestDto);
	}

}
