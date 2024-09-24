package com.saswat.autopay.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegisterAutopayRequestDto {

	private Float amount;

	private String productinfo;

	private String firstname;
	@Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
	@Pattern(regexp = "\\d{10}", message = "Phone number must contain only digits")
	private String phone;
	private String email;
	private String udf1;
	private Float maxAmount;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String final_collection_date;
	private String show_payment_mode;
	private String lenderName;

	public String getLenderName() {
		return lenderName;
	}

	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
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

	public String getUdf1() {
		return udf1;
	}

	public void setUdf1(String udf1) {
		this.udf1 = udf1;
	}

	public Float getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Float maxAmount) {
		this.maxAmount = maxAmount;
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

	public String getFinal_collection_date() {
		return final_collection_date;
	}

	public void setFinal_collection_date(String final_collection_date) {
		this.final_collection_date = final_collection_date;
	}

	public String getShow_payment_mode() {
		return show_payment_mode;
	}

	public void setShow_payment_mode(String show_payment_mode) {
		this.show_payment_mode = show_payment_mode;
	}

	@Override
	public String toString() {
		return "RegisterAutopayRequestDto [amount=" + amount + ", productinfo=" + productinfo + ", firstname="
				+ firstname + ", phone=" + phone + ", email=" + email + ", udf1=" + udf1 + ", maxAmount=" + maxAmount
				+ ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", zipcode=" + zipcode + ", final_collection_date=" + final_collection_date
				+ ", show_payment_mode=" + show_payment_mode + ", lenderName=" + lenderName + "]";
	}

	

}
