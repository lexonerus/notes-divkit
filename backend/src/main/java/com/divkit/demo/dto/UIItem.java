package com.divkit.demo.dto;

import java.util.Map;

public class UIItem {
    private String type;
    private String id;
    private String text;
    private String title;
    private String subtitle;
    private Object value;
    private Boolean enabled;
    private String error;
    private Map<String, Object> data;
    private Map<String, Object> properties;
    
    // Constructors
    public UIItem() {}
    
    public UIItem(String type, String id, String text) {
        this.type = type;
        this.id = id;
        this.text = text;
        this.enabled = true;
    }
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String type;
        private String id;
        private String text;
        private String title;
        private String subtitle;
        private Object value;
        private Boolean enabled = true;
        private String error;
        private Map<String, Object> data;
        private Map<String, Object> properties;
        
        public Builder type(String type) {
            this.type = type;
            return this;
        }
        
        public Builder id(String id) {
            this.id = id;
            return this;
        }
        
        public Builder text(String text) {
            this.text = text;
            return this;
        }
        
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        
        public Builder subtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }
        
        public Builder value(Object value) {
            this.value = value;
            return this;
        }
        
        public Builder enabled(Boolean enabled) {
            this.enabled = enabled;
            return this;
        }
        
        public Builder error(String error) {
            this.error = error;
            return this;
        }
        
        public Builder data(Map<String, Object> data) {
            this.data = data;
            return this;
        }
        
        public Builder properties(Map<String, Object> properties) {
            this.properties = properties;
            return this;
        }
        
        public UIItem build() {
            UIItem item = new UIItem();
            item.type = type;
            item.id = id;
            item.text = text;
            item.title = title;
            item.subtitle = subtitle;
            item.value = value;
            item.enabled = enabled;
            item.error = error;
            item.data = data;
            item.properties = properties;
            return item;
        }
    }
    
    // Getters and Setters
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getSubtitle() {
        return subtitle;
    }
    
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    
    public Object getValue() {
        return value;
    }
    
    public void setValue(Object value) {
        this.value = value;
    }
    
    public Boolean getEnabled() {
        return enabled;
    }
    
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
    public Map<String, Object> getProperties() {
        return properties;
    }
    
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
