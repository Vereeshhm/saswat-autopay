package com.saswat.autopay.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:config/application.properties")
public class PropertiesConfig {

	@Value("${InitiateUrl}")
	private String InitiateUrl;
	@Value("${DebitUrl}")
	private String DebitUrl;

	@Value("${TransactionUrl}")
	private String TransactionUrl;

	@Value("${CancelUrl}")
	private String CancelUrl;

	@Value("${successRedirectUrl}")
	private String successRedirectUrl;

	@Value("${failureRedirectUrl}")
	private String failureRedirectUrl;

	@Value("${callbackUrl}")
	private String callbackUrl;

	@Value("${subMerchantId}")
	private String subMerchantId;
	
	@Value("${Initiatelink.url}")
	private String Initiatelinkurl;
	
	
	@Value("${key}")
	private String key;

	@Value("${salt}")
	private String salt;

	@Value("${successurl}")
	private String surl;

	@Value("${failureurl}")
	private String furl;
	
	@Value("${Payment.Url}")
	private String PaymentUrl;
	
	@Value("${DebitRequest.url}")
	private String DebitRequesturl;

//	@Value("${x-client-unique-id}")
//	private String x_client_unique_id;
//
//	public String getX_client_unique_id() {
//		return x_client_unique_id;
//	}
//
//	public void setX_client_unique_id(String x_client_unique_id) {
//		this.x_client_unique_id = x_client_unique_id;
//	}

	public String getPaymentUrl() {
		return PaymentUrl;
	}

	public String getDebitRequesturl() {
		return DebitRequesturl;
	}

	public void setDebitRequesturl(String debitRequesturl) {
		DebitRequesturl = debitRequesturl;
	}

	public void setPaymentUrl(String paymentUrl) {
		PaymentUrl = paymentUrl;
	}

	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public String getInitiatelinkurl() {
		return Initiatelinkurl;
	}

	public void setInitiatelinkurl(String initiatelinkurl) {
		Initiatelinkurl = initiatelinkurl;
	}

	public String getCancelUrl() {
		return CancelUrl;
	}

	public void setCancelUrl(String cancelUrl) {
		CancelUrl = cancelUrl;
	}

	public String getTransactionUrl() {
		return TransactionUrl;
	}

	public void setTransactionUrl(String transactionUrl) {
		TransactionUrl = transactionUrl;
	}

	public String getDebitUrl() {
		return DebitUrl;
	}

	public void setDebitUrl(String debitUrl) {
		DebitUrl = debitUrl;
	}

	public String getInitiateUrl() {
		return InitiateUrl;
	}

	public void setInitiateUrl(String initiateUrl) {
		InitiateUrl = initiateUrl;
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

	public String getSubMerchantId() {
		return subMerchantId;
	}

	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
	}

}
