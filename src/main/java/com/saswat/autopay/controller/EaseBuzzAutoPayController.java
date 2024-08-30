package com.saswat.autopay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saswat.autopay.Utils.DebitAutopayRequestDto;
import com.saswat.autopay.Utils.InitiateAutopayRequestDto;
import com.saswat.autopay.service.EasebuzzAutopayRegisterService;


@RestController
@RequestMapping("/saswat")
public class EaseBuzzAutoPayController {


	@Autowired
	EasebuzzAutopayRegisterService easebuzzautopayservice;

	@PostMapping("/v1/payment/initiate")
	public ResponseEntity<String> InitiatePay(@ModelAttribute InitiateAutopayRequestDto initiateAutopayRequestDto) throws Exception {

		
		return easebuzzautopayservice.registerAutopay(initiateAutopayRequestDto);
	}

	@PostMapping("/v1/payment/debitRequest")
	public ResponseEntity<String> DebitRequest(@ModelAttribute DebitAutopayRequestDto debitAutopayRequestDto) throws Exception {

		
		return easebuzzautopayservice.debitRequest(debitAutopayRequestDto);
	}

	
}
