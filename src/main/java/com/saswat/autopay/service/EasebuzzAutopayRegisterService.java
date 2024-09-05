package com.saswat.autopay.service;

import org.springframework.http.ResponseEntity;

import com.saswat.autopay.dto.DebitAutopayRequestDto;
import com.saswat.autopay.model.InitiateAutopayRequestDto;

public interface EasebuzzAutopayRegisterService {

	ResponseEntity<String> registerAutopay(InitiateAutopayRequestDto initiatePayRequest) throws Exception;

	ResponseEntity<String> debitRequest(DebitAutopayRequestDto debitAutopayRequestDto) throws Exception;

}
