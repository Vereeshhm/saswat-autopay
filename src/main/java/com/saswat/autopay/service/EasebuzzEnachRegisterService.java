package com.saswat.autopay.service;

import org.springframework.http.ResponseEntity;

import com.saswat.autopay.dto.InitiateEnachRequestDto;

public interface EasebuzzEnachRegisterService {

	ResponseEntity<String> registerEnach(InitiateEnachRequestDto initiateEnachRequestDto);

	
}
