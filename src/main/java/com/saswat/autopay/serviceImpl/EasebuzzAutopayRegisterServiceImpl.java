package com.saswat.autopay.serviceImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.saswat.autopay.Utils.PropertiesConfig;
import com.saswat.autopay.dto.DebitAutopayRequestDto;
import com.saswat.autopay.model.AutopayApiLog;
import com.saswat.autopay.model.Debitrequestdetails;
import com.saswat.autopay.model.InitiateAutopayRequestDto;
import com.saswat.autopay.repository.AutopayApilogrepository;
import com.saswat.autopay.repository.DebitRequestRepository;
import com.saswat.autopay.repository.InitiateAutopayRepository;
import com.saswat.autopay.service.EasebuzzAutopayRegisterService;

@Service
public class EasebuzzAutopayRegisterServiceImpl implements EasebuzzAutopayRegisterService {

	@Autowired
	PropertiesConfig config;

	@Autowired
	AutopayApilogrepository autopayapilogrepository;

	@Autowired
	InitiateAutopayRepository initiateAutopayRepository;

	@Autowired
	DebitRequestRepository debitRequestRepository;

	private Key secretKey;

	private static final Logger logger = LoggerFactory.getLogger(EasebuzzAutopayRegisterServiceImpl.class);

	public EasebuzzAutopayRegisterServiceImpl() {
		this.secretKey = generateSecretKey();
	}

	public void logApi(String url, String requestBody, String responseBody, HttpStatus status, String statusmsg,
			String apiType) {
		AutopayApiLog apiLog = new AutopayApiLog();

		apiLog.setUrl(url);
		apiLog.setRequestBody(requestBody);
		apiLog.setResponseBody(responseBody);
		apiLog.setStatusCode(status.value()); // Status will now be correctly set
		apiLog.setCreated_date(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		apiLog.setApiType(apiType);
		apiLog.setStatus(statusmsg);
		autopayapilogrepository.save(apiLog);

		logger.info("API: {}, Status: {}, Request: {}, Response: {}", url, status.value(), requestBody, responseBody,
				statusmsg, apiType);
	}

	@Override
	public ResponseEntity<String> registerAutopay(InitiateAutopayRequestDto initiateAutopayRequestDto)
			throws Exception {

		String phone = initiateAutopayRequestDto.getPhone();
		if (phone == null || !phone.matches("\\d{10}")) {
			// Return a custom error response for invalid phone number
			logger.warn("Phone number must be exactly 10 digits and contain only digits.");
			return ResponseEntity.badRequest().body("Phone number must be exactly 10 digits and contain only digits.");
		}

		if (initiateAutopayRequestDto.getProductinfo() == null
				|| initiateAutopayRequestDto.getProductinfo().isEmpty()) {
			logger.warn("Productinfo is missing.");
			return ResponseEntity.badRequest().body("Productinfo is a required field.");
		}

		// Required field validation
		if (initiateAutopayRequestDto.getAddress1() == null || initiateAutopayRequestDto.getAddress1().isEmpty()) {
			logger.warn("Address1 is missing.");
			return ResponseEntity.badRequest().body("Address1 is a required field.");
		}
		if (initiateAutopayRequestDto.getAddress2() == null || initiateAutopayRequestDto.getAddress2().isEmpty()) {
			logger.warn("Address2 is missing.");
			return ResponseEntity.badRequest().body("Address2 is a required field.");
		}
		if (initiateAutopayRequestDto.getCity() == null || initiateAutopayRequestDto.getCity().isEmpty()) {
			logger.warn("City is missing.");
			return ResponseEntity.badRequest().body("City is a required field.");
		}
		if (initiateAutopayRequestDto.getState() == null || initiateAutopayRequestDto.getState().isEmpty()) {
			logger.warn("State is missing.");
			return ResponseEntity.badRequest().body("State is a required field.");
		}
		if (initiateAutopayRequestDto.getCountry() == null || initiateAutopayRequestDto.getCountry().isEmpty()) {
			logger.warn("Country is missing.");
			return ResponseEntity.badRequest().body("Country is a required field.");
		}
		if (initiateAutopayRequestDto.getZipcode() == null || initiateAutopayRequestDto.getZipcode().isEmpty()) {
			logger.warn("Zipcode is missing.");
			return ResponseEntity.badRequest().body("Zipcode is a required field.");
		}
		String UrlString = config.getInitiatelinkurl();
		String txnid = generateUniqueTransactionId();
		String customerAuthenticationId = generateUniqueCustomerAuthenticationId();

		initiateAutopayRequestDto.setUdf2("NA");
		initiateAutopayRequestDto.setUdf3("NA");
		initiateAutopayRequestDto.setUdf4("NA");
		initiateAutopayRequestDto.setUdf5(initiateAutopayRequestDto.getMaxAmount());
		initiateAutopayRequestDto.setUdf6("NA");
		initiateAutopayRequestDto.setUdf7("NA");

		logger.info("Uniquely generated txnid for registerautopay" + txnid);
		logger.info("Uniquely generated customerAuthenticationId for registerautopay " + customerAuthenticationId);

		// Generate hash string
		String hashString = config.getKey() + "|" + txnid + "|" + initiateAutopayRequestDto.getAmount() + "|"
				+ initiateAutopayRequestDto.getProductinfo() + "|" + initiateAutopayRequestDto.getFirstname() + "|"
				+ initiateAutopayRequestDto.getEmail() + "|" + initiateAutopayRequestDto.getUdf1() + "|"
				+ initiateAutopayRequestDto.getUdf2() + "|" + initiateAutopayRequestDto.getUdf3() + "|"
				+ initiateAutopayRequestDto.getUdf4() + "|" + initiateAutopayRequestDto.getUdf5() + "|"
				+ initiateAutopayRequestDto.getUdf6() + "|" + initiateAutopayRequestDto.getUdf7() + "||||"
				+ config.getSalt();

		logger.info("Final Hash String before hashing: {}", hashString);

		String hash = generateHash(hashString);

		logger.info("Generated Hash for registerautopay: " + hash);

		String urlParameters = "key=" + config.getKey() + "&txnid=" + txnid + "&amount="
				+ initiateAutopayRequestDto.getAmount() + "&productinfo=" + initiateAutopayRequestDto.getProductinfo()
				+ "&firstname=" + initiateAutopayRequestDto.getFirstname() + "&phone="
				+ initiateAutopayRequestDto.getPhone() + "&email=" + initiateAutopayRequestDto.getEmail() + "&surl="
				+ config.getSurl() + "&furl=" + config.getFurl()

				+ "&hash=" + hash

				+ "&udf1=" + initiateAutopayRequestDto.getUdf1() + "&udf2=" + initiateAutopayRequestDto.getUdf2()
				+ "&udf3=" + initiateAutopayRequestDto.getUdf3() + "&udf4=" + initiateAutopayRequestDto.getUdf4()
				+ "&udf5=" + initiateAutopayRequestDto.getUdf5()

				+ "&udf6=" + initiateAutopayRequestDto.getUdf6() + "&udf7=" + initiateAutopayRequestDto.getUdf7()

				+ "&address1=" + initiateAutopayRequestDto.getAddress1() + "&address2="
				+ initiateAutopayRequestDto.getAddress2() + "&city=" + initiateAutopayRequestDto.getCity() + "&state="
				+ initiateAutopayRequestDto.getState() + "&country=" + initiateAutopayRequestDto.getCountry()
				+ "&zipcode=" + initiateAutopayRequestDto.getZipcode() + "&customer_authentication_id="
				+ customerAuthenticationId + "&sub_merchant_id=" + config.getSubmerchantid()

				+ "&final_collection_date=" + initiateAutopayRequestDto.getFinal_collection_date();

		initiateAutopayRequestDto.setCustomer_authentication_id(customerAuthenticationId);
		initiateAutopayRequestDto.setSurl(config.getSurl());
		initiateAutopayRequestDto.setFurl(config.getFurl());
		initiateAutopayRequestDto.setTxnid(txnid);
		initiateAutopayRequestDto.setHash(hash);
		initiateAutopayRequestDto.setKey(config.getKey());
		initiateAutopayRequestDto.setSub_merchant_id(config.getSubmerchantid());
		initiateAutopayRequestDto.setCreated_by("Admin");
		initiateAutopayRequestDto.setCreated_date(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

		String response1;
		HttpURLConnection connection = null;

		try {
			// Send POST request
			URL url = new URL(UrlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setDoOutput(true);

			logger.info("Request Url " + UrlString);
			logger.info("Request Body " + urlParameters);

			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
				wr.writeBytes(urlParameters);
				wr.flush();
			}

			int responseCode = connection.getResponseCode();
			logger.info("Response Code " + responseCode);

			StringBuilder response = new StringBuilder();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				}
				response1 = response.toString();
				logger.info("Response Body " + response1);

				// Parse JSON response to retrieve the access key
				ObjectMapper mapper = new ObjectMapper();
				JsonNode jsonResponse = mapper.readTree(response1);

				int status = jsonResponse.get("status").asInt();
				if (status == 1) {
					// Success scenario
					String accessKey = jsonResponse.get("data").asText();
					String paymentBaseUrl = config.getPaymentUrl();
					String paymentUrl = paymentBaseUrl + accessKey;

					ObjectNode responseObject = mapper.createObjectNode();

					responseObject.put("status", 1);
					responseObject.put("txnid", txnid);
					responseObject.put("amount", initiateAutopayRequestDto.getAmount());
					responseObject.put("productinfo", initiateAutopayRequestDto.getProductinfo());
					responseObject.put("firstname", initiateAutopayRequestDto.getFirstname());
					responseObject.put("phone", initiateAutopayRequestDto.getPhone());
					responseObject.put("email", initiateAutopayRequestDto.getEmail());
					responseObject.put("customer_authentication_id", customerAuthenticationId);
					responseObject.put("data", paymentUrl);
					responseObject.put("finalCollectionDate", initiateAutopayRequestDto.getFinal_collection_date());
					responseObject.put("maxAmount", initiateAutopayRequestDto.getMaxAmount());
					responseObject.put("access_key", accessKey);

					logger.info("Response Body " + responseObject);
					logApi(UrlString, urlParameters, responseObject.toString(), HttpStatus.OK, "Success", "Initiate");

					initiateAutopayRequestDto.setStatusMesg("Success");
					initiateAutopayRequestDto.setAccess_key(accessKey);
					initiateAutopayRequestDto.setData(paymentUrl);
					initiateAutopayRequestDto.setStatus(status);
					initiateAutopayRequestDto.setErrorDesc("success");
					initiateAutopayRepository.save(initiateAutopayRequestDto);

					return ResponseEntity.status(responseCode).body(responseObject.toString());

				} else {
					// Error scenario
					String errorDesc = jsonResponse.get("error_desc").asText();
					String data = jsonResponse.get("data").asText();

					ObjectNode errorResponse = mapper.createObjectNode();
					errorResponse.put("status", 0);
					errorResponse.put("error_desc", errorDesc);
					errorResponse.put("data", data);
					logApi(UrlString, urlParameters, errorResponse.toString(), HttpStatus.OK, "Failure", "Initiate");

					initiateAutopayRequestDto.setStatusMesg("Failure");
					initiateAutopayRequestDto.setAccess_key("NA");
					initiateAutopayRequestDto.setData("NA");
					initiateAutopayRequestDto.setStatus(0);
					initiateAutopayRequestDto.setErrorDesc(errorDesc);
					initiateAutopayRepository.save(initiateAutopayRequestDto);
					return ResponseEntity.status(responseCode).body(errorResponse.toString());
				}

			} else {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				}
				response1 = response.toString();

				logger.error("Error Response Body: " + response1);
				logApi(UrlString, urlParameters, response1, HttpStatus.valueOf(responseCode), "Failure", "Initiate");

				return ResponseEntity.status(responseCode).body(response1);
			}

		} catch (IOException e) {
			response1 = e.getMessage();
			logApi(UrlString, urlParameters, response1, HttpStatus.INTERNAL_SERVER_ERROR, "Error", "Initiate");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response1);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}

		}
	}

	@Override
	public ResponseEntity<String> debitRequest(DebitAutopayRequestDto debitAutopayRequestDto) throws Exception {

		Optional<InitiateAutopayRequestDto> autopayEntityOpt = initiateAutopayRepository
				.findByCustomerAuthenticationId(debitAutopayRequestDto.getCustomer_authentication_id());

		if (!autopayEntityOpt.isPresent()) {
			// Handle case where entity is not found

			logger.warn("Records not found for the provided customer_authentication_id");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Records not found for the provided customer_authentication_id");

		}

		// Extract the entity from Optional
		InitiateAutopayRequestDto autopayEntity = autopayEntityOpt.get();

		// Set fields from the entity
		debitAutopayRequestDto.setProductinfo(autopayEntity.getProductinfo());
		debitAutopayRequestDto.setFirstname(autopayEntity.getFirstname());
		debitAutopayRequestDto.setPhone(autopayEntity.getPhone());
		debitAutopayRequestDto.setEmail(autopayEntity.getEmail());
		debitAutopayRequestDto.setUdf1(autopayEntity.getUdf1());
		debitAutopayRequestDto.setAddress1(autopayEntity.getAddress1());
		debitAutopayRequestDto.setAddress2(autopayEntity.getAddress2());
		debitAutopayRequestDto.setCity(autopayEntity.getCity());
		debitAutopayRequestDto.setState(autopayEntity.getState());
		debitAutopayRequestDto.setCountry(autopayEntity.getCountry());
		debitAutopayRequestDto.setZipcode(autopayEntity.getZipcode());

		String UrlString = config.getDebitRequesturl();
		String txnid = generateUniqueTransactionId();

		String merchant_debit_id = generateUniqueMerchantDebitId();
		logger.info("Uniquely generated txnid for debitrequest " + txnid);
		logger.info("Uniquely generated merchant_debit_id for debitreques " + merchant_debit_id);

		debitAutopayRequestDto.setUdf2("NA");
		debitAutopayRequestDto.setUdf3("NA");
		debitAutopayRequestDto.setUdf4("NA");
		debitAutopayRequestDto.setUdf5("NA");

		// Generate hash string
		String hashString = config.getKey() + "|" + txnid + "|" + debitAutopayRequestDto.getAmount() + "|"
				+ debitAutopayRequestDto.getProductinfo() + "|" + debitAutopayRequestDto.getFirstname() + "|"
				+ debitAutopayRequestDto.getEmail() + "|" + debitAutopayRequestDto.getUdf1() + "|"
				+ debitAutopayRequestDto.getUdf2() + "|" + debitAutopayRequestDto.getUdf3() + "|"
				+ debitAutopayRequestDto.getUdf4() + "|" + debitAutopayRequestDto.getUdf5() + "||||||"
				+ config.getSalt();

		logger.info("Final Hash String before hashing: {}", hashString);

		String hash = generateHash(hashString);

		logger.info("Generated Hash: " + hash);

		String urlParameters = "key=" + config.getKey() + "&txnid=" + txnid + "&amount="
				+ debitAutopayRequestDto.getAmount() + "&productinfo=" + debitAutopayRequestDto.getProductinfo()
				+ "&firstname=" + debitAutopayRequestDto.getFirstname() + "&phone=" + debitAutopayRequestDto.getPhone()
				+ "&email=" + debitAutopayRequestDto.getEmail() + "&surl=" + config.getSurl() + "&furl="
				+ config.getFurl()

				+ "&hash=" + hash

				+ "&udf1=" + debitAutopayRequestDto.getUdf1() + "&udf2=" + debitAutopayRequestDto.getUdf2() + "&udf3="
				+ debitAutopayRequestDto.getUdf3() + "&udf4=" + debitAutopayRequestDto.getUdf4() + "&udf5="
				+ debitAutopayRequestDto.getUdf5()

				+ "&address1=" + debitAutopayRequestDto.getAddress1() + "&address2="
				+ debitAutopayRequestDto.getAddress2() + "&city=" + debitAutopayRequestDto.getCity() + "&state="
				+ debitAutopayRequestDto.getState() + "&country=" + debitAutopayRequestDto.getCountry() + "&zipcode="
				+ debitAutopayRequestDto.getZipcode()

				+ "&customer_authentication_id=" + debitAutopayRequestDto.getCustomer_authentication_id()

				+ "&merchant_debit_id=" + merchant_debit_id + "&auto_debit_access_key="
				+ debitAutopayRequestDto.getAuto_debit_access_key() + "&sub_merchant_id=" + config.getSubmerchantid();

		Debitrequestdetails debitrequestdetails = new Debitrequestdetails();

		debitrequestdetails.setCustomer_authentication_id(debitAutopayRequestDto.getCustomer_authentication_id());
		debitrequestdetails.setFirstname(debitAutopayRequestDto.getFirstname());
		debitrequestdetails.setEmail(debitAutopayRequestDto.getEmail());
		debitrequestdetails.setPhone(debitAutopayRequestDto.getPhone());
		debitrequestdetails.setProductinfo(debitAutopayRequestDto.getProductinfo());
		debitrequestdetails.setCreated_by("Admin");
		debitrequestdetails.setCreated_date(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		debitrequestdetails.setAuto_debit_access_key(debitAutopayRequestDto.getAuto_debit_access_key());
		debitrequestdetails.setHash(hash);
		debitrequestdetails.setMaxAmount(autopayEntity.getMaxAmount());

		String response1;
		HttpURLConnection connection = null;

		try {
			// Send POST request
			URL url = new URL(UrlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setDoOutput(true);

			logger.info("Request Url " + UrlString);
			logger.info("Request Body " + urlParameters);

			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
				wr.writeBytes(urlParameters);
				wr.flush();
			}

			int responseCode = connection.getResponseCode();
			logger.info("Response Code " + responseCode);

			StringBuilder response = new StringBuilder();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				}
				response1 = response.toString();
				logger.info("Response Body " + response1);
				ObjectMapper mapper = new ObjectMapper();
				JsonNode jsonResponse = mapper.readTree(response1);

				int status = jsonResponse.get("status").asInt();
				String data = jsonResponse.get("data").asText();
				if (status == 1) {
					// Success scenario

					ObjectNode responseObject = mapper.createObjectNode();
					responseObject.put("status", 1);
					responseObject.put("data", data);
					responseObject.put("txnid", txnid);
					responseObject.put("merchant_debit_id", merchant_debit_id);

					debitrequestdetails.setStatus(status);
					debitrequestdetails.setData(data);
					debitrequestdetails.setTxnid(txnid);
					debitrequestdetails.setMerchant_debit_id(merchant_debit_id);
					debitrequestdetails.setDebitStatus("Success");
					debitrequestdetails.setErrorDesc(data);

					debitRequestRepository.save(debitrequestdetails);

					logger.info("Response Body " + responseObject);
					logApi(UrlString, urlParameters, responseObject.toString(), HttpStatus.OK, "Success",
							"DebitRequest");
					return ResponseEntity.status(responseCode).body(responseObject.toString());

				} else {
					// Error scenario
					String errorDesc = jsonResponse.get("error_desc").asText();
					String data1 = jsonResponse.get("data").asText();

					ObjectNode errorResponse = mapper.createObjectNode();
					errorResponse.put("status", 0);
					errorResponse.put("error_desc", errorDesc);
					errorResponse.put("data", data1);
					debitrequestdetails.setStatus(status);
					debitrequestdetails.setData(data);
					debitrequestdetails.setTxnid(txnid);
					debitrequestdetails.setMerchant_debit_id(merchant_debit_id);
					debitrequestdetails.setErrorDesc(errorDesc);
					debitrequestdetails.setDebitStatus("Failure");

					debitRequestRepository.save(debitrequestdetails);
					logApi(UrlString, urlParameters, errorResponse.toString(), HttpStatus.OK, "Failure",
							"DebitRequest");
					return ResponseEntity.status(responseCode).body(errorResponse.toString());
				}

			} else {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				}
				response1 = response.toString();
				logApi(UrlString, urlParameters, response1, HttpStatus.OK, "Failure", "DebitRequest");
				logger.error("Error Response Body: " + response1);

				return ResponseEntity.status(responseCode).body(response1);
			}
		} catch (IOException e) {
			response1 = e.getMessage();
			logApi(UrlString, urlParameters, response1, HttpStatus.INTERNAL_SERVER_ERROR, "Error", "DebitRequest");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response1);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	private SecretKey generateSecretKey() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(128);
			return keyGenerator.generateKey();
		} catch (Exception e) {
			throw new RuntimeException("Error generating encryption key", e);
		}
	}

	private String encrypt(String data) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] iv = new byte[16];
		SecureRandom random = new SecureRandom();
		random.nextBytes(iv);
		IvParameterSpec ivSpec = new IvParameterSpec(iv);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
		byte[] encrypted = cipher.doFinal(data.getBytes());
		byte[] encryptedWithIv = new byte[iv.length + encrypted.length];
		System.arraycopy(iv, 0, encryptedWithIv, 0, iv.length);
		System.arraycopy(encrypted, 0, encryptedWithIv, iv.length, encrypted.length);

		return Base64.getUrlEncoder().encodeToString(encryptedWithIv);
	}

	private String generateUniqueTransactionId() throws Exception {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String encryptedUuid = encrypt(uuid);
		String alphanumericId = encryptedUuid.replaceAll("[^a-zA-Z0-9]", "");
		int desiredLength = 32;
		return alphanumericId.substring(0, Math.min(desiredLength, alphanumericId.length()));
	}

	private String generateUniqueMerchantDebitId() throws Exception {

		String uuid = UUID.randomUUID().toString().replace("-", "");
		String encryptedUuid = encrypt(uuid);
		String alphanumericId = encryptedUuid.replaceAll("[^a-zA-Z0-9]", "");
		int desiredLength = 32;
		return alphanumericId.substring(0, Math.min(desiredLength, alphanumericId.length()));
	}

	private String generateUniqueCustomerAuthenticationId() throws Exception {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String encryptedUuid = encrypt(uuid);
		String alphanumericId = encryptedUuid.replaceAll("[^a-zA-Z0-9]", "");
		int desiredLength = 32;
		return alphanumericId.substring(0, Math.min(desiredLength, alphanumericId.length()));
	}

	private String generateHash(String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("sha512");
		md.update(input.getBytes(StandardCharsets.UTF_8)); // Ensure UTF-8 encoding
		byte[] hashBytes = md.digest();
		StringBuilder sb = new StringBuilder();
		for (byte b : hashBytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}
