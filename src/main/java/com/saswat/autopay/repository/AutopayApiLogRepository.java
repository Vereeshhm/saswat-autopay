package com.saswat.autopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saswat.autopay.model.AutopayApiLog;

public interface AutopayApilogrepository extends JpaRepository<AutopayApiLog, String> {

}
