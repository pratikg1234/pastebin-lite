package com.pastebinlite.dto;

public class PasteResponse {

    private String content;
    private Integer remaining_views;
    private String expires_at;

    public PasteResponse(String content, Integer remaining_views, String expires_at) {
        this.content = content;
        this.remaining_views = remaining_views;
        this.expires_at = expires_at;
    }

    public String getContent() {
        return content;
    }

    public Integer getRemaining_views() {
        return remaining_views;
    }

    public String getExpires_at() {
        return expires_at;
    }
}
