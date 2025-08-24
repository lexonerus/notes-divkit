package com.divkit.demo.service;

import com.divkit.demo.model.Note;
import com.divkit.demo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;
    
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
    
    public Optional<Note> getNoteById(Long id) {
        return noteRepository.findById(id);
    }
    
    public Note createNote(String title, String content) {
        Note note = new Note(title, content);
        return noteRepository.save(note);
    }
    
    public Note updateNote(Long id, String title, String content) {
        Optional<Note> existingNote = noteRepository.findById(id);
        if (existingNote.isPresent()) {
            Note note = existingNote.get();
            note.setTitle(title);
            note.setContent(content);
            return noteRepository.save(note);
        }
        throw new RuntimeException("Note not found with id: " + id);
    }
    
    public void deleteNote(Long id) {
        if (noteRepository.existsById(id)) {
            noteRepository.deleteById(id);
        } else {
            throw new RuntimeException("Note not found with id: " + id);
        }
    }
    
    public boolean noteExists(Long id) {
        return noteRepository.existsById(id);
    }
}
