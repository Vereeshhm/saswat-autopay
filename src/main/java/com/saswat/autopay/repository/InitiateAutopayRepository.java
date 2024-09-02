package com.saswat.autopay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saswat.autopay.model.InitiateAutopayRequestDto;

@Repository
public interface InitiateAutopayRepository extends JpaRepository<InitiateAutopayRequestDto, String> {

	 @Query("SELECT i FROM InitiateAutopayRequestDto i WHERE i.customer_authentication_id = :customerAuthenticationId")
	    Optional<InitiateAutopayRequestDto> findByCustomerAuthenticationId(@Param("customerAuthenticationId") String customerAuthenticationId);
}
