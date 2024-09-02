package com.saswat.autopay.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "transaction_status")
public class Transactionstatus {

	@Id
	@JsonProperty("txnid")
	private String txnid;

	@JsonProperty("deduction_percentage")
	private Double deductionPercentage;

	@JsonProperty("net_amount_debit")
	private Double netAmountDebit;

	@JsonProperty("cardCategory")
	private String cardCategory;

	@JsonProperty("unmappedstatus")
	private String unmappedStatus;

	@JsonProperty("addedon")
	private LocalDateTime addedOn;

	@JsonProperty("auto_debit_auth_error")
	private String autoDebitAuthError;

	@JsonProperty("cash_back_percentage")
	private Double cashBackPercentage;

	@JsonProperty("bank_ref_num")
	private String bankRefNum;

	@JsonProperty("error_Message")
	private String errorMessage;

	@JsonProperty("authorization_status")
	private String authorizationStatus;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("easepayid")
	private String easepayid;

	@JsonProperty("cardnum")
	private String cardNum;

	@JsonProperty("auto_debit_access_key")
	private String autoDebitAccessKey;

	@JsonProperty("upi_va")
	private String upiVa;

	@JsonProperty("payment_source")
	private String paymentSource;

	@JsonProperty("card_type")
	private String cardType;

	@JsonProperty("mode")
	private String mode;

	@JsonProperty("error")
	private String error;

	@JsonProperty("bankcode")
	private String bankCode;

	@JsonProperty("name_on_card")
	private String nameOnCard;

	@JsonProperty("bank_name")
	private String bankName;

	@JsonProperty("issuing_bank")
	private String issuingBank;

	@JsonProperty("customer_authentication_id")
	private String customerAuthenticationId;

	@JsonProperty("PG_TYPE")
	private String pgType;

	@JsonProperty("amount")
	private Double amount;

	@JsonProperty("furl")
	private String failureUrl;

	@JsonProperty("productinfo")
	private String productInfo;

	@JsonProperty("auth_code")
	private String authCode;

	@JsonProperty("email")
	private String email;

	@JsonProperty("status")
	private String status;

	@JsonProperty("hash")
	private String hash;

	@JsonProperty("firstname")
	private String firstname;

	@JsonProperty("surl")
	private String successUrl;

	@JsonProperty("auto_debit_auth_msg")
	private String autoDebitAuthMsg;

	@JsonProperty("key")
	private String key;

	@JsonProperty("merchant_logo")
	private String merchantLogo;

	@JsonProperty("udf1")
	private String udf1;

	@JsonProperty("udf2")
	private String udf2;

	@JsonProperty("udf3")
	private String udf3;

	@JsonProperty("udf4")
	private String udf4;

	@JsonProperty("udf5")
	private Double udf5;

	@JsonProperty("udf6")
	private String udf6;

	@JsonProperty("udf7")
	private String udf7;

	@JsonProperty("udf8")
	private String udf8;

	@JsonProperty("udf9")
	private String udf9;

	@JsonProperty("udf10")
	private String udf10;

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public Double getDeductionPercentage() {
		return deductionPercentage;
	}

	public void setDeductionPercentage(Double deductionPercentage) {
		this.deductionPercentage = deductionPercentage;
	}

	public Double getNetAmountDebit() {
		return netAmountDebit;
	}

	public void setNetAmountDebit(Double netAmountDebit) {
		this.netAmountDebit = netAmountDebit;
	}

	public String getCardCategory() {
		return cardCategory;
	}

	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}

	public String getUnmappedStatus() {
		return unmappedStatus;
	}

	public void setUnmappedStatus(String unmappedStatus) {
		this.unmappedStatus = unmappedStatus;
	}

	public LocalDateTime getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(LocalDateTime addedOn) {
		this.addedOn = addedOn;
	}

	public String getAutoDebitAuthError() {
		return autoDebitAuthError;
	}

	public void setAutoDebitAuthError(String autoDebitAuthError) {
		this.autoDebitAuthError = autoDebitAuthError;
	}

	public Double getCashBackPercentage() {
		return cashBackPercentage;
	}

	public void setCashBackPercentage(Double cashBackPercentage) {
		this.cashBackPercentage = cashBackPercentage;
	}

	public String getBankRefNum() {
		return bankRefNum;
	}

	public void setBankRefNum(String bankRefNum) {
		this.bankRefNum = bankRefNum;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getAuthorizationStatus() {
		return authorizationStatus;
	}

	public void setAuthorizationStatus(String authorizationStatus) {
		this.authorizationStatus = authorizationStatus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEasepayid() {
		return easepayid;
	}

	public void setEasepayid(String easepayid) {
		this.easepayid = easepayid;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getAutoDebitAccessKey() {
		return autoDebitAccessKey;
	}

	public void setAutoDebitAccessKey(String autoDebitAccessKey) {
		this.autoDebitAccessKey = autoDebitAccessKey;
	}

	public String getUpiVa() {
		return upiVa;
	}

	public void setUpiVa(String upiVa) {
		this.upiVa = upiVa;
	}

	public String getPaymentSource() {
		return paymentSource;
	}

	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIssuingBank() {
		return issuingBank;
	}

	public void setIssuingBank(String issuingBank) {
		this.issuingBank = issuingBank;
	}

	public String getCustomerAuthenticationId() {
		return customerAuthenticationId;
	}

	public void setCustomerAuthenticationId(String customerAuthenticationId) {
		this.customerAuthenticationId = customerAuthenticationId;
	}

	public String getPgType() {
		return pgType;
	}

	public void setPgType(String pgType) {
		this.pgType = pgType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getFailureUrl() {
		return failureUrl;
	}

	public void setFailureUrl(String failureUrl) {
		this.failureUrl = failureUrl;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getAutoDebitAuthMsg() {
		return autoDebitAuthMsg;
	}

	public void setAutoDebitAuthMsg(String autoDebitAuthMsg) {
		this.autoDebitAuthMsg = autoDebitAuthMsg;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMerchantLogo() {
		return merchantLogo;
	}

	public void setMerchantLogo(String merchantLogo) {
		this.merchantLogo = merchantLogo;
	}

	public String getUdf1() {
		return udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public String getUdf2() {
		return udf2;
	}

	public void setUdf2(String udf2) {
		this.udf2 = udf2;
	}

	public String getUdf3() {
		return udf3;
	}

	public void setUdf3(String udf3) {
		this.udf3 = udf3;
	}

	public String getUdf4() {
		return udf4;
	}

	public void setUdf4(String udf4) {
		this.udf4 = udf4;
	}

	public Double getUdf5() {
		return udf5;
	}

	public void setUdf5(Double udf5) {
		this.udf5 = udf5;
	}

	public String getUdf6() {
		return udf6;
	}

	public void setUdf6(String udf6) {
		this.udf6 = udf6;
	}

	public String getUdf7() {
		return udf7;
	}

	public void setUdf7(String udf7) {
		this.udf7 = udf7;
	}

	public String getUdf8() {
		return udf8;
	}

	public void setUdf8(String udf8) {
		this.udf8 = udf8;
	}

	public String getUdf9() {
		return udf9;
	}

	public void setUdf9(String udf9) {
		this.udf9 = udf9;
	}

	public String getUdf10() {
		return udf10;
	}

	public void setUdf10(String udf10) {
		this.udf10 = udf10;
	}

}
