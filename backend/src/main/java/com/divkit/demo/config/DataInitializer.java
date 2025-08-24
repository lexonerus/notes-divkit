package com.divkit.demo.config;

import com.divkit.demo.model.Note;
import com.divkit.demo.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private NoteRepository noteRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Создаем тестовые заметки
        if (noteRepository.count() == 0) {
            noteRepository.save(new Note("Первая заметка", "Это моя первая заметка в приложении"));
            noteRepository.save(new Note("Покупки", "Молоко, хлеб, яйца"));
            noteRepository.save(new Note("Идеи", "Новые идеи для проектов"));
            
            System.out.println("Тестовые данные созданы!");
        }
    }
}
