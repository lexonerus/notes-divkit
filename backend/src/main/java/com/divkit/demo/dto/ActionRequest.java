package com.divkit.demo.dto;

import java.util.Map;

public class ActionRequest {
    private String type;
    private Map<String, Object> data;
    private String screen;
    
    // Constructors
    public ActionRequest() {}
    
    public ActionRequest(String type, Map<String, Object> data, String screen) {
        this.type = type;
        this.data = data;
        this.screen = screen;
    }
    
    // Getters and Setters
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
    public String getScreen() {
        return screen;
    }
    
    public void setScreen(String screen) {
        this.screen = screen;
    }
}
