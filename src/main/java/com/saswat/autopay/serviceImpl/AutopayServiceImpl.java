//package com.saswat.autopay.serviceImpl;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.HttpServerErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.saswat.autopay.Utils.CancelRequest;
//import com.saswat.autopay.Utils.DebitRequest;
//import com.saswat.autopay.Utils.InitiateRequest;
//import com.saswat.autopay.Utils.PropertiesConfig;
//import com.saswat.autopay.Utils.TransactionStatusRequest;
//import com.saswat.autopay.model.AutopayApilog;
//import com.saswat.autopay.model.DebitPaymentResponseEntity;
//import com.saswat.autopay.model.InitiatePaymentResponseEntity;
//import com.saswat.autopay.repository.AutopayApiLogRepository;
//import com.saswat.autopay.repository.DebitPaymentResponseRepository;
//import com.saswat.autopay.repository.InitiatePaymentResponseRepository;
//import com.saswat.autopay.service.AutopayService;
//
//@Service
//public class AutopayServiceImpl implements AutopayService {
//
//	private final RestTemplate restTemplate;
//	private final String token;
//
//	private static final Logger logger = LoggerFactory.getLogger(AutopayServiceImpl.class);
//
//	@Autowired
//	private AutopayApiLogRepository apiLogRepository;
//
//	@Autowired
//	private InitiatePaymentResponseRepository initiatePaymentResponseRepository;
//
//	@Autowired
//	DebitPaymentResponseRepository debitPaymentResponseRepository;
//
//	@Autowired
//	private PropertiesConfig config;
//
//	public AutopayServiceImpl(RestTemplate restTemplate, @Value("${token}") String token) {
//		this.restTemplate = restTemplate;
//		this.token = token;
//
//	}
//
//	private ResponseEntity<String> handleResponse(String url, String requestBody, String responseBody,
//			HttpStatus defaultStatus) throws Exception {
//		ObjectMapper mapper = new ObjectMapper();
//		JsonNode rootNode = mapper.readTree(responseBody);
//		HttpStatus statusToLog = defaultStatus;
//
//		if (rootNode.has("error")) {
//			JsonNode errorNode = rootNode.get("error");
//
//			if (errorNode.has("status")) {
//				int statusCode = errorNode.get("status").asInt();
//				statusToLog = HttpStatus.valueOf(statusCode);
//			}
//		}
//
//		logApi(url, requestBody, responseBody, statusToLog);
//
//		return ResponseEntity.status(statusToLog).body(responseBody);
//	}
//
//	public void logApi(String url, String requestBody, String responseBody, HttpStatus status) {
//		AutopayApilog apiLog = new AutopayApilog();
//
//		apiLog.setUrl(url);
//		apiLog.setRequestBody(requestBody);
//		apiLog.setResponseBody(responseBody);
//		apiLog.setStatus(status.value()); // Status will now be correctly set
//		apiLog.setTimestamp(LocalDateTime.now());
//		apiLogRepository.save(apiLog);
//
//		logger.info("API: {}, Status: {}, Request: {}, Response: {}", url, status.value(), requestBody, responseBody);
//	}
//
//	@Override
//	public ResponseEntity<String> fetchInitiate(InitiateRequest initiateRequest) throws JsonProcessingException {
//		ObjectMapper mapper = new ObjectMapper();
//		String url = config.getInitiateUrl();
//
//		try {
//			HttpHeaders headers = new HttpHeaders();
//
//			initiateRequest.setSuccessRedirectUrl(config.getSuccessRedirectUrl());
//			initiateRequest.setFailureRedirectUrl(config.getFailureRedirectUrl());
//			initiateRequest.setCallbackUrl(config.getCallbackUrl());
//			initiateRequest.setSubMerchantId(config.getSubMerchantId());
//			headers.set("Authorization", token);
//			headers.set("Content-Type", "application/json");
//			// headers.set("x-client-unique-id", config.getX_client_unique_id());
//
//			HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(initiateRequest), headers);
//
//			String response = restTemplate.postForObject(url, requestEntity, String.class);
//
//			if (response.contains("error")) {
//				logApi(url, initiateRequest.toString(), response, HttpStatus.BAD_REQUEST);
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//			} else {
//
//				JsonNode responseNode = mapper.readTree(response);
//				JsonNode resultNode = responseNode.path("result");
//
//				((ObjectNode) resultNode).put("finalCollectionDate", initiateRequest.getFinalCollectionDate());
//				((ObjectNode) resultNode).put("maxAmount", initiateRequest.getMaxAmount());
//
//				String modifiedResponse = responseNode.toString();
//
//				InitiatePaymentResponseEntity initiatePaymentResponse = mapper.readValue(modifiedResponse,
//						InitiatePaymentResponseEntity.class);
//				String customerAuthenticationId = initiatePaymentResponse.getResult().getCustomerAuthenticationId();
//
//				if (customerAuthenticationId != null && !customerAuthenticationId.isEmpty()) {
//
//					InitiatePaymentResponseEntity.Result result = new InitiatePaymentResponseEntity.Result();
//					result.setTxnId(initiatePaymentResponse.getResult().getTxnId());
//					result.setAmount(initiatePaymentResponse.getResult().getAmount());
//					result.setProductInfo(initiatePaymentResponse.getResult().getProductInfo());
//					result.setFirstName(initiatePaymentResponse.getResult().getFirstName());
//					result.setPhone(initiatePaymentResponse.getResult().getPhone());
//					result.setEmail(initiatePaymentResponse.getResult().getEmail());
//					result.setFinalCollectionDate(initiateRequest.getFinalCollectionDate());
//					result.setMaxAmount(initiateRequest.getMaxAmount());
//					result.setCustomerAuthenticationId(customerAuthenticationId);
//
//					result.setData(initiatePaymentResponse.getResult().getData());
//
//					InitiatePaymentResponseEntity entity = new InitiatePaymentResponseEntity();
//					entity.setResult(result);
//
//					initiatePaymentResponseRepository.save(entity);
//
//					logger.info("Modified response body: " + modifiedResponse);
//					logger.info("Extracted CustomerAuthenticationId: " + customerAuthenticationId);
//					logApi(url, initiateRequest.toString(), modifiedResponse, HttpStatus.OK);
//
//					return ResponseEntity.ok(modifiedResponse);
//				} else {
//					logger.warn("customerAuthenticationId is null or empty, skipping transaction save.");
//					return ResponseEntity.badRequest().body("Failure: customerAuthenticationId is null or empty");
//				}
//			}
//
//		} catch (HttpClientErrorException | HttpServerErrorException e) {
//			String responseBody = e.getResponseBodyAsString() != null ? e.getResponseBodyAsString()
//					: "No response body";
//			logApi(url, initiateRequest.toString(), responseBody, e.getStatusCode());
//			return ResponseEntity.status(e.getStatusCode()).body(responseBody);
//		} catch (Exception e) {
//			logApi(url, initiateRequest.toString(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failure: " + e.getMessage());
//		}
//	}
//
//	@Override
//	public ResponseEntity<String> fetchDebitRequest(DebitRequest debitRequest) {
//		String url = config.getDebitUrl();
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//
//			String customerAuthenticationId = debitRequest.getCustomerAuthenticationId();
//			if (customerAuthenticationId == null || customerAuthenticationId.isEmpty()) {
//				throw new IllegalArgumentException("customerAuthenticationId must not be null or empty");
//			}
//
//			InitiatePaymentResponseEntity initiatePaymentResponseEntity = initiatePaymentResponseRepository
//					.findByCustomerAuthenticationId(customerAuthenticationId);
//
//			if (initiatePaymentResponseEntity == null) {
//
//				String logMessage = "No records found for the provided customerAuthenticationId "
//						+ customerAuthenticationId;
//				logger.warn(logMessage);
//				logApi(url, debitRequest.toString(), logMessage, HttpStatus.BAD_REQUEST);
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logMessage);
//
//			}
//
//			InitiatePaymentResponseEntity.Result result = initiatePaymentResponseEntity.getResult();
//
//			debitRequest.getAmount();
//			logger.info("Extracted productinfo " + result.getProductInfo());
//			logger.info("Extracted firstname  " + result.getFirstName());
//
//			logger.info("Extracted phone " + result.getPhone());
//			logger.info("Extracted email " + result.getEmail());
//			logger.info("Extracted maxamount " + result.getMaxAmount());
//			logger.info("Extracted finalcollectiondate " + result.getFinalCollectionDate());
//
//			debitRequest.setProductInfo(result.getProductInfo());
//			debitRequest.setFirstName(result.getFirstName());
//			debitRequest.setPhone(result.getPhone());
//			debitRequest.setEmail(result.getEmail());
//			debitRequest.setMaxAmount(result.getMaxAmount());
//			debitRequest.setFinalCollectionDate(result.getFinalCollectionDate());
//
//			HttpHeaders headers = new HttpHeaders();
//			debitRequest.setSuccessRedirectUrl(config.getSuccessRedirectUrl());
//			debitRequest.setFailureRedirectUrl(config.getFailureRedirectUrl());
//			debitRequest.setCallbackUrl(config.getCallbackUrl());
//			debitRequest.setSubMerchantId(config.getSubMerchantId());
//			headers.set("Authorization", token);
//			headers.set("Content-Type", "application/json");
//
//			// headers.set("x-client-unique-id", config.getX_client_unique_id());
//
//			HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(debitRequest), headers);
//
//			String response = restTemplate.postForObject(url, requestEntity, String.class);
//
//			if (response.contains("error")) {
//
//				logApi(url, debitRequest.toString(), response, HttpStatus.BAD_REQUEST); // Assuming a bad request in
//																						// case of error
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//			}
//
//			else {
//				DebitPaymentResponseEntity debitPaymentResponse = mapper.readValue(response,
//						DebitPaymentResponseEntity.class);
//				String txnId = debitPaymentResponse.getResult().getTxnId();
//
//				if (txnId != null && !txnId.isEmpty()) {
//
//					DebitPaymentResponseEntity.Result debitresult = new DebitPaymentResponseEntity.Result();
//
//					debitresult.setTxnId(debitPaymentResponse.getResult().getTxnId());
//					debitresult.setAmount(debitPaymentResponse.getResult().getAmount());
//
//					debitresult.setProductInfo(debitPaymentResponse.getResult().getProductInfo());
//					debitresult.setFirstName(debitPaymentResponse.getResult().getFirstName());
//					debitresult.setPhone(debitPaymentResponse.getResult().getPhone());
//					debitresult.setEmail(debitPaymentResponse.getResult().getEmail());
//					debitresult.setCustomerAuthenticationId(
//							debitPaymentResponse.getResult().getCustomerAuthenticationId());
//					debitresult.setAutoDebitAccessKey(debitPaymentResponse.getResult().getAutoDebitAccessKey());
//					debitresult.setSignzyId(debitPaymentResponse.getResult().getSignzyId());
//					debitresult.setMerchantDebitId(debitPaymentResponse.getResult().getMerchantDebitId());
//
//					debitresult.setData(debitPaymentResponse.getResult().getData());
//
//					DebitPaymentResponseEntity entity = new DebitPaymentResponseEntity();
//					entity.setResult(debitresult);
//
//					debitPaymentResponseRepository.save(entity);
//
//					logger.info("Response body: " + response);
//					logger.info("Extracted txnId: " + txnId);
//
//					logApi(url, debitRequest.toString(), response, HttpStatus.OK);
//
//					return ResponseEntity.ok(response);
//
//				} else {
//					logger.warn("txnId is null or empty, skipping transaction save.");
//					return ResponseEntity.badRequest().body("Failure: customerAuthenticationId is null or empty");
//				}
//
//			}
//		}
//
//		catch (HttpClientErrorException | HttpServerErrorException e) {
//			String responseBody = e.getResponseBodyAsString() != null ? e.getResponseBodyAsString()
//					: "No response body";
//			HttpStatus statusCode = e.getStatusCode() != null ? (HttpStatus) e.getStatusCode()
//					: HttpStatus.INTERNAL_SERVER_ERROR;
//			logApi(url, debitRequest.toString(), responseBody, statusCode);
//			return ResponseEntity.status(statusCode).body("Failure: " + responseBody);
//		} catch (Exception e) {
//			logApi(url, debitRequest.toString(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failure: " + e.getMessage());
//		}
//	}
//
//	@Override
//	public ResponseEntity<String> fetchTransactionstatus(TransactionStatusRequest request) {
//
//		ObjectMapper mapper = new ObjectMapper();
//		String url = config.getTransactionUrl();
//		try {
//
//			String txnId = request.getTxnId();
//			if (txnId == null || txnId.isEmpty()) {
//				throw new IllegalArgumentException("txnId must not be null or empty");
//			}
//
//			DebitPaymentResponseEntity debitPaymentResponseEntity = debitPaymentResponseRepository.findByTxnId(txnId);
//
//			if (debitPaymentResponseEntity == null) {
//				String logMessage = "No records found for the provided txnId :" + txnId;
//				logger.warn(logMessage);
//				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logMessage);
//			}
//
//			DebitPaymentResponseEntity.Result result = debitPaymentResponseEntity.getResult();
//
//			logger.info("Extracted amount: " + result.getAmount());
//			logger.info("Extracted email: " + result.getEmail());
//			logger.info("Extracted phone: " + result.getPhone());
//			request.setAmount(result.getAmount());
//			request.setEmail(result.getEmail());
//			request.setPhone(result.getPhone());
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.set("Authorization", token);
//			headers.set("Content-Type", "application/json");
//			// headers.set("x-client-unique-id", config.getX_client_unique_id());
//
//			HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(request), headers);
//
//			String response = restTemplate.postForObject(url, requestEntity, String.class);
//
//			logger.info("Response body: " + response);
//			return handleResponse(url, new ObjectMapper().writeValueAsString(request), response, HttpStatus.OK);
//		} catch (HttpClientErrorException | HttpServerErrorException e) {
//			String responseBody = e.getResponseBodyAsString() != null ? e.getResponseBodyAsString()
//					: "No response body";
//			HttpStatus statusCode = e.getStatusCode() != null ? (HttpStatus) e.getStatusCode()
//					: HttpStatus.INTERNAL_SERVER_ERROR;
//			logApi(url, request.toString(), responseBody, statusCode);
//			return ResponseEntity.status(statusCode).body("Failure: " + responseBody);
//		} catch (Exception e) {
//			logApi(url, request.toString(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failure: " + e.getMessage());
//		}
//	}
//
//	@Override
//	public ResponseEntity<String> fetchCancelrequest(CancelRequest cancelRequest) {
//		ObjectMapper mapper = new ObjectMapper();
//		String url = config.getCancelUrl();
//		try {
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.set("Authorization", token);
//			headers.set("Content-Type", "application/json");
//			// headers.set("x-client-unique-id", config.getX_client_unique_id());
//
//			HttpEntity<String> requestEntity = new HttpEntity<>(mapper.writeValueAsString(cancelRequest), headers);
//
//			String response = restTemplate.postForObject(url, requestEntity, String.class);
//
//			logger.info("Response body: " + response);
//
//			Map<String, Object> responseMap = mapper.readValue(response, new TypeReference<Map<String, Object>>() {
//			});
//
//			// Check and add the "message" field if it doesn't exist
//			if (!responseMap.containsKey("message")) {
//				responseMap.put("message", "Mandate cancelled successfully.");
//			}
//
//			// Serialize back to JSON string
//
//			String modifiedResponse = mapper.writeValueAsString(responseMap);
//
//			// Return the modified response
//			return handleResponse(url, requestEntity.getBody(), modifiedResponse, HttpStatus.OK);
//		} catch (HttpClientErrorException | HttpServerErrorException e) {
//			String responseBody = e.getResponseBodyAsString() != null ? e.getResponseBodyAsString()
//					: "No response body";
//			HttpStatus statusCode = e.getStatusCode() != null ? (HttpStatus) e.getStatusCode()
//					: HttpStatus.INTERNAL_SERVER_ERROR;
//			logApi(url, cancelRequest.toString(), responseBody, statusCode);
//			return ResponseEntity.status(statusCode).body("Failure: " + responseBody);
//		} catch (Exception e) {
//			logApi(url, cancelRequest.toString(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failure: " + e.getMessage());
//		}
//	}
//
//}
