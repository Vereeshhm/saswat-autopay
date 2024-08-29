//package com.saswat.autopay.repository;
//
//
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.saswat.autopay.model.InitiatePaymentResponseEntity;
//
//@Repository
//public interface InitiatePaymentResponseRepository extends JpaRepository<InitiatePaymentResponseEntity, String> {
//
//
//
//    @Query("SELECT i FROM InitiatePaymentResponseEntity i WHERE i.result.customerAuthenticationId = :customerAuthenticationId")
//    InitiatePaymentResponseEntity findByCustomerAuthenticationId(@Param("customerAuthenticationId") String customerAuthenticationId);
//
//}
