package com.saswat.autopay.Utils;

public class TransactionStatusRequest {

	private String txnId;

	private String amount;

	private String email;

	private String phone;

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	@Override
	public String toString() {
		return "TransactionStatusRequest [txnId=" + txnId + ", amount=" + amount + ", email=" + email + ", phone="
				+ phone + "]";
	}

}
