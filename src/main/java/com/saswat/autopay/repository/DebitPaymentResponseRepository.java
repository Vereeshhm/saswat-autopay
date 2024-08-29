//package com.saswat.autopay.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.saswat.autopay.model.DebitPaymentResponseEntity;
//
//@Repository
//public interface DebitPaymentResponseRepository extends JpaRepository<DebitPaymentResponseEntity, String>{
//
//	
//	
//
//    @Query("SELECT i FROM DebitPaymentResponseEntity i WHERE i.result.txnId = :txnId")
//    DebitPaymentResponseEntity findByTxnId(@Param("txnId") String txnId);
//
//}
