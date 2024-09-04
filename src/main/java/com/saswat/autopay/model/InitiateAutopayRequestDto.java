package com.saswat.autopay.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "initiate_autopay_details")
public class InitiateAutopayRequestDto {

	@Id
	@Column(name = "customer_authentication_id")
	@JsonProperty("customer_authentication_id")
	private String customer_authentication_id;

	@Column(name = "key")
	private String key;

	@Column(name = "txnid")
	private String txnid;

	@Column(name = "amount")
	private Float amount;

	@Column(name = "productinfo")
	private String productinfo;

	@Column(name = "firstname")
	private String firstname;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "surl")
	private String surl;

	@Column(name = "furl")
	private String furl;

	@Column(name = "hash")
	private String hash;

	@Column(name = "udf1")
	private String udf1;

	@Column(name = "udf2")
	private String udf2;

	@Column(name = "udf3")
	private String udf3;

	@Column(name = "udf4")
	private String udf4;

	@Column(name = "udf5")
	private Float udf5;

	@Column(name = "udf6")
	private String udf6;

	@Column(name = "udf7")
	private String udf7;

	@Column(name = "address1")
	private String address1;

	@Column(name = "address2")
	private String address2;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "country")
	private String country;

	@Column(name = "zipcode")
	private String zipcode;

	@Column(name = "final_collection_date")
	@JsonProperty("final_collection_date")
	private String final_collection_date;

	@Column(name = "max_amount")
	private Float maxAmount;

	@JsonProperty("sub_merchant_id")
	@Column(name = "sub_merchant_id")
	private String sub_merchant_id;

	public String getSub_merchant_id() {
		return sub_merchant_id;
	}

	public void setSub_merchant_id(String sub_merchant_id) {
		this.sub_merchant_id = sub_merchant_id;
	}

	public String getKey() {
		return key;
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

	public Float getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Float maxAmount) {
		this.maxAmount = maxAmount;
	}

	@Override
	public String toString() {
		return "InitiateAutopayRequestDto [customer_authentication_id=" + customer_authentication_id + ", key=" + key
				+ ", txnid=" + txnid + ", amount=" + amount + ", productinfo=" + productinfo + ", firstname="
				+ firstname + ", phone=" + phone + ", email=" + email + ", surl=" + surl + ", furl=" + furl + ", hash="
				+ hash + ", udf1=" + udf1 + ", udf2=" + udf2 + ", udf3=" + udf3 + ", udf4=" + udf4 + ", udf5=" + udf5
				+ ", udf6=" + udf6 + ", udf7=" + udf7 + ", address1=" + address1 + ", address2=" + address2 + ", city="
				+ city + ", state=" + state + ", country=" + country + ", zipcode=" + zipcode
				+ ", final_collection_date=" + final_collection_date + ", maxAmount=" + maxAmount + "]";
	}

}
