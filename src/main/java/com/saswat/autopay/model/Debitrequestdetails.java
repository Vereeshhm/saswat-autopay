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
@Table(name = "debit_request_details")
public class Debitrequestdetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "debit_response_details_seq")
    @SequenceGenerator(name = "debit_response_details_seq", sequenceName = "debit_response_details_seq", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "status")
	private Integer status;

	@Column(name = "data")
	private String data;

	@Column(name = "txnid")
	private String txnid;
	
	@Column(name="firstname")
	private String firstname;
	@Column(name="phone")
	private String phone;
	@Column(name="productinfo")
	private String productinfo;
	
	@Column(name="email")
	private String email; 
	
	@JsonProperty("customer_authentication_id")
	@Column(name="customer_authentication_id")
	private String customer_authentication_id;
	
	@Column(name="auto_debit_access_key")
	@JsonProperty("auto_debit_access_key")
	private String auto_debit_access_key;

	@Column(name = "merchant_debit_id")
	@JsonProperty("merchant_debit_id")
	private String merchant_debit_id;
	
	@Column(name="created_date")
	private LocalDateTime created_date=LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	
	@Column(name="debit_status")
	private String debitStatus;

	@Column(name="created_by")
	private String created_by="Admin";

	@Column(name="hash")
	private String hash;
	
	@Column(name = "max_amount")
	private Float maxAmount;
	
	@Column(name="errorDesc")
	private String errorDesc;
	
	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public Float getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Float maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProductinfo() {
		return productinfo;
	}

	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomer_authentication_id() {
		return customer_authentication_id;
	}

	public void setCustomer_authentication_id(String customer_authentication_id) {
		this.customer_authentication_id = customer_authentication_id;
	}

	public String getAuto_debit_access_key() {
		return auto_debit_access_key;
	}

	public void setAuto_debit_access_key(String auto_debit_access_key) {
		this.auto_debit_access_key = auto_debit_access_key;
	}

	public LocalDateTime getCreated_date() {
		return created_date;
	}

	public void setCreated_date(LocalDateTime created_date) {
		this.created_date = created_date;
	}

	public String getDebitStatus() {
		return debitStatus;
	}

	public void setDebitStatus(String debitStatus) {
		this.debitStatus = debitStatus;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

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

	@Override
	public String toString() {
		return "Debitrequestdetails [id=" + id + ", status=" + status + ", data=" + data + ", txnid=" + txnid
				+ ", firstname=" + firstname + ", phone=" + phone + ", productinfo=" + productinfo + ", email=" + email
				+ ", customer_authentication_id=" + customer_authentication_id + ", auto_debit_access_key="
				+ auto_debit_access_key + ", merchant_debit_id=" + merchant_debit_id + ", created_date=" + created_date
				+ ", debitStatus=" + debitStatus + ", created_by=" + created_by + ", hash=" + hash + "]";
	}

}
