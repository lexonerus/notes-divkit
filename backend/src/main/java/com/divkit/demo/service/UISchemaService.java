package com.divkit.demo.service;

import com.divkit.demo.dto.*;
import com.divkit.demo.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UISchemaService {
    
    @Autowired
    private NoteService noteService;
    
    public UISchemaResponse generateSchema(String screenName, Map<String, String> params) {
        switch (screenName) {
            case "notes_list":
                return generateNotesListSchema();
            case "note_create":
                return generateNoteCreateSchema();
            case "note_edit":
                Long noteId = Long.parseLong(params.get("id"));
                return generateNoteEditSchema(noteId);
            case "note_view":
                Long viewNoteId = Long.parseLong(params.get("id"));
                return generateNoteViewSchema(viewNoteId);
            default:
                throw new IllegalArgumentException("Unknown screen: " + screenName);
        }
    }
    
    public ActionResponse processAction(ActionRequest request) {
        try {
            switch (request.getType()) {
                case "create_note":
                    return handleCreateNote(request);
                case "update_note":
                    return handleUpdateNote(request);
                case "delete_note":
                    return handleDeleteNote(request);
                case "navigate":
                    return handleNavigation(request);
                default:
                    return ActionResponse.builder()
                            .success(false)
                            .message("Unknown action: " + request.getType())
                            .build();
            }
        } catch (Exception e) {
            return ActionResponse.builder()
                    .success(false)
                    .message("Error: " + e.getMessage())
                    .build();
        }
    }
    
    private UISchemaResponse generateNotesListSchema() {
        List<Note> notes = noteService.getAllNotes();
        
        List<UIItem> items = new ArrayList<>();
        
        // Добавляем заголовок
        items.add(UIItem.builder()
                .type("header")
                .id("header")
                .title("Мои заметки")
                .subtitle("Всего заметок: " + notes.size())
                .build());
        
        // Добавляем заметки
        for (Note note : notes) {
            items.add(UIItem.builder()
                    .type("note_item")
                    .id("note_" + note.getId())
                    .title(note.getTitle())
                    .subtitle(truncateContent(note.getContent(), 50))
                    .data(Map.of("note_id", note.getId()))
                    .build());
        }
        
        // Добавляем кнопку создания
        items.add(UIItem.builder()
                .type("button")
                .id("create_note_btn")
                .text("Создать заметку")
                .build());
        
        UISchema ui = UISchema.builder()
                .type("list")
                .items(items)
                .build();
        
        NavigationConfig navigation = NavigationConfig.builder()
                .allowedScreens(Arrays.asList("notes_list", "note_create", "note_edit", "note_view"))
                .actions(Map.of(
                    "create_note_btn", NavigationAction.builder()
                            .action("navigate")
                            .screen("note_create")
                            .build(),
                    "note_item", NavigationAction.builder()
                            .action("navigate")
                            .screen("note_view")
                            .build()
                ))
                .build();
        
        return UISchemaResponse.builder()
                .screen("notes_list")
                .ui(ui)
                .data(Map.of("notes_count", notes.size()))
                .navigation(navigation)
                .build();
    }
    
    private UISchemaResponse generateNoteCreateSchema() {
        List<UIItem> items = Arrays.asList(
            UIItem.builder()
                    .type("header")
                    .id("header")
                    .title("Создать заметку")
                    .build(),
            UIItem.builder()
                    .type("input")
                    .id("title_input")
                    .text("Заголовок")
                    .value("")
                    .enabled(true)
                    .build(),
            UIItem.builder()
                    .type("textarea")
                    .id("content_input")
                    .text("Содержание")
                    .value("")
                    .enabled(true)
                    .build(),
            UIItem.builder()
                    .type("button")
                    .id("save_btn")
                    .text("Сохранить")
                    .enabled(true)
                    .build(),
            UIItem.builder()
                    .type("button")
                    .id("cancel_btn")
                    .text("Отмена")
                    .enabled(true)
                    .build()
        );
        
        UISchema ui = UISchema.builder()
                .type("form")
                .items(items)
                .build();
        
        NavigationConfig navigation = NavigationConfig.builder()
                .backScreen("notes_list")
                .allowedScreens(Arrays.asList("notes_list", "note_create"))
                .build();
        
        return UISchemaResponse.builder()
                .screen("note_create")
                .ui(ui)
                .navigation(navigation)
                .build();
    }
    
    private UISchemaResponse generateNoteEditSchema(Long noteId) {
        Optional<Note> noteOpt = noteService.getNoteById(noteId);
        if (noteOpt.isEmpty()) {
            throw new RuntimeException("Note not found with id: " + noteId);
        }
        
        Note note = noteOpt.get();
        
        List<UIItem> items = Arrays.asList(
            UIItem.builder()
                    .type("header")
                    .id("header")
                    .title("Редактировать заметку")
                    .build(),
            UIItem.builder()
                    .type("input")
                    .id("title_input")
                    .text("Заголовок")
                    .value(note.getTitle())
                    .enabled(true)
                    .build(),
            UIItem.builder()
                    .type("textarea")
                    .id("content_input")
                    .text("Содержание")
                    .value(note.getContent())
                    .enabled(true)
                    .build(),
            UIItem.builder()
                    .type("button")
                    .id("save_btn")
                    .text("Сохранить")
                    .enabled(true)
                    .build(),
            UIItem.builder()
                    .type("button")
                    .id("cancel_btn")
                    .text("Отмена")
                    .enabled(true)
                    .build(),
            UIItem.builder()
                    .type("button")
                    .id("delete_btn")
                    .text("Удалить")
                    .enabled(true)
                    .build()
        );
        
        UISchema ui = UISchema.builder()
                .type("form")
                .items(items)
                .build();
        
        NavigationConfig navigation = NavigationConfig.builder()
                .backScreen("notes_list")
                .allowedScreens(Arrays.asList("notes_list", "note_edit"))
                .build();
        
        return UISchemaResponse.builder()
                .screen("note_edit")
                .ui(ui)
                .data(Map.of("note_id", noteId))
                .navigation(navigation)
                .build();
    }
    
    private UISchemaResponse generateNoteViewSchema(Long noteId) {
        Optional<Note> noteOpt = noteService.getNoteById(noteId);
        if (noteOpt.isEmpty()) {
            throw new RuntimeException("Note not found with id: " + noteId);
        }
        
        Note note = noteOpt.get();
        
        List<UIItem> items = Arrays.asList(
            UIItem.builder()
                    .type("header")
                    .id("header")
                    .title(note.getTitle())
                    .subtitle("Создано: " + note.getCreatedAt())
                    .build(),
            UIItem.builder()
                    .type("text")
                    .id("content")
                    .text(note.getContent())
                    .build(),
            UIItem.builder()
                    .type("button")
                    .id("edit_btn")
                    .text("Редактировать")
                    .enabled(true)
                    .build(),
            UIItem.builder()
                    .type("button")
                    .id("back_btn")
                    .text("Назад")
                    .enabled(true)
                    .build()
        );
        
        UISchema ui = UISchema.builder()
                .type("detail")
                .items(items)
                .build();
        
        NavigationConfig navigation = NavigationConfig.builder()
                .backScreen("notes_list")
                .allowedScreens(Arrays.asList("notes_list", "note_edit", "note_view"))
                .actions(Map.of(
                    "edit_btn", NavigationAction.builder()
                            .action("navigate")
                            .screen("note_edit")
                            .params(Map.of("id", noteId))
                            .build()
                ))
                .build();
        
        return UISchemaResponse.builder()
                .screen("note_view")
                .ui(ui)
                .data(Map.of("note_id", noteId))
                .navigation(navigation)
                .build();
    }
    
    private ActionResponse handleCreateNote(ActionRequest request) {
        String title = (String) request.getData().get("title");
        String content = (String) request.getData().get("content");
        
        if (title == null || title.trim().isEmpty()) {
            return ActionResponse.builder()
                    .success(false)
                    .message("Заголовок не может быть пустым")
                    .build();
        }
        
        Note note = noteService.createNote(title, content);
        
        return ActionResponse.builder()
                .success(true)
                .message("Заметка создана успешно")
                .nextScreen("notes_list")
                .build();
    }
    
    private ActionResponse handleUpdateNote(ActionRequest request) {
        Long noteId = Long.parseLong(request.getData().get("note_id").toString());
        String title = (String) request.getData().get("title");
        String content = (String) request.getData().get("content");
        
        if (title == null || title.trim().isEmpty()) {
            return ActionResponse.builder()
                    .success(false)
                    .message("Заголовок не может быть пустым")
                    .build();
        }
        
        Note note = noteService.updateNote(noteId, title, content);
        
        return ActionResponse.builder()
                .success(true)
                .message("Заметка обновлена успешно")
                .nextScreen("notes_list")
                .build();
    }
    
    private ActionResponse handleDeleteNote(ActionRequest request) {
        Long noteId = Long.parseLong(request.getData().get("note_id").toString());
        noteService.deleteNote(noteId);
        
        return ActionResponse.builder()
                .success(true)
                .message("Заметка удалена успешно")
                .nextScreen("notes_list")
                .build();
    }
    
    private ActionResponse handleNavigation(ActionRequest request) {
        String screen = (String) request.getData().get("screen");
        Map<String, Object> params = (Map<String, Object>) request.getData().get("params");
        
        return ActionResponse.builder()
                .success(true)
                .nextScreen(screen)
                .data(params)
                .build();
    }
    
    private String truncateContent(String content, int maxLength) {
        if (content.length() <= maxLength) {
            return content;
        }
        return content.substring(0, maxLength) + "...";
    }
}
