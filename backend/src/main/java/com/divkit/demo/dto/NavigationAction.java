package com.divkit.demo.dto;

import java.util.Map;

public class NavigationAction {
    private String action;
    private String screen;
    private Map<String, Object> params;
    
    // Constructors
    public NavigationAction() {}
    
    public NavigationAction(String action, String screen, Map<String, Object> params) {
        this.action = action;
        this.screen = screen;
        this.params = params;
    }
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String action;
        private String screen;
        private Map<String, Object> params;
        
        public Builder action(String action) {
            this.action = action;
            return this;
        }
        
        public Builder screen(String screen) {
            this.screen = screen;
            return this;
        }
        
        public Builder params(Map<String, Object> params) {
            this.params = params;
            return this;
        }
        
        public NavigationAction build() {
            return new NavigationAction(action, screen, params);
        }
    }
    
    // Getters and Setters
    public String getAction() {
        return action;
    }
    
    public void setAction(String action) {
        this.action = action;
    }
    
    public String getScreen() {
        return screen;
    }
    
    public void setScreen(String screen) {
        this.screen = screen;
    }
    
    public Map<String, Object> getParams() {
        return params;
    }
    
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
