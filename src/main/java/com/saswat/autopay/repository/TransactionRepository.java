package com.saswat.autopay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saswat.autopay.model.Transactionstatus;

@Repository
public interface TransactionRepository extends JpaRepository<Transactionstatus, String> {

	Optional<Transactionstatus> findByTxnid(String txnid);



}