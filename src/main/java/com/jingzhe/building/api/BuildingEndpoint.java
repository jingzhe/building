package com.jingzhe.building.api;

import com.jingzhe.building.api.model.BuildingDataRequest;
import com.jingzhe.building.api.model.BuildingDataResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RequestMapping(BuildingEndpoint.PATH)
public interface BuildingEndpoint {
    String PATH = "/buildings";
    String BUILDING_ID_PATH_VARIABLE = "building-id";
    String QUERY_NAME = "name";
    String QUERY_STREET = "street";
    String QUERY_NUMBER = "number";
    String QUERY_POST_CODE = "post_code";
    String QUERY_CITY = "city";
    String QUERY_COUNTRY = "country";
    String QUERY_LIMIT = "limit";
    String QUERY_OFFSET = "offset";

    @Operation(
            summary = "Create new buildings",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Created building IDs"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Input value invalid",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Bad request",
                                                    value = "{\"status\": \"BAD_REQUEST\", \"message\" : \"Input value invalid\"}"
                                            )
                                    }
                            )
                    )
            }
    )
    @HeaderApiParams
    @PostMapping(
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @CrossOrigin(allowCredentials = "true", originPatterns = "*")
    Flux<BuildingDataResponse> create(@RequestBody @Valid List<BuildingDataRequest> buildings);

    @Operation(
            summary = "Search buildings",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of buildings"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Input value invalid",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Bad request",
                                                    value = "{\"status\": \"BAD_REQUEST\", \"message\" : \"Input value invalid\"}"
                                            )
                                    }
                            )
                    )
            }
    )
    @HeaderApiParams
    @GetMapping(
            produces = APPLICATION_JSON_VALUE
    )
    @CrossOrigin(allowCredentials = "true", originPatterns = "*")
    Flux<BuildingDataResponse> search(@Parameter(description = "Building name") @RequestParam(required = false, value = QUERY_NAME) String name,
                              @Parameter(description = "Building street") @RequestParam(required = false, value = QUERY_STREET) String street,
                              @Parameter(description = "Building number") @RequestParam(required = false, value = QUERY_NUMBER) Integer number,
                              @Parameter(description = "Building post code") @RequestParam(required = false, value = QUERY_POST_CODE) String postCode,
                              @Parameter(description = "Building city") @RequestParam(required = false, value = QUERY_CITY) String city,
                              @Parameter(description = "Building country") @RequestParam(required = false, value = QUERY_COUNTRY) String country,
                              @Parameter(description = "Search limit") @RequestParam(required = false, value = QUERY_LIMIT) Integer limit,
                              @Parameter(description = "Search offset") @RequestParam(required = false, value = QUERY_OFFSET) Integer offset);


    @Operation(
            summary = "Update a building",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated build data"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Input value invalid",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Bad request",
                                                    value = "{\"status\": \"BAD_REQUEST\", \"message\" : \"Input value invalid\"}"
                                            )
                                    }
                            )
                    )
            }
    )
    @HeaderApiParams
    @PutMapping(
            value = "/{" + BUILDING_ID_PATH_VARIABLE + "}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    Mono<BuildingDataResponse> update(@Parameter(required = true, description = "Building ID") @PathVariable(BUILDING_ID_PATH_VARIABLE) String id,
                                     @RequestBody @Valid BuildingDataRequest building);

    @Operation(
            summary = "delete a building",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Delete a building"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Input value invalid",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Bad request",
                                                    value = "{\"status\": \"BAD_REQUEST\", \"message\" : \"Input value invalid\"}"
                                            )
                                    }
                            )
                    )
            }
    )
    @HeaderApiParams
    @DeleteMapping(
            value = "/{" + BUILDING_ID_PATH_VARIABLE + "}"
    )
    Mono<Void> delete(@Parameter(required = true, description = "Building ID") @PathVariable(BUILDING_ID_PATH_VARIABLE) String id);
}
