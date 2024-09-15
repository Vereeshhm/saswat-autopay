package com.saswat.autopay.dto;

public class CancelMandateDto {

	private String key;
	private String easebuzz_id;
	private String customer_authentication_id;
	private String auto_debit_access_key;

	private String hash;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getEasebuzz_id() {
		return easebuzz_id;
	}

	public void setEasebuzz_id(String easebuzz_id) {
		this.easebuzz_id = easebuzz_id;
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

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		return "CancelMandateDto [key=" + key + ", easebuzz_id=" + easebuzz_id + ", customer_authentication_id="
				+ customer_authentication_id + ", auto_debit_access_key=" + auto_debit_access_key + ", hash=" + hash
				+ "]";
	}
	

}
