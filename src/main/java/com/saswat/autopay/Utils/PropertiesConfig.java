package com.saswat.autopay.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:config/application.properties")
public class PropertiesConfig {

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

	@Value("${CancelMandate.url}")
	private String CancelMandateurl;

	@Value("${Transaction.Url}")
	private String TransactionUrl;


//	@Value("${sub_merchant_id.wcap}")
//	private String sub_merchant_idwcap;
//
//	@Value("${sub_merchant_id.ambit}")
//	private String sub_merchant_idambit;
//	
//	
//
//	public String getSub_merchant_idambit() {
//		return sub_merchant_idambit;
//	}
//
//	public void setSub_merchant_idambit(String sub_merchant_idambit) {
//		this.sub_merchant_idambit = sub_merchant_idambit;
//	}

	public String getTransactionUrl() {
		return TransactionUrl;
	}

	public void setTransactionUrl(String transactionUrl) {
		TransactionUrl = transactionUrl;
	}

	public String getCancelMandateurl() {
		return CancelMandateurl;
	}

	public void setCancelMandateurl(String cancelMandateurl) {
		CancelMandateurl = cancelMandateurl;
	}

//	public String getSub_merchant_idwcap() {
//		return sub_merchant_idwcap;
//	}
//
//	public void setSub_merchant_idwcap(String sub_merchant_idwcap) {
//		this.sub_merchant_idwcap = sub_merchant_idwcap;
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

}
