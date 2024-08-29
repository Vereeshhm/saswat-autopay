package com.saswat.autopay.serviceImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
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
import com.saswat.autopay.Utils.HashUtil;
import com.saswat.autopay.Utils.InitiateAutopayRequestDto;
import com.saswat.autopay.Utils.PropertiesConfig;
import com.saswat.autopay.service.EasebuzzAutopayRegisterService;

@Service
public class EasebuzzAutopayRegisterServiceImpl implements EasebuzzAutopayRegisterService {

	@Autowired
	PropertiesConfig config;

	private Key secretKey;

	private static final Logger logger = LoggerFactory.getLogger(EasebuzzAutopayRegisterServiceImpl.class);

	public EasebuzzAutopayRegisterServiceImpl() {
		this.secretKey = generateSecretKey();
	}

	@Override
	public ResponseEntity<String> registerAutopay(InitiateAutopayRequestDto initiateAutopayRequestDto)
			throws Exception {

		// Prepare URL and transaction ID
		String UrlString = config.getInitiatelinkurl();
		String txnid = generateUniqueTransactionId();
		String customerAuthenticationId = "apay_" + txnid;

		initiateAutopayRequestDto.setTxnid(txnid);
		initiateAutopayRequestDto.setCustomer_authentication_id(customerAuthenticationId);
		initiateAutopayRequestDto.setSurl(config.getSurl());
		initiateAutopayRequestDto.setFurl(config.getFurl());
		initiateAutopayRequestDto.setKey(config.getMerchantKey());

		logger.info("Uniquely generated txnid " + txnid);

		// Generate hash
		String hashString = config.getMerchantKey() + "|" + txnid + "|" + initiateAutopayRequestDto.getAmount() + "|"
				+ initiateAutopayRequestDto.getProductinfo() + "|" + initiateAutopayRequestDto.getFirstname() + "|"
				+ initiateAutopayRequestDto.getEmail() + "|" + initiateAutopayRequestDto.getUdf1() + "|"
				+ initiateAutopayRequestDto.getUdf2() + "|" + initiateAutopayRequestDto.getUdf3() + "|"
				+ initiateAutopayRequestDto.getUdf4() + "|" + initiateAutopayRequestDto.getUdf5() + "|"
				+ initiateAutopayRequestDto.getUdf6() + "|" + initiateAutopayRequestDto.getUdf7() + "|"
				+ initiateAutopayRequestDto.getUdf8() + "|" + initiateAutopayRequestDto.getUdf9() + "|"
				+ initiateAutopayRequestDto.getUdf10() + "|" + config.getMerchantSalt();

		String hash = HashUtil.generateHash(hashString);

		logger.info("Generated Hash: " + hash);
		initiateAutopayRequestDto.setHash(hash);

		// Prepare URL parameters
		String urlParameters = "key=" + config.getMerchantKey() + "&txnid=" + txnid + "&amount="
				+ initiateAutopayRequestDto.getAmount() + "&productinfo=" + initiateAutopayRequestDto.getProductinfo()
				+ "&firstname=" + initiateAutopayRequestDto.getFirstname() + "&phone="
				+ initiateAutopayRequestDto.getPhone() + "&email=" + initiateAutopayRequestDto.getEmail() + "&surl="
				+ config.getSurl() + "&furl=" + config.getFurl() + "&hash=" + hash

				+ "&udf1=" + initiateAutopayRequestDto.getUdf1() + "&udf2=" + initiateAutopayRequestDto.getUdf2()
				+ "&udf3=" + initiateAutopayRequestDto.getUdf3() + "&udf4=" + initiateAutopayRequestDto.getUdf4()
				+ "&udf5=" + initiateAutopayRequestDto.getUdf5() + "&udf6=" + initiateAutopayRequestDto.getUdf6()
				+ "&udf7=" + initiateAutopayRequestDto.getUdf7() + "&udf8=" + initiateAutopayRequestDto.getUdf8()

				+ "&udf9=" + initiateAutopayRequestDto.getUdf9() + "&udf10=" + initiateAutopayRequestDto.getUdf10() +

				"&address1=" + initiateAutopayRequestDto.getAddress1() + "&address2="
				+ initiateAutopayRequestDto.getAddress2() + "&city=" + initiateAutopayRequestDto.getCity() + "&state="
				+ initiateAutopayRequestDto.getState() + "&country=" + initiateAutopayRequestDto.getCountry()
				+ "&zipcode=" + initiateAutopayRequestDto.getZipcode() + "&customer_authentication_id="
				+ customerAuthenticationId + "&showpaymentmode=" + initiateAutopayRequestDto.getShow_payment_mode()
				+ "&submerchantid=" + initiateAutopayRequestDto.getSub_merchant_id() + "&requestflow="
				+ initiateAutopayRequestDto.getRequest_flow() + "&finalcollectiondate="
				+ initiateAutopayRequestDto.getFinal_collection_date();

		logger.info("Request for Autopay Register : {}", initiateAutopayRequestDto);
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
					responseObject.put("paymentUrl", paymentUrl);
					responseObject.put("txnid", txnid);
					responseObject.put("customer_authentication_id", customerAuthenticationId);
					responseObject.put("auto_debit_access_key", accessKey);
					logger.info("Response Body " + responseObject);
					return ResponseEntity.status(responseCode).body(responseObject.toString());

				} else {
					// Error scenario
					String errorDesc = jsonResponse.get("error_desc").asText();
					String data = jsonResponse.get("data").asText();

					ObjectNode errorResponse = mapper.createObjectNode();
					errorResponse.put("status", 0);
					errorResponse.put("error_desc", errorDesc);
					errorResponse.put("data", data);

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

				logger.error("Error          Response Body: " + response1);

				return ResponseEntity.status(responseCode).body(response1);
			}

		} catch (IOException e) {
			response1 = e.getMessage();

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
}
