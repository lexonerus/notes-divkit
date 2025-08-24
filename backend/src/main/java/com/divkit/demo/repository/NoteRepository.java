package com.divkit.demo.repository;

import com.divkit.demo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // Базовые CRUD операции предоставляются JpaRepository
}
