package com.saswat.autopay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saswat.autopay.model.TransactionEntity;

public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Long> {

}
