package com.jingzhe.building.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.http.HttpHeaders;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.HEADER;

@Parameters({
        @Parameter(
                description = "Access token",
                name = HttpHeaders.AUTHORIZATION,
                in = HEADER,
                example = "Bearer JWT",
                required = true
        ),
})
@Retention(RetentionPolicy.RUNTIME)
@interface HeaderApiParams {
}
