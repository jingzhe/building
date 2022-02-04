package com.jingzhe.building.api;

import com.jingzhe.building.api.model.Building;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping(BuildingEndpoint.PATH)
public interface BuildingEndpoint {
    String PATH = "/buildings";
    String BUILDING_ID_PATH_VARIABLE = "building-id";

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
    Flux<Building> create(@RequestBody @Valid List<Building> buildings);

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
    Mono<Building> update(@Parameter(required = true, description = "Building ID") @PathVariable(BUILDING_ID_PATH_VARIABLE) String id,
                          @RequestBody @Valid Building building);

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
