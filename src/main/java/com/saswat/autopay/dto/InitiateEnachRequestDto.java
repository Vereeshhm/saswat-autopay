package com.saswat.autopay.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InitiateEnachRequestDto {

	@JsonProperty("access_key")
	private String access_key;

	@JsonProperty("payment_mode")
	private String payment_mode;

	@JsonProperty("ifsc")
	private String ifsc;

	public String getAccess_key() {
		return access_key;
	}

	public void setAccess_key(String access_key) {
		this.access_key = access_key;
	}

	public String getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}

	public String getAuth_mode() {
		return auth_mode;
	}

	public void setAuth_mode(String auth_mode) {
		this.auth_mode = auth_mode;
	}

	public String getBank_code() {
		return bank_code;
	}

	public void setBank_code(String bank_code) {
		this.bank_code = bank_code;
	}

	@JsonProperty("account_type")
	private String account_type;

	@JsonProperty("account_no")
	private String account_no;

	@JsonProperty("auth_mode")
	private String auth_mode;

	@JsonProperty("bank_code")
	private String bank_code;

}
