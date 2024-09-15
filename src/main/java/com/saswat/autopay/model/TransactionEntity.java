package com.saswat.autopay.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "transactions")
public class TransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstname;
	private String email;
	private String phone;
	private String txnid;
	private String easepayid;
	private Double amount;
	private String udf5;
	private String productInfo;
	private String hash;
	private String paymentSource;
	private String successUrl;
	private String failureUrl;
	private String status;
	@Column(name = "card_type")
	private String card_type;
	@JsonProperty("customer_authentication_id")
	@Column(name = "customer_authentication_id")
	private String customer_authentication_id;
	@Column(name = "auto_debit_access_key")
	private String auto_debit_access_key;
	@Column(name = "authorization_status")
	private String authorization_status;

	private String addedon;

	private String error;

	private String error_Message;

	public String getUdf5() {
		return udf5;
	}

	public void setUdf5(String udf5) {
		this.udf5 = udf5;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError_Message() {
		return error_Message;
	}

	public void setError_Message(String error_Message) {
		this.error_Message = error_Message;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getAddedon() {
		return addedon;
	}

	public void setAddedon(String addedon) {
		this.addedon = addedon;
	}

	private LocalDateTime txnCreatedAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

	// Getters and setters

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

	public String getAuthorization_status() {
		return authorization_status;
	}

	public void setAuthorization_status(String authorization_status) {
		this.authorization_status = authorization_status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public String getEasepayid() {
		return easepayid;
	}

	public void setEasepayid(String easepayid) {
		this.easepayid = easepayid;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPaymentSource() {
		return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getTxnCreatedAt() {
		return txnCreatedAt;
	}

	public void setTxnCreatedAt(LocalDateTime txnCreatedAt) {
		this.txnCreatedAt = txnCreatedAt;
	}
}
