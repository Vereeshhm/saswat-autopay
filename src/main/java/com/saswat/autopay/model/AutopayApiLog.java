package com.saswat.autopay.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name="autopay_api_logs")
public class AutopayApiLog {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "autopay_api_logs_seq")
	@SequenceGenerator(name = "autopay_api_logs_seq", sequenceName = "autopay_api_logs_seq", allocationSize = 1)
	private Long id;

	private String url;

	@Column(columnDefinition = "TEXT")
	private String requestBody;

	@Column(columnDefinition = "TEXT")
	private String responseBody;

	private int statusCode;
	@Column(name="created_date")
	private LocalDateTime created_date=LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	
	@Column(name="status")
	private String status;

	@Column(name="created_by")
	private String created_by="Admin";
	
	@Column(name="api_type")
	private String apiType;
	
	public String getApiType() {
		return apiType;
	}


	public void setApiType(String apiType) {
		this.apiType = apiType;
	}


	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


	public LocalDateTime getCreated_date() {
		return created_date;
	}


	public void setCreated_date(LocalDateTime created_date) {
		this.created_date = created_date;
	}


	public String getCreated_by() {
		return created_by;
	}


	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public AutopayApiLog() {
		// Constructor logic (if any)
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}


	public String getStatus() {
		return status;
	}


	@Override
	public String toString() {
		return "AutopayApiLog [id=" + id + ", url=" + url + ", requestBody=" + requestBody + ", responseBody="
				+ responseBody + ", statusCode=" + statusCode + ", created_date=" + created_date + ", status=" + status
				+ ", created_by=" + created_by + ", apiType=" + apiType + "]";
	}

	
	
	
}
