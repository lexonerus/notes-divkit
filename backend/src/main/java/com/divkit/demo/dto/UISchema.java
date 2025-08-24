package com.divkit.demo.dto;

import java.util.List;
import java.util.Map;

public class UISchema {
    private String type;
    private List<UIItem> items;
    private Map<String, Object> properties;
    
    // Constructors
    public UISchema() {}
    
    public UISchema(String type, List<UIItem> items, Map<String, Object> properties) {
        this.type = type;
        this.items = items;
        this.properties = properties;
    }
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String type;
        private List<UIItem> items;
        private Map<String, Object> properties;
        
        public Builder type(String type) {
            this.type = type;
            return this;
        }
        
        public Builder items(List<UIItem> items) {
            this.items = items;
            return this;
        }
        
        public Builder properties(Map<String, Object> properties) {
            this.properties = properties;
            return this;
        }
        
        public UISchema build() {
            return new UISchema(type, items, properties);
        }
    }
    
    // Getters and Setters
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public List<UIItem> getItems() {
        return items;
    }
    
    public void setItems(List<UIItem> items) {
        this.items = items;
    }
    
    public Map<String, Object> getProperties() {
        return properties;
    }
    
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
