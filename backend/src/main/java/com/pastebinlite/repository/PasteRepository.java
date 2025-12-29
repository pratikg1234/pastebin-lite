package com.pastebinlite.repository;

import com.pastebinlite.model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasteRepository extends JpaRepository<Paste, String> {
}
