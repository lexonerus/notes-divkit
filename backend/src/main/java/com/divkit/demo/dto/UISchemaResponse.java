package com.divkit.demo.dto;

import java.util.Map;
import java.util.List;

public class UISchemaResponse {
    private String screen;
    private UISchema ui;
    private Map<String, Object> data;
    private NavigationConfig navigation;
    private Map<String, Object> actions;
    
    // Constructors
    public UISchemaResponse() {}
    
    public UISchemaResponse(String screen, UISchema ui, Map<String, Object> data, 
                           NavigationConfig navigation, Map<String, Object> actions) {
        this.screen = screen;
        this.ui = ui;
        this.data = data;
        this.navigation = navigation;
        this.actions = actions;
    }
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String screen;
        private UISchema ui;
        private Map<String, Object> data;
        private NavigationConfig navigation;
        private Map<String, Object> actions;
        
        public Builder screen(String screen) {
            this.screen = screen;
            return this;
        }
        
        public Builder ui(UISchema ui) {
            this.ui = ui;
            return this;
        }
        
        public Builder data(Map<String, Object> data) {
            this.data = data;
            return this;
        }
        
        public Builder navigation(NavigationConfig navigation) {
            this.navigation = navigation;
            return this;
        }
        
        public Builder actions(Map<String, Object> actions) {
            this.actions = actions;
            return this;
        }
        
        public UISchemaResponse build() {
            return new UISchemaResponse(screen, ui, data, navigation, actions);
        }
    }
    
    // Getters and Setters
    public String getScreen() {
        return screen;
    }
    
    public void setScreen(String screen) {
        this.screen = screen;
    }
    
    public UISchema getUi() {
        return ui;
    }
    
    public void setUi(UISchema ui) {
        this.ui = ui;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
    public NavigationConfig getNavigation() {
        return navigation;
    }
    
    public void setNavigation(NavigationConfig navigation) {
        this.navigation = navigation;
    }
    
    public Map<String, Object> getActions() {
        return actions;
    }
    
    public void setActions(Map<String, Object> actions) {
        this.actions = actions;
    }
}
