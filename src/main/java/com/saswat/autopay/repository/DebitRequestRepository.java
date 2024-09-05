package com.saswat.autopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saswat.autopay.model.Debitrequestdetails;

@Repository
public interface DebitRequestRepository extends JpaRepository<Debitrequestdetails, String> {

}