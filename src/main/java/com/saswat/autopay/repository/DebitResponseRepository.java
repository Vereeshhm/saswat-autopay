package com.saswat.autopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saswat.autopay.model.Debitresponsedetails;

@Repository
public interface DebitResponseRepository extends JpaRepository<Debitresponsedetails, String> {

}