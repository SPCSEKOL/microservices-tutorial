package com.nn.home.microservices.currencyexchangeservice.core;

import com.nn.home.microservices.currencyexchangeservice.model.CurrencyExchange;
import com.nn.home.microservices.currencyexchangeservice.repository.CurrencyExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CurrencyExchangeController {

    private static final Logger log = LoggerFactory.getLogger(CurrencyExchangeController.class);
    private Environment env;
    private CurrencyExchangeRepository ceRepository;

    @Autowired
    public CurrencyExchangeController(Environment environment, CurrencyExchangeRepository currencyExchangeRepository) {
        this.env = environment;
        this.ceRepository = currencyExchangeRepository;
    }

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange getExchangeValue(@PathVariable String from, @PathVariable String to) {
        log.info("getExchangeValue is called with from {} to {}", from, to);
        CurrencyExchange currencyExchange = ceRepository.findByFromAndTo(from, to);
        if (currencyExchange == null) {
            throw new RuntimeException("Unable to find data for "+from+" and "+to);
        }
        String port = env.getProperty("local.server.port", "");
        currencyExchange.setEnvironment(port);
        return currencyExchange;
    }
}
