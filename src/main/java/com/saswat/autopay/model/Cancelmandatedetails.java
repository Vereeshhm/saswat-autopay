package com.saswat.autopay.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cancel_mandate_details")
public class Cancelmandatedetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "key")
	private String key;

	@Column(name = "auto_debit_access_key")
	private String auto_debit_access_key;

	@Column(name = "customer_authentication_id")
	private String customer_authentication_id;

	@Column(name = "hash")
	private String hash;

	@Column(name = "created_date")
	private LocalDateTime created_date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

	@Column(name = "status")
	private boolean status;

	@Column(name = "easebuzz_id")
	private String easebuzz_id;

	@Column(name = "message")
	private String message;

	@Column(name = "data")
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Column(name = "error_desc")
	private String error_desc;

	public String getEasebuzz_id() {
		return easebuzz_id;
	}

	public void setEasebuzz_id(String easebuzz_id) {
		this.easebuzz_id = easebuzz_id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getAuto_debit_access_key() {
		return auto_debit_access_key;
	}

	public void setAuto_debit_access_key(String auto_debit_access_key) {
		this.auto_debit_access_key = auto_debit_access_key;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public LocalDateTime getCreated_date() {
		return created_date;
	}

	public void setCreated_date(LocalDateTime created_date) {
		this.created_date = created_date;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getError_desc() {
		return error_desc;
	}

	public void setError_desc(String error_desc) {
		this.error_desc = error_desc;
	}

	public String getCustomer_authentication_id() {
		return customer_authentication_id;
	}

	public void setCustomer_authentication_id(String customer_authentication_id) {
		this.customer_authentication_id = customer_authentication_id;
	}
}
