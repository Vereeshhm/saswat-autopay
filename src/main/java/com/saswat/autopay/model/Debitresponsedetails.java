package com.saswat.autopay.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "debit_response_details")
public class Debitresponsedetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "debit_response_details_seq")
    @SequenceGenerator(name = "debit_response_details_seq", sequenceName = "debit_response_details_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id; // You should have a primary key in the entity

	@Column(name = "status")
	private Integer status;

	@Column(name = "data")
	private String data;

	@Column(name = "txnid")
	private String txnid;

	@Column(name = "merchant_debit_id")
	@JsonProperty("merchant_debit_id")
	private String merchant_debit_id;
	
	@Column(name="created_date")
	private LocalDateTime created_date=LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	
	@Column(name="debit_status")
	private String debitStatus;

	@Column(name="created_by")
	private String created_by="Admin";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public String getMerchant_debit_id() {
		return merchant_debit_id;
	}

	public void setMerchant_debit_id(String merchant_debit_id) {
		this.merchant_debit_id = merchant_debit_id;
	}

}
