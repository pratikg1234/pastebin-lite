package com.pastebinlite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response returned when fetching a paste")
public class PasteResponse {

    @Schema(
            description = "Paste content",
            example = "Hello, this is my paste"
    )
    private String content;

    @Schema(
            description = "Remaining number of allowed views",
            example = "4",
            nullable = true
    )
    @JsonProperty("remaining_views")
    private Integer remainingViews;

    @Schema(
            description = "Expiration timestamp in ISO-8601 format",
            example = "2025-12-31T23:59:59Z",
            nullable = true
    )
    @JsonProperty("expires_at")
    private String expiresAt;

    public PasteResponse() {
    }

    public PasteResponse(String content, Integer remainingViews, String expiresAt) {
        this.content = content;
        this.remainingViews = remainingViews;
        this.expiresAt = expiresAt;
    }

    public String getContent() {
        return content;
    }

    public Integer getRemainingViews() {
        return remainingViews;
    }

    public String getExpiresAt() {
        return expiresAt;
    }
}
