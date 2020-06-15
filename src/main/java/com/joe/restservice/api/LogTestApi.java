package com.joe.restservice.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestApi {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/log")
    public String log(){

        String name = "Joe";
        String email = "1900849@system.com";
        logger.info("info --- log");
        logger.warn("wrn --- log");
        logger.error("error --- log");
        logger.debug("debug --- log");
        logger.trace("trace --- log");
        logger.info("name : {}, email : {}", name,email);


        return "logtest";
    }
}
