package com.pastebinlite.controller;

import com.pastebinlite.model.Paste;
import com.pastebinlite.repository.PasteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
@Tag(name = "Paste View", description = "HTML-based paste rendering")
public class PasteViewController {

    private final PasteRepository repository;

    public PasteViewController(PasteRepository repository) {
        this.repository = repository;
    }

    @Operation(
            summary = "Render paste as HTML",
            description = "Returns paste content rendered as safe HTML inside <pre> tags"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Paste rendered successfully",
            content = @Content(
                    mediaType = "text/html",
                    schema = @Schema(
                            example = "<pre>Hello World</pre>"
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Paste not found"
    )
    @GetMapping(value = "/p/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> viewPaste(
            @Parameter(
                    description = "Paste ID",
                    example = "abc123",
                    required = true
            )
            @PathVariable String id
    ) {
        Paste paste = repository.findById(id).orElse(null);
        if (paste == null) {
            return ResponseEntity.status(404).build();
        }

        String safe = HtmlUtils.htmlEscape(paste.getContent());
        return ResponseEntity.ok("<pre>" + safe + "</pre>");
    }
}
