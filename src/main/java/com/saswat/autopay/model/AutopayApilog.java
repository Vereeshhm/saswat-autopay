//package com.saswat.autopay.model;
//
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.PrePersist;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;


//
//@Entity
//@Table(name = "autopay_api_logss")
//public class AutopayApilog {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autopay_api_logs_seq")
//	@SequenceGenerator(name = "autopay_api_logs_seq", sequenceName = "autopay_api_logs_seq", allocationSize = 1)
//	private Long id;
//
//	private String url;
//
//
//	@Column(columnDefinition = "TEXT")
//	private String requestBody;
//
//	@Column(columnDefinition = "TEXT")
//	private String responseBody;
//
//	private int status;
//
//	private LocalDateTime timestamp;
//
//	public AutopayApilog() {
//		// Constructor logic (if any)
//	}
//
//	@PrePersist
//	protected void onCreate() {
//		this.timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
//	}
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
//	public String getUrl() {
//		return url;
//	}
//
//	public void setUrl(String url) {
//		this.url = url;
//	}
//
//	public String getRequestBody() {
//		return requestBody;
//	}
//
//	public void setRequestBody(String requestBody) {
//		this.requestBody = requestBody;
//	}
//
//	public String getResponseBody() {
//		return responseBody;
//	}
//
//	public void setResponseBody(String responseBody) {
//		this.responseBody = responseBody;
//	}
//
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}
//
//	public LocalDateTime getTimestamp() {
//		return timestamp;
//	}
//
//	public void setTimestamp(LocalDateTime timestamp) {
//		this.timestamp = timestamp;
//	}
//
//	public void setResponseBodyAsJson(String message) {
//		this.responseBody = "{\"message\": \"" + message.replace("\"", "\\\"") + "\"}";
//	}
//}
