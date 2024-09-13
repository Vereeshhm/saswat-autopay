package com.saswat.autopay.dto;



public class TransactionStatusDto {

	private String txnid;
	private String key;
	private String hash;

	// Getters and setters
	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		return "TransactionStatusDto [txnid=" + txnid + ", key=" + key + ", hash=" + hash + "]";
	}

}

