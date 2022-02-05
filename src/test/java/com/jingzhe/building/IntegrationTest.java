package com.jingzhe.building;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Objects;

@AutoConfigureWebTestClient(timeout = "PT5M") // 5 minute timeout to all WebTestClient calls
public abstract class IntegrationTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public final void configureRestAssured(@Autowired(required = false) @LocalServerPort Integer port,
                                           @Autowired(required = false) ObjectMapper objectMapper) {
        Objects.requireNonNull(port);
        Objects.requireNonNull(objectMapper);

        //Use Spring default object mapper
        ObjectMapperConfig objectMapperConfig = RestAssuredConfig.config()
                .getObjectMapperConfig()
                .jackson2ObjectMapperFactory((cls, charset) -> objectMapper);
        RestAssured.config = RestAssured.config().objectMapperConfig(objectMapperConfig);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}