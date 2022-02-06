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
    String QUERY_SORT_BY = "sort_by";
    String QUERY_ORDER = "order";

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
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Internal error",
                                                    value = "{\"status\": \"INTERNAL_SERVER_ERROR\", \"message\" : \"Failed to connect to Database\"}"
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
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Internal error",
                                                    value = "{\"status\": \"INTERNAL_SERVER_ERROR\", \"message\" : \"Failed to connect to Database\"}"
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
                                      @Parameter(description = "Search offset") @RequestParam(required = false, value = QUERY_OFFSET) Integer offset,
                                      @Parameter(description = "Sort by") @RequestParam(required = false, value = QUERY_SORT_BY) String sortBy,
                                      @Parameter(description = "Order") @RequestParam(required = false, value = QUERY_ORDER) String order);

    @Operation(
            summary = "Fetch a building information",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Fetched build data"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Building not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Not found",
                                                    value = "{\"status\": \"NOT_FOUND\", \"message\" : \"Building not found\"}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Internal error",
                                                    value = "{\"status\": \"INTERNAL_SERVER_ERROR\", \"message\" : \"Failed to connect to Database\"}"
                                            )
                                    }
                            )
                    )
            }
    )
    @HeaderApiParams
    @GetMapping(
            value = "/{" + BUILDING_ID_PATH_VARIABLE + "}",
            produces = APPLICATION_JSON_VALUE
    )
    @CrossOrigin(allowCredentials = "true", originPatterns = "*")
    Mono<BuildingDataResponse> getBuilding(@Parameter(required = true, description = "Building ID") @PathVariable(BUILDING_ID_PATH_VARIABLE) String id);

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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Building not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Not found",
                                                    value = "{\"status\": \"NOT_FOUND\", \"message\" : \"Building not found\"}"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Internal error",
                                                    value = "{\"status\": \"INTERNAL_SERVER_ERROR\", \"message\" : \"Failed to connect to Database\"}"
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
    @CrossOrigin(allowCredentials = "true", originPatterns = "*")
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
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Internal server error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiResponse.class),
                                    examples = {
                                            @ExampleObject(
                                                    name = "Internal error",
                                                    value = "{\"status\": \"INTERNAL_SERVER_ERROR\", \"message\" : \"Failed to connect to Database\"}"
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
    @CrossOrigin(allowCredentials = "true", originPatterns = "*")
    Mono<Void> delete(@Parameter(required = true, description = "Building ID") @PathVariable(BUILDING_ID_PATH_VARIABLE) String id);
}
