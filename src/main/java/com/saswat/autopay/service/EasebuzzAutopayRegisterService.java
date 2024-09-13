package com.saswat.autopay.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.saswat.autopay.dto.CancelMandateDto;
import com.saswat.autopay.dto.DebitAutopayRequestDto;
import com.saswat.autopay.dto.RegisterAutopayRequestDto;
import com.saswat.autopay.dto.TransactionStatusDto;
import com.saswat.autopay.model.InitiateAutopayRequestDto;

public interface EasebuzzAutopayRegisterService {

	ResponseEntity<String> registerAutopay(RegisterAutopayRequestDto registerAutopayRequestDto) throws Exception;

	ResponseEntity<String> debitRequest(DebitAutopayRequestDto debitAutopayRequestDto) throws Exception;

	ResponseEntity<String> cancelMandate(CancelMandateDto cancelMandateDto) throws NoSuchAlgorithmException, JsonProcessingException;

	ResponseEntity<String> getTransactionstatus(TransactionStatusDto statusrequest) throws Exception;

}
