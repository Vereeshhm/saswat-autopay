package com.saswat.autopay.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.saswat.autopay.model.LenderSubmerchant;

public interface LenderSubmerchantRepository extends JpaRepository<LenderSubmerchant, Long> {
	@Query("SELECT ls FROM LenderSubmerchant ls WHERE LOWER(ls.lenderName) = LOWER(:lenderName) AND ls.environment = :environment")
	Optional<LenderSubmerchant> findByLenderNameAndEnvironment(@Param("lenderName") String lenderName,
			@Param("environment") String environment);

}
