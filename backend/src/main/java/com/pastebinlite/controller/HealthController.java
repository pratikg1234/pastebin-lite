package com.pastebinlite.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Health", description = "Application health and readiness checks")
public class HealthController {

    private final DataSource dataSource;

    public HealthController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Operation(
            summary = "Health check endpoint",
            description = "Checks whether the application and database connection are healthy"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Application is reachable",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = "{ \"ok\": true }"
                    )
            )
    )
    @GetMapping("/healthz")
    public Map<String, Object> health() {
        try (var connection = dataSource.getConnection()) {
            return Map.of("ok", true);
        } catch (Exception e) {
            return Map.of("ok", false);
        }
    }
}
