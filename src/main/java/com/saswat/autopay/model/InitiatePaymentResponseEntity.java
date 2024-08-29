//package com.saswat.autopay.model;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//import javax.persistence.Embeddable;
//import javax.persistence.Embedded;
//
//@Entity
//@Table(name = "initiate_payment_response")
//public class InitiatePaymentResponseEntity {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID
//	@Column(name = "id", updatable = false, nullable = false)
//	private Long id;
//
//	@Embedded
//	private Result result;
//
//	// Getters and setters
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Result getResult() {
//		return result;
//	}
//
//	public void setResult(Result result) {
//		this.result = result;
//	}
//
//	@Embeddable
//	public static class Result {
//
//		@Column(name = "txnId", nullable = false)
//		private String txnId;
//
//		@Column(name = "amount", nullable = false)
//		private String amount;
//
//		@Column(name = "productInfo", nullable = false)
//		private String productInfo;
//
//		@Column(name = "firstName", nullable = false)
//		private String firstName;
//
//		@Column(name = "phone", nullable = false)
//		private String phone;
//
//		@Column(name = "email", nullable = false)
//		private String email;
//
//		@Column(name = "customerAuthenticationId", nullable = false, unique = true)
//		private String customerAuthenticationId;
//
//		@Column(name = "finalCollectionDate", nullable = false)
//		private String finalCollectionDate;
//
//		@Column(name = "maxAmount", nullable = false)
//		private String maxAmount;
//
//		@Column(name = "data", nullable = false)
//		private String data;
//
//		// Getters and setters
//
//		public String getTxnId() {
//			return txnId;
//		}
//
//		public String getMaxAmount() {
//			return maxAmount;
//		}
//
//		public void setMaxAmount(String maxAmount) {
//			this.maxAmount = maxAmount;
//		}
//
//		public String getFinalCollectionDate() {
//			return finalCollectionDate;
//		}
//
//		public void setFinalCollectionDate(String finalCollectionDate) {
//			this.finalCollectionDate = finalCollectionDate;
//		}
//
//		public void setTxnId(String txnId) {
//			this.txnId = txnId;
//		}
//
//		public String getAmount() {
//			return amount;
//		}
//
//		public void setAmount(String amount) {
//			this.amount = amount;
//		}
//
//		public String getProductInfo() {
//			return productInfo;
//		}
//
//		public void setProductInfo(String productInfo) {
//			this.productInfo = productInfo;
//		}
//
//		public String getFirstName() {
//			return firstName;
//		}
//
//		public void setFirstName(String firstName) {
//			this.firstName = firstName;
//		}
//
//		public String getPhone() {
//			return phone;
//		}
//
//		public void setPhone(String phone) {
//			this.phone = phone;
//		}
//
//		public String getEmail() {
//			return email;
//		}
//
//		public void setEmail(String email) {
//			this.email = email;
//		}
//
//		public String getCustomerAuthenticationId() {
//			return customerAuthenticationId;
//		}
//
//		public void setCustomerAuthenticationId(String customerAuthenticationId) {
//			this.customerAuthenticationId = customerAuthenticationId;
//		}
//
//		public String getData() {
//			return data;
//		}
//
//		public void setData(String data) {
//			this.data = data;
//		}
//	}
//}
