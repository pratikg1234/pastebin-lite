package com.pastebinlite.controller;
import com.pastebinlite.model.Paste;
import com.pastebinlite.repository.PasteRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

@RestController
public class PasteViewController {

    private final PasteRepository repository;

    public PasteViewController(PasteRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/p/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> viewPaste(@PathVariable String id) {
        Paste paste = repository.findById(id).orElse(null);
        if (paste == null) {
            return ResponseEntity.status(404).build();
        }

        String safe = HtmlUtils.htmlEscape(paste.getContent());
        return ResponseEntity.ok("<pre>" + safe + "</pre>");
    }
}

