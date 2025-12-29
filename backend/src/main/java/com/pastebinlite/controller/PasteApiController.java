package com.pastebinlite.controller;
import com.pastebinlite.dto.CreatePasteRequest;
import com.pastebinlite.dto.PasteResponse;
import com.pastebinlite.model.Paste;
import com.pastebinlite.repository.PasteRepository;
import com.pastebinlite.util.TimeUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/pastes")
public class PasteApiController {

    private final PasteRepository repository;

    public PasteApiController(PasteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<?> createPaste(
            @RequestBody CreatePasteRequest req,
            HttpServletRequest request
    ) {
        if (req.getContent() == null || req.getContent().trim().isEmpty()) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "content required");
            return ResponseEntity.badRequest().body(body);
        }

        if (req.getTtl_seconds() != null && req.getTtl_seconds() < 1) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "invalid ttl_seconds");
            return ResponseEntity.badRequest().body(body);
        }

        if (req.getMax_views() != null && req.getMax_views() < 1) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "invalid max_views");
            return ResponseEntity.badRequest().body(body);
        }

        long now = TimeUtil.now(request);

        Paste paste = new Paste();
        paste.setId(UUID.randomUUID().toString().replace("-", ""));
        paste.setContent(req.getContent());
        paste.setCreatedAt(now);
        paste.setViewCount(0);
        paste.setMaxViews(req.getMax_views());

        if (req.getTtl_seconds() != null) {
            paste.setExpiresAt(now + req.getTtl_seconds() * 1000L);
        }

        repository.save(paste);

        Map<String, String> body = new HashMap<>();
        body.put("id", paste.getId());
        body.put("url", System.getenv("FRONTEND_BASE_URL") + "/p/" + paste.getId());
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity<?> getPaste(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        Paste paste = repository.findById(id).orElse(null);

        if (paste == null) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "not found");
            return ResponseEntity.status(404).body(body);
        }

        long now = TimeUtil.now(request);

        if (paste.getExpiresAt() != null && now >= paste.getExpiresAt()) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "expired");
            return ResponseEntity.status(404).body(body);
        }

        if (paste.getMaxViews() != null &&
                paste.getViewCount() >= paste.getMaxViews()) {
            Map<String, String> body = new HashMap<>();
            body.put("error", "view limit exceeded");
            return ResponseEntity.status(404).body(body);
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

