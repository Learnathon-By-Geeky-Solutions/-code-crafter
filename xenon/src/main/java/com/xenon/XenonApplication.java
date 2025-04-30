package com.xenon;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XenonApplication {

    public static void main(String[] args) {
        SpringApplication.run(XenonApplication.class, args);
    }

}
