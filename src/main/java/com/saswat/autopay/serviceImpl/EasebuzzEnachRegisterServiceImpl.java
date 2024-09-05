package com.saswat.autopay.serviceImpl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.saswat.autopay.Utils.PropertiesConfig;
import com.saswat.autopay.dto.InitiateEnachRequestDto;
import com.saswat.autopay.service.EasebuzzEnachRegisterService;

@Service
public class EasebuzzEnachRegisterServiceImpl implements EasebuzzEnachRegisterService {

	@Autowired
	PropertiesConfig config;

	private static final Logger logger = LoggerFactory.getLogger(EasebuzzEnachRegisterServiceImpl.class);

	@Override
	public ResponseEntity<String> registerEnach(InitiateEnachRequestDto initiateEnachRequestDto) {

		String UrlString = config.getInitiateEnachpayUrl();

		String urlParameters = "access_key=" + initiateEnachRequestDto.getAccess_key() + "&payment_mode="
				+ initiateEnachRequestDto.getPayment_mode() + "&ifsc=" + initiateEnachRequestDto.getIfsc()
				+ "&account_type=" + initiateEnachRequestDto.getAccount_type() + "&account_no="
				+ initiateEnachRequestDto.getAccount_no() + "&auth_mode=" + initiateEnachRequestDto.getAuth_mode()
				+ "&bank_code=" + initiateEnachRequestDto.getBank_code();

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

				return ResponseEntity.status(responseCode).body(response1);

			} else {
				try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
				}
				response1 = response.toString();

				logger.error("Error Response Body: " + response1);

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

}
