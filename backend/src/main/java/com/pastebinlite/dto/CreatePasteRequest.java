package com.pastebinlite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body for creating a new paste")
public class CreatePasteRequest {

    @Schema(
            description = "Paste content",
            example = "Hello, this is my paste"
    )
    @NotBlank
    private String content;

    @Schema(
            description = "Time-to-live in seconds",
            example = "60",
            minimum = "1",
            nullable = true
    )
    @Min(1)
    @JsonProperty("ttl_seconds")
    private Integer ttl_Seconds;

    @Schema(
            description = "Maximum number of allowed views",
            example = "5",
            minimum = "1",
            nullable = true
    )
    @Min(1)
    @JsonProperty("max_views")
    private Integer max_Views;

    public CreatePasteRequest() {
    }

    public CreatePasteRequest(String content, Integer ttlSeconds, Integer maxViews) {
        this.content = content;
        this.ttl_Seconds = ttlSeconds;
        this.max_Views = maxViews;
    }

    public String getContent() {
        return content;
    }

    public Integer getTtl_Seconds() {
        return ttl_Seconds;
    }

    public Integer getMax_Views() {
        return max_Views;
    }
}
