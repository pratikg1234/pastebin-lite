package com.pastebinlite.controller;

import com.pastebinlite.dto.CreatePasteRequest;
import com.pastebinlite.dto.PasteResponse;
import com.pastebinlite.model.Paste;
import com.pastebinlite.repository.PasteRepository;
import com.pastebinlite.util.TimeUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pastes")
@Tag(name = "Pastes", description = "Paste creation and retrieval APIs")
public class PasteApiController {

    private final PasteRepository repository;

    private String frontendBaseUrl = System.getenv()
        .getOrDefault("FRONTEND_BASE_URL", "http://localhost:5173");

    public PasteApiController(PasteRepository repository) {
        this.repository = repository;
    }

    // ---------------- CREATE PASTE ----------------

    @Operation(
            summary = "Create a new paste",
            description = "Creates a paste with optional TTL and max view limits"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Paste created successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = "{ \"id\": \"abc123\", \"url\": \"http://localhost:3000/p/abc123\" }"
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Invalid input",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = "{ \"error\": \"content required\" }"
                    )
            )
    )
    @PostMapping
    public ResponseEntity<?> createPaste(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Paste creation request"
            )
            @RequestBody CreatePasteRequest req,
            HttpServletRequest request
    ) {
        if (req.getContent() == null || req.getContent().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "content required"));
        }

        if (req.getTtl_Seconds() != null && req.getTtl_Seconds() < 1) {
            return ResponseEntity.badRequest().body(Map.of("error", "invalid ttl_seconds"));
        }

        if (req.getMax_Views() != null && req.getMax_Views() < 1) {
            return ResponseEntity.badRequest().body(Map.of("error", "invalid max_views"));
        }

        long now = TimeUtil.now(request);

        Paste paste = new Paste();
        paste.setId(UUID.randomUUID().toString().replace("-", ""));
        paste.setContent(req.getContent());
        paste.setCreatedAt(now);
        paste.setViewCount(0);
        paste.setMaxViews(req.getMax_Views());

        if (req.getTtl_Seconds() != null) {
            paste.setExpiresAt(now + req.getTtl_Seconds() * 1000L);
        }

        repository.save(paste);

        return ResponseEntity.ok(Map.of(
                "id", paste.getId(),
                "url", frontendBaseUrl + "/p/" + paste.getId()
        ));
    }

    // ---------------- GET PASTE ----------------

    @Operation(
            summary = "Fetch a paste by ID",
            description = "Returns paste content and remaining views. View count is incremented."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Paste retrieved successfully",
            content = @Content(schema = @Schema(implementation = PasteResponse.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "missing paste / expired paste/ view limit exceeded",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = "{ \"error\": \"missing paste\" }"
                    )
            )
    )
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<?> getPaste(
            @Parameter(
                    description = "Paste ID",
                    example = "abc123",
                    required = true
            )
            @PathVariable String id,
            HttpServletRequest request
    ) {
        Paste paste = repository.findById(id).orElse(null);

        if (paste == null) {
            return ResponseEntity.status(404).body(Map.of("error", "missing paste"));
        }

        long now = TimeUtil.now(request);

        if (paste.getExpiresAt() != null && now >= paste.getExpiresAt()) {
            return ResponseEntity.status(404).body(Map.of("error", "expired paste"));
        }

        if (paste.getMaxViews() != null && paste.getViewCount() >= paste.getMaxViews()) {
            return ResponseEntity.status(404).body(Map.of("error", "view limit exceeded"));
        }

        paste.setViewCount(paste.getViewCount() + 1);

        Integer remaining = paste.getMaxViews() == null
                ? null
                : Math.max(0, paste.getMaxViews() - paste.getViewCount());

        return ResponseEntity.ok(new PasteResponse(
                paste.getContent(),
                remaining,
                paste.getExpiresAt() == null
                        ? null
                        : Instant.ofEpochMilli(paste.getExpiresAt()).toString()
        ));
    }
}
