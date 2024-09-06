package com.saswat.autopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saswat.autopay.model.Cancelmandatedetails;

@Repository
public interface CancelMandatedetailsrepository extends JpaRepository<Cancelmandatedetails, String>{

}
