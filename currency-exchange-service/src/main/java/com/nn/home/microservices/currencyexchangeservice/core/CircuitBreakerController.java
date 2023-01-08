package com.nn.home.microservices.currencyexchangeservice.core;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {
    private static final Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-msg")
    //@Retry(name = "sampleMsg", fallbackMethod = "customizedErrors")
    //@CircuitBreaker(name = "default", fallbackMethod = "customizedErrors")
    //@RateLimiter(name = "default")
    @Bulkhead(name = "default")
    public String sampleMessage() {
        logger.info("Inside sample message API");
        //ResponseEntity<String> responseEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy-api", String.class);
        return "sample-message";
    }

    public String customizedErrors(Throwable throwable) {
        return "Nothing to show since error has occurred.";
    }
}
