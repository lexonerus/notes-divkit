package com.divkit.demo.controller;

import com.divkit.demo.dto.ActionRequest;
import com.divkit.demo.dto.ActionResponse;
import com.divkit.demo.dto.UISchemaResponse;
import com.divkit.demo.service.UISchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ui")
@CrossOrigin(origins = "*") // Для разработки
public class UISchemaController {
    
    @Autowired
    private UISchemaService uiSchemaService;
    
    @GetMapping("/screen/{screenName}")
    public ResponseEntity<UISchemaResponse> getScreenSchema(
            @PathVariable String screenName,
            @RequestParam Map<String, String> params) {
        
        try {
            UISchemaResponse schema = uiSchemaService.generateSchema(screenName, params);
            return ResponseEntity.ok(schema);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/action")
    public ResponseEntity<ActionResponse> handleAction(@RequestBody ActionRequest request) {
        try {
            ActionResponse response = uiSchemaService.processAction(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ActionResponse errorResponse = ActionResponse.builder()
                    .success(false)
                    .message("Error: " + e.getMessage())
                    .build();
            return ResponseEntity.ok(errorResponse);
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }
}
