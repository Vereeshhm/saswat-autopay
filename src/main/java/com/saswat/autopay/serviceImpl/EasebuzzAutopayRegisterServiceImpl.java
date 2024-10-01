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
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.saswat.autopay.Utils.PropertiesConfig;
import com.saswat.autopay.dto.CancelMandateDto;
import com.saswat.autopay.dto.DebitAutopayRequestDto;
import com.saswat.autopay.dto.TransactionStatusDto;
import com.saswat.autopay.model.AutopayApiLog;
import com.saswat.autopay.model.Cancelmandatedetails;
import com.saswat.autopay.model.Debitrequestdetails;
import com.saswat.autopay.model.InitiateAutopayRequestDto;
import com.saswat.autopay.model.LenderSubmerchant;
import com.saswat.autopay.model.TransactionEntity;
import com.saswat.autopay.repository.AutopayApilogrepository;
import com.saswat.autopay.repository.CancelMandatedetailsrepository;
import com.saswat.autopay.repository.DebitRequestRepository;
import com.saswat.autopay.repository.InitiateAutopayRepository;
import com.saswat.autopay.repository.LenderSubmerchantRepository;
import com.saswat.autopay.repository.TransactionEntityRepository;
import com.saswat.autopay.service.EasebuzzAutopayRegisterService;
import net.minidev.json.JSONObject;

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

	@Autowired
	CancelMandatedetailsrepository cancelMandatedetailsrepository;

	@Autowired
	TransactionEntityRepository transactionEntityRepository;

	@Autowired
	RestTemplate restTemplate;

	private Key secretKey;

	@Autowired
	Environment environment;

	@Autowired
	LenderSubmerchantRepository lenderSubmerchantRepository;

	private static final Logger logger = LoggerFactory.getLogger(EasebuzzAutopayRegisterServiceImpl.class);

	public EasebuzzAutopayRegisterServiceImpl() {
		this.secretKey = generateSecretKey();
	}

	private String getSubMerchantId(String lenderName) {
		String currentProfile = environment.getActiveProfiles()[0]; // Get active profile

		logger.info("current environment running:{} ", currentProfile);
		Optional<LenderSubmerchant> lenderSubmerchantOpt = lenderSubmerchantRepository
				.findByLenderNameAndEnvironment(lenderName.toLowerCase(), currentProfile);

		return lenderSubmerchantOpt.map(LenderSubmerchant::getSubMerchantId)
				.orElseThrow(() -> new IllegalArgumentException(
						"No submerchant ID found for lender: " + lenderName + " in environment: " + currentProfile));
	}

	public void logApi(String url, String requestBody, String responseBody, HttpStatus status, String statusmsg,
			String apiType) {
		AutopayApiLog apiLog = new AutopayApiLog();

		apiLog.setUrl(url);
		apiLog.setRequestBody(requestBody);
		apiLog.setResponseBody(responseBody);
		apiLog.setStatusCode(status.value());
		apiLog.setCreated_date(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		apiLog.setApiType(apiType);
		apiLog.setStatus(statusmsg);
		autopayapilogrepository.save(apiLog);

		logger.info("API: {}, Status: {}, Request: {}, Response: {}", url, status.value(), requestBody, responseBody,
				statusmsg, apiType);
	}

	@Override
	public ResponseEntity<String> registerAutopay(
			com.saswat.autopay.dto.RegisterAutopayRequestDto registerAutopayRequestDto) throws Exception {

		InitiateAutopayRequestDto initiateAutopayRequestDto = new InitiateAutopayRequestDto();

		initiateAutopayRequestDto.setAmount(registerAutopayRequestDto.getAmount());
		initiateAutopayRequestDto.setProductinfo(registerAutopayRequestDto.getProductinfo());
		initiateAutopayRequestDto.setFirstname(registerAutopayRequestDto.getFirstname());
		initiateAutopayRequestDto.setPhone(registerAutopayRequestDto.getPhone());
		initiateAutopayRequestDto.setEmail(registerAutopayRequestDto.getEmail());
		initiateAutopayRequestDto.setUdf1(registerAutopayRequestDto.getUdf1());
		initiateAutopayRequestDto.setMaxAmount(registerAutopayRequestDto.getMaxAmount());
		initiateAutopayRequestDto.setAddress1(registerAutopayRequestDto.getAddress1());
		initiateAutopayRequestDto.setAddress2(registerAutopayRequestDto.getAddress1());
		initiateAutopayRequestDto.setCity(registerAutopayRequestDto.getCity());
		initiateAutopayRequestDto.setState(registerAutopayRequestDto.getState());
		initiateAutopayRequestDto.setCountry(registerAutopayRequestDto.getCountry());
		initiateAutopayRequestDto.setZipcode(registerAutopayRequestDto.getZipcode());
		initiateAutopayRequestDto.setFinal_collection_date(registerAutopayRequestDto.getFinal_collection_date());
		initiateAutopayRequestDto.setShow_payment_mode("");

		String UrlString = config.getInitiatelinkurl();
		String txnid = generateUniqueTransactionId();
		String customerAuthenticationId = generateUniqueCustomerAuthenticationId();

		String lenderName = registerAutopayRequestDto.getLenderName();
		if (lenderName == null || lenderName.isEmpty()) {
			throw new IllegalArgumentException("Lender name cannot be null or empty.");
		}

		String subMerchantId = getSubMerchantId(lenderName);
		initiateAutopayRequestDto.setSub_merchant_id(subMerchantId);

		logger.info("Received lenderName: {}, Sub-Merchant ID: {}", lenderName, subMerchantId);

		initiateAutopayRequestDto.setSub_merchant_id(subMerchantId);
		initiateAutopayRequestDto.setUdf2("NA");
		initiateAutopayRequestDto.setUdf3("NA");
		initiateAutopayRequestDto.setUdf4("NA");
		initiateAutopayRequestDto.setUdf5(initiateAutopayRequestDto.getMaxAmount());
		initiateAutopayRequestDto.setUdf6("NA");
		initiateAutopayRequestDto.setUdf7("NA");

		logger.info("Uniquely generated txnid for registerautopay " + txnid);
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
				+ customerAuthenticationId + "&sub_merchant_id=" + subMerchantId + "&lenderName=" + lenderName

				+ "&final_collection_date=" + initiateAutopayRequestDto.getFinal_collection_date();

		initiateAutopayRequestDto.setCustomer_authentication_id(customerAuthenticationId);
		initiateAutopayRequestDto.setSurl(config.getSurl());
		initiateAutopayRequestDto.setFurl(config.getFurl());
		initiateAutopayRequestDto.setTxnid(txnid);
		initiateAutopayRequestDto.setHash(hash);
		initiateAutopayRequestDto.setKey(config.getKey());
		initiateAutopayRequestDto.setSub_merchant_id(subMerchantId);
		initiateAutopayRequestDto.setCreated_by("Admin");
		initiateAutopayRequestDto.setCreated_date(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		initiateAutopayRequestDto.setLenderName(lenderName);

		String phone = initiateAutopayRequestDto.getPhone();
		if (phone == null || !phone.matches("\\d{10}")) {

			logger.warn("phone number must be exactly 10 digits and contain only digits.");
			return createErrorResponse("phone number must be exactly 10 digits and contain only digits.",
					initiateAutopayRequestDto);
		}

		if (initiateAutopayRequestDto.getProductinfo() == null
				|| initiateAutopayRequestDto.getProductinfo().isEmpty()) {
			logger.warn("productinfo is missing.");
			return createErrorResponse("productinfo is a required field.", initiateAutopayRequestDto);
		}

		if (initiateAutopayRequestDto.getAddress1() == null || initiateAutopayRequestDto.getAddress1().isEmpty()) {
			logger.warn("address1 is missing.");
			return createErrorResponse("address1 is a required field.", initiateAutopayRequestDto);
		}
		if (initiateAutopayRequestDto.getAddress2() == null || initiateAutopayRequestDto.getAddress2().isEmpty()) {
			logger.warn("address2 is missing.");
			return createErrorResponse("address2 is a required field.", initiateAutopayRequestDto);
		}
		if (initiateAutopayRequestDto.getCity() == null || initiateAutopayRequestDto.getCity().isEmpty()) {
			logger.warn("city is missing.");
			return createErrorResponse("city is a required field.", initiateAutopayRequestDto);
		}
		if (initiateAutopayRequestDto.getState() == null || initiateAutopayRequestDto.getState().isEmpty()) {
			logger.warn("state is missing.");
			return createErrorResponse("state is a required field.", initiateAutopayRequestDto);
		}
		if (initiateAutopayRequestDto.getCountry() == null || initiateAutopayRequestDto.getCountry().isEmpty()) {
			logger.warn("country is missing.");
			return createErrorResponse("country is a required field.", initiateAutopayRequestDto);
		}
		if (initiateAutopayRequestDto.getZipcode() == null || initiateAutopayRequestDto.getZipcode().isEmpty()) {
			logger.warn("zipcode is missing.");
			return createErrorResponse("zipcode is a required field.", initiateAutopayRequestDto);
		}

		String response1;
		HttpURLConnection connection = null;

		try {
			// Send POST request
			URL url = new URL(UrlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept", "application/json");
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

	private ResponseEntity<String> createErrorResponse(String message,
			InitiateAutopayRequestDto initiateAutopayRequestDto) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode errorResponse = mapper.createObjectNode();
		errorResponse.put("status", 0);
		errorResponse.put("error_desc", message);
		errorResponse.put("data", message);

		logApi(config.getInitiatelinkurl(), initiateAutopayRequestDto.toString(), errorResponse.toString(),
				HttpStatus.BAD_REQUEST, "Failure", "Initiate");
		return ResponseEntity.ok().body(errorResponse.toString());
	}

	@Override
	public ResponseEntity<String> debitRequest(DebitAutopayRequestDto debitAutopayRequestDto) throws Exception {

		Optional<InitiateAutopayRequestDto> autopayEntityOpt = initiateAutopayRepository
				.findByCustomerAuthenticationId(debitAutopayRequestDto.getCustomer_authentication_id());

		if (!autopayEntityOpt.isPresent()) {

			logger.warn("Records not found for the provided customer_authentication_id");
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body("Records not found for the provided customer_authentication_id");

		}

		InitiateAutopayRequestDto autopayEntity = autopayEntityOpt.get();

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
		debitAutopayRequestDto.setSub_merchant_id(autopayEntity.getSub_merchant_id());

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
				+ debitAutopayRequestDto.getAuto_debit_access_key() + "&sub_merchant_id="
				+ debitAutopayRequestDto.getSub_merchant_id();

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
		debitrequestdetails.setLenderName(autopayEntity.getLenderName());
		debitrequestdetails.setSub_merchant_id(autopayEntity.getSub_merchant_id());

		String response1;
		HttpURLConnection connection = null;

		try {
			// Send POST request
			URL url = new URL(UrlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept", "application/json");
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

	@Override
	public ResponseEntity<String> cancelMandate(CancelMandateDto cancelMandateDto)
			throws NoSuchAlgorithmException, JsonProcessingException {

		String UrlString = config.getCancelMandateurl();

		// Construct the hash string
		String hashString = config.getKey() + "|" + cancelMandateDto.getEasebuzz_id() + "|"
				+ cancelMandateDto.getAuto_debit_access_key() + "|" + cancelMandateDto.getCustomer_authentication_id()
				+ "|" + config.getSalt();

		logger.info("Final Hash String before hashing: {}", hashString);

		// Generate the hash
		String hash = generateHash(hashString);

		logger.info("Generated Hash: {}", hash);

		JSONObject json = new JSONObject();
		json.put("key", config.getKey());
		json.put("easebuzz_id", cancelMandateDto.getEasebuzz_id());
		json.put("customer_authentication_id", cancelMandateDto.getCustomer_authentication_id());
		json.put("auto_debit_access_key", cancelMandateDto.getAuto_debit_access_key());
		json.put("hash", hash);

		
		String jsonRequestBody = json.toString();

		String response1;
		HttpURLConnection connection = null;

		try {
			// Send POST request
			URL url = new URL(UrlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);

			logger.info("Request Url " + config.getCancelMandateurl());
			logger.info("Request Body " + jsonRequestBody);

			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {

				wr.writeBytes(jsonRequestBody);
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

				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode jsonResponse = objectMapper.readTree(response1);
				Cancelmandatedetails cancelMandateDetails = new Cancelmandatedetails();

				cancelMandateDetails.setAuto_debit_access_key(cancelMandateDto.getAuto_debit_access_key());
				cancelMandateDetails.setHash(hash);
				cancelMandateDetails.setEasebuzz_id(cancelMandateDto.getEasebuzz_id());
				cancelMandateDetails.setKey(config.getKey());
				cancelMandateDetails.setCustomer_authentication_id(cancelMandateDto.getCustomer_authentication_id());

				cancelMandatedetailsrepository.save(cancelMandateDetails);
				if (jsonResponse.has("status") && jsonResponse.get("status").asBoolean()) {

					cancelMandateDetails.setStatus(true);
					if (jsonResponse.has("data")) {
						String dataMessage = jsonResponse.get("data").asText();
						cancelMandateDetails.setData(dataMessage);
						logger.info("Success response data: {}", dataMessage);
					}

					if (jsonResponse.has("message")) {
						String message = jsonResponse.get("message").asText();
						cancelMandateDetails.setMessage(message);

						logger.info("Success response message: {}", message);
					}

				} else {

					cancelMandateDetails.setStatus(false);

					if (jsonResponse.has("message")) {
						String message = jsonResponse.get("message").asText();
						cancelMandateDetails.setMessage(message);

						logger.info("Error response message: {}", message);
					}
				}

				cancelMandatedetailsrepository.save(cancelMandateDetails);
				logApi(UrlString, jsonRequestBody, response1, HttpStatus.OK, "Success", "Cancelmandate");
				return ResponseEntity.ok(response1);
			} else {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				}
				response1 = response.toString();
				logApi(UrlString, jsonRequestBody, response1, HttpStatus.OK, "Failure", "Cancelmandate");
				logger.error("Error Response Body: {}", response1);

				Cancelmandatedetails cancelMandateDetails = new Cancelmandatedetails();
				cancelMandateDetails.setStatus(false);
				String customerAuthId = cancelMandateDto.getCustomer_authentication_id();
				String autoDebitAccessKey = cancelMandateDto.getAuto_debit_access_key();

				cancelMandateDetails.setAuto_debit_access_key(autoDebitAccessKey);
				cancelMandateDetails.setCustomer_authentication_id(customerAuthId);

				logger.info("Setting auto_debit_access_key: {}", autoDebitAccessKey);
				logger.info("Setting customer_authentication_id: {}", customerAuthId);

				logger.info("fetched customer auth id" + cancelMandateDto.getCustomer_authentication_id());

				if (response1.contains("error_desc")) {
					try {
						ObjectMapper objectMapper = new ObjectMapper();
						JsonNode jsonResponse = objectMapper.readTree(response1);
						if (jsonResponse.has("error_desc")) {
							String errorMessage = jsonResponse.get("error_desc").asText();

							logger.info("Error desc: {}", errorMessage);
							cancelMandateDetails.setError_desc(errorMessage);
						}
					} catch (JsonProcessingException e) {
						logger.error("Error processing JSON response: {}", e.getMessage());
					}
				}

				cancelMandatedetailsrepository.save(cancelMandateDetails);
				return ResponseEntity.status(responseCode).body(response1);
			}

		} catch (IOException e) {
			response1 = e.getMessage();
			logApi(UrlString, jsonRequestBody, response1, HttpStatus.INTERNAL_SERVER_ERROR, "Error", "Cancelmandate");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response1);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	@Override
	public ResponseEntity<String> getTransactionstatus(TransactionStatusDto statusrequest) throws Exception {

		String txnid = statusrequest.getTxnid();
		String UrlString = config.getTransactionUrl();
		String hashString = config.getKey() + "|" + txnid + "|" + config.getSalt();

		logger.info("Generated txnid " + txnid);
		String hash = generateHash(hashString);

		logger.info("Generated hash for Transaction status " + hash);
		statusrequest.setHash(hash);
		statusrequest.setTxnid(statusrequest.getTxnid());
		statusrequest.setKey(config.getKey());

		String urlParameters = "txnid=" + txnid + "&key=" + config.getKey() + "&hash=" + hash;

		String responseBody = null;
		String errorResponse = null;

		HttpURLConnection connection = null;

		try {
			URL url = new URL(UrlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Accept", "application/json");
			connection.setDoOutput(true);

			logger.info("Request Url: " + UrlString);
			logger.info("Request Body: " + urlParameters);

			try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
				wr.writeBytes(urlParameters);
				wr.flush();
			}

			int responseCode = connection.getResponseCode();
			logger.info("Response Code: " + responseCode);

			StringBuilder response = new StringBuilder();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				}
				responseBody = response.toString();
				logger.info("Response Body: " + responseBody);

				ObjectMapper mapper = new ObjectMapper();
				JsonNode jsonResponse = mapper.readTree(responseBody);

				// Check if the response contains the "status" field
				boolean status = jsonResponse.get("status").asBoolean();
				JsonNode msgNode = jsonResponse.get("msg");

				if (status) {
					// "status" is true, now check if "msg" is an array
					if (msgNode.isArray() && msgNode.size() > 0) {
						JsonNode firstMsgObject = msgNode.get(0); // Get the first object in the array

						String transactionId = firstMsgObject.has("txnid") ? firstMsgObject.get("txnid").asText()
								: null;
						String firstname = firstMsgObject.has("firstname") ? firstMsgObject.get("firstname").asText()
								: null;
						String email = firstMsgObject.has("email") ? firstMsgObject.get("email").asText() : null;
						String phone = firstMsgObject.has("phone") ? firstMsgObject.get("phone").asText() : null;

						String easepayid = firstMsgObject.has("easepayid") ? firstMsgObject.get("easepayid").asText()
								: null;
						String amount = firstMsgObject.has("amount") ? firstMsgObject.get("amount").asText() : null;
						String productinfo = firstMsgObject.has("productinfo")
								? firstMsgObject.get("productinfo").asText()
								: null;
						String hashValue = firstMsgObject.has("hash") ? firstMsgObject.get("hash").asText() : null;
						String paymentSource = firstMsgObject.has("payment_source")
								? firstMsgObject.get("payment_source").asText()
								: null;
						String surl = firstMsgObject.has("surl") ? firstMsgObject.get("surl").asText() : null;
						String furl = firstMsgObject.has("furl") ? firstMsgObject.get("furl").asText() : null;
						String statusMessage = firstMsgObject.has("status") ? firstMsgObject.get("status").asText()
								: null;
						String customerauthenticationid = firstMsgObject.has("customer_authentication_id")
								? firstMsgObject.get("customer_authentication_id").asText()
								: null;
						String autodebitaccesskey = firstMsgObject.has("auto_debit_access_key")
								? firstMsgObject.get("auto_debit_access_key").asText()
								: "NA";
						String authorization_status = firstMsgObject.has("authorization_status")
								? firstMsgObject.get("authorization_status").asText()
								: "NA";
						String addedon = firstMsgObject.has("addedon") ? firstMsgObject.get("addedon").asText() : "NA";

						String card_type = firstMsgObject.has("card_type") ? firstMsgObject.get("card_type").asText()
								: "NA";
						String error_Message = firstMsgObject.has("error_Message")
								? firstMsgObject.get("error_Message").asText()
								: "NA";
						String error = firstMsgObject.has("error") ? firstMsgObject.get("error").asText() : "NA";
						String udf5 = firstMsgObject.has("udf5") ? firstMsgObject.get("udf5").asText() : "NA";

						// Create a new Transactions entity and populate it
						TransactionEntity transaction = new TransactionEntity();
						transaction.setTxnid(transactionId);
						transaction.setFirstname(firstname);
						transaction.setEmail(email);
						transaction.setPhone(phone);
						transaction.setEasepayid(easepayid);
						transaction.setAmount(Double.parseDouble(amount));
						transaction.setProductInfo(productinfo);
						transaction.setHash(hashValue);
						transaction.setPaymentSource(paymentSource);
						transaction.setSuccessUrl(surl);
						transaction.setFailureUrl(furl);
						transaction.setStatus(statusMessage);
						transaction.setCustomer_authentication_id(customerauthenticationid);
						transaction.setAuto_debit_access_key(autodebitaccesskey);
						transaction.setAuthorization_status(authorization_status);
						transaction.setError(error);
						transaction.setError_Message(error_Message);
						transaction.setCard_type(card_type);
						transaction.setAddedon(addedon);
						transaction.setUdf5(udf5);

						// Save the transaction to the database
						transactionEntityRepository.save(transaction);

						logApi(UrlString, urlParameters, responseBody, HttpStatus.OK, "Success", "Transaction");
						return ResponseEntity.ok(responseBody);

					} else {
						logger.error("Unexpected format for 'msg' when status is true");
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected response format");
					}
				} else {
					// "status" is false, so we expect "msg" to be a simple string
					if (msgNode.isTextual()) {
						String errorDesc = msgNode.asText();
						logger.error("Error: " + errorDesc);

						TransactionEntity transaction = new TransactionEntity();
						transaction.setError_Message(errorDesc);
						transaction.setTxnid(txnid);
						transactionEntityRepository.save(transaction);
						logApi(UrlString, urlParameters, errorDesc, HttpStatus.OK, "failure", "Transaction");
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + errorDesc);
					} else {
						logger.error("Unexpected format for 'msg' when status is false");
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unexpected response format");
					}
				}

			} else {

				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				}
				errorResponse = response.toString();
				logger.error("Error Response Body: " + errorResponse);

				try {
					ObjectMapper mapper = new ObjectMapper();
					JsonNode jsonResponse = mapper.readTree(errorResponse);

					// Check if 'error_desc' exists in the JSON response
					if (jsonResponse.has("error_desc")) {
						String errorDesc = jsonResponse.get("error_desc").asText();

						TransactionEntity transaction = new TransactionEntity();
						transaction.setError_Message(errorDesc);
						transaction.setTxnid(txnid);
						transactionEntityRepository.save(transaction);
					}
				} catch (JsonProcessingException e) {
					logger.error("Failed to parse error response body: " + e.getMessage(), e);
				}

				logApi(UrlString, urlParameters, errorResponse, HttpStatus.valueOf(responseCode), "Failure",
						"Transaction");
				return ResponseEntity.status(responseCode).body(errorResponse); // Return the actual error response

			}

		} catch (IOException e) {
			errorResponse = e.getMessage();
			logger.error("Exception occurred: " + e.getMessage(), e);

			logApi(UrlString, urlParameters, errorResponse, HttpStatus.INTERNAL_SERVER_ERROR, "Failure",
					"Transaction");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse); // Return the exception
																								// message

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
		md.update(input.getBytes(StandardCharsets.UTF_8));
		byte[] hashBytes = md.digest();
		StringBuilder sb = new StringBuilder();
		for (byte b : hashBytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}
