package com.saswat.autopay.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class SignzyStatusController {

    private static final Logger logger = LoggerFactory.getLogger(SignzyStatusController.class);

    @PostMapping("/v1/success-redirect")
    public String successRedirect(@RequestBody(required = false) String str) {
        logger.info("Success: {}", str);
        return "Payment is successful"; // Simple response
    }

    @PostMapping("/v1/failure-redirect")
    public String failureRedirect(@RequestBody(required = false) String str) {
        logger.info("Failure: {}", str);
        return "Payment has failed"; // Simple response
    }

    @PostMapping("/v1/callback")
    public String callbackURL(@RequestBody(required = false) String str) {
        logger.info("Callback received: {}", str);
        return "Callback processed"; // Simple response
    }
}
