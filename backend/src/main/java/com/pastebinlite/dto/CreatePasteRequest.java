package com.pastebinlite.dto;

public class CreatePasteRequest {

    private String content;
    private Integer ttl_seconds;
    private Integer max_views;

    public CreatePasteRequest(String content, Integer ttl_seconds, Integer max_views) {
        this.content = content;
        this.ttl_seconds = ttl_seconds;
        this.max_views = max_views;
    }

    public String getContent() {
        return content;
    }

    public Integer getTtl_seconds() {
        return ttl_seconds;
    }

    public Integer getMax_views() {
        return max_views;
    }
}
