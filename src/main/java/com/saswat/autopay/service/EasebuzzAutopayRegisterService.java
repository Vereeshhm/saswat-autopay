package com.saswat.autopay.service;

import org.springframework.http.ResponseEntity;

import com.saswat.autopay.Utils.InitiateAutopayRequestDto;

public interface EasebuzzAutopayRegisterService {

	ResponseEntity<String> registerAutopay(InitiateAutopayRequestDto initiatePayRequest) throws Exception;

}
