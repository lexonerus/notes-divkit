package com.divkit.demo.dto;

import java.util.Map;

public class ActionResponse {
    private Boolean success;
    private String message;
    private String nextScreen;
    private UISchema uiUpdate;
    private Map<String, Object> data;
    
    // Constructors
    public ActionResponse() {}
    
    public ActionResponse(Boolean success, String message, String nextScreen, 
                         UISchema uiUpdate, Map<String, Object> data) {
        this.success = success;
        this.message = message;
        this.nextScreen = nextScreen;
        this.uiUpdate = uiUpdate;
        this.data = data;
    }
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private Boolean success;
        private String message;
        private String nextScreen;
        private UISchema uiUpdate;
        private Map<String, Object> data;
        
        public Builder success(Boolean success) {
            this.success = success;
            return this;
        }
        
        public Builder message(String message) {
            this.message = message;
            return this;
        }
        
        public Builder nextScreen(String nextScreen) {
            this.nextScreen = nextScreen;
            return this;
        }
        
        public Builder uiUpdate(UISchema uiUpdate) {
            this.uiUpdate = uiUpdate;
            return this;
        }
        
        public Builder data(Map<String, Object> data) {
            this.data = data;
            return this;
        }
        
        public ActionResponse build() {
            return new ActionResponse(success, message, nextScreen, uiUpdate, data);
        }
    }
    
    // Getters and Setters
    public Boolean getSuccess() {
        return success;
    }
    
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getNextScreen() {
        return nextScreen;
    }
    
    public void setNextScreen(String nextScreen) {
        this.nextScreen = nextScreen;
    }
    
    public UISchema getUiUpdate() {
        return uiUpdate;
    }
    
    public void setUiUpdate(UISchema uiUpdate) {
        this.uiUpdate = uiUpdate;
    }
    
    public Map<String, Object> getData() {
        return data;
    }
    
    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
