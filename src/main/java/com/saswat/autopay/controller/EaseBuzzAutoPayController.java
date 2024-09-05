package com.saswat.autopay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saswat.autopay.dto.DebitAutopayRequestDto;
import com.saswat.autopay.dto.InitiateEnachRequestDto;
import com.saswat.autopay.model.InitiateAutopayRequestDto;
import com.saswat.autopay.service.EasebuzzAutopayRegisterService;
import com.saswat.autopay.service.EasebuzzEnachRegisterService;

@RestController
@RequestMapping("/saswat")
public class EaseBuzzAutoPayController {

	@Autowired
	EasebuzzAutopayRegisterService easebuzzautopayservice;

	@Autowired
	EasebuzzEnachRegisterService easebuzzEnachRegisterService;

	@PostMapping("/v1/payment/initiate")
	public ResponseEntity<String> InitiatePay(@ModelAttribute InitiateAutopayRequestDto initiateAutopayRequestDto)
			throws Exception {

		return easebuzzautopayservice.registerAutopay(initiateAutopayRequestDto);
	}

	@PostMapping("/v1/payment/debitRequest")
	public ResponseEntity<String> DebitRequest(@ModelAttribute DebitAutopayRequestDto debitAutopayRequestDto)
			throws Exception {

		return easebuzzautopayservice.debitRequest(debitAutopayRequestDto);
	}

	@PostMapping("/v1/payment/enach/initiate")
	public ResponseEntity<String> InitiatePay(@ModelAttribute InitiateEnachRequestDto initiateEnachRequestDto)
			throws Exception {

		return easebuzzEnachRegisterService.registerEnach(initiateEnachRequestDto);
	}

}
