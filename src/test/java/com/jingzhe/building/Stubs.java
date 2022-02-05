package com.jingzhe.building;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.netty.handler.codec.http.HttpHeaders.Values.APPLICATION_JSON;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Slf4j
@UtilityClass
public class Stubs {

    private static final String JWKS_PATH_PATTERN = "/authentication/jwks";
    private static final String GEO_PATH_PATTERN = "/v1/geocode/.*";

    public static void stubForJwks() {
        stubFor(request("GET", urlMatching(JWKS_PATH_PATTERN))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withStatus(200)
                        .withBody(readFile("jwks.json"))));
    }

    public static void stubForGeoData(String address) {
        stubFor(request("GET", urlMatching(GEO_PATH_PATTERN))
                .withQueryParam("text", containing(address))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withStatus(200)
                        .withBody(readFile(address + "_geo.json"))));
    }

    public static void stubForGeoDataWithStatus(int status) {
        stubFor(request("GET", urlMatching(GEO_PATH_PATTERN))
                .willReturn(aResponse()
                        .withStatus(status)));
    }

    @SneakyThrows
    private String readFile(String name) {
        File resource = new ClassPathResource(name).getFile();
        return  Files.readString(resource.toPath(), StandardCharsets.UTF_8);
    }
}
