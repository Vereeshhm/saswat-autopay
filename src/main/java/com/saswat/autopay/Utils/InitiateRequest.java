package com.saswat.autopay.Utils;

public class InitiateRequest {

	private String firstName;

	private String amount;

	private String productInfo;

	private String phone;

	private String email;

	private String maxAmount;

	private String finalCollectionDate;

	private String subMerchantId;

	private String successRedirectUrl;

	private String failureRedirectUrl;

	private String callbackUrl;

	private String address1;

	private String address2;

	private String city;

	private String state;

	private String country;

	private String zipcode;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
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

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
	}

	public String getFinalCollectionDate() {
		return finalCollectionDate;
	}

	public void setFinalCollectionDate(String finalCollectionDate) {
		this.finalCollectionDate = finalCollectionDate;
	}

	public String getSubMerchantId() {
		return subMerchantId;
	}

	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
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

	public String getSuccessRedirectUrl() {
		return successRedirectUrl;
	}

	public void setSuccessRedirectUrl(String successRedirectUrl) {
		this.successRedirectUrl = successRedirectUrl;
	}

	public String getFailureRedirectUrl() {
		return failureRedirectUrl;
	}

	public void setFailureRedirectUrl(String failureRedirectUrl) {
		this.failureRedirectUrl = failureRedirectUrl;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	@Override
	public String toString() {
		return "InitiateRequest [firstName=" + firstName + ", amount=" + amount + ", productInfo=" + productInfo
				+ ", phone=" + phone + ", email=" + email + ", maxAmount=" + maxAmount + ", finalCollectionDate="
				+ finalCollectionDate + ", subMerchantId=" + subMerchantId + ", successRedirectUrl="
				+ successRedirectUrl + ", failureRedirectUrl=" + failureRedirectUrl + ", callbackUrl=" + callbackUrl
				+ ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state
				+ ", country=" + country + ", zipcode=" + zipcode + "]";
	}

}
