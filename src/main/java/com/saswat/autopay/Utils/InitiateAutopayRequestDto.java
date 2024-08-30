package com.saswat.autopay.Utils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InitiateAutopayRequestDto {

	private String key;
	private String txnid;
	private Float amount;
	private String productinfo;
	private String firstname;
	private String phone;
	private String email;
	private String surl;
	private String furl;
	private String hash;
	private String udf1;
	private String udf2;
	private String udf3;
	private String udf4;
	private Float udf5;
	private String udf6;
	private String udf7;

	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	@JsonProperty("customer_authentication_id")
	private String customer_authentication_id;
	@JsonProperty("final_collection_date")
	private String final_collection_date;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTxnid() {
		return txnid;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getProductinfo() {
		return productinfo;
	}

	public void setProductinfo(String productinfo) {
		this.productinfo = productinfo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSurl() {
		return surl;
	}

	public void setSurl(String surl) {
		this.surl = surl;
	}

	public String getFurl() {
		return furl;
	}

	public void setFurl(String furl) {
		this.furl = furl;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
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

	public Float getUdf5() {
		return udf5;
	}

	public void setUdf5(Float udf5) {
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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCustomer_authentication_id() {
		return customer_authentication_id;
	}

	public void setCustomer_authentication_id(String customer_authentication_id) {
		this.customer_authentication_id = customer_authentication_id;
	}



	public String getFinal_collection_date() {
		return final_collection_date;
	}

	public void setFinal_collection_date(String final_collection_date) {
		this.final_collection_date = final_collection_date;
	}

	@Override
	public String toString() {
		return "InitiateAutopayRequestDto [key=" + key + ", txnid=" + txnid + ", amount=" + amount + ", productinfo="
				+ productinfo + ", firstname=" + firstname + ", phone=" + phone + ", email=" + email + ", surl=" + surl
				+ ", furl=" + furl + ", hash=" + hash + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3
				+ ", udf4=" + udf4 + ", udf5=" + udf5 + ", udf6=" + udf6 + ", udf7=" + udf7 + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", zipcode=" + zipcode + ", customer_authentication_id=" + customer_authentication_id
				+ ", final_collection_date=" + final_collection_date + "]";
	}



}
