package com.divkit.demo.dto;

import java.util.List;
import java.util.Map;

public class NavigationConfig {
    private String nextScreen;
    private String backScreen;
    private List<String> allowedScreens;
    private Map<String, NavigationAction> actions;
    
    // Constructors
    public NavigationConfig() {}
    
    public NavigationConfig(String nextScreen, String backScreen, 
                           List<String> allowedScreens, Map<String, NavigationAction> actions) {
        this.nextScreen = nextScreen;
        this.backScreen = backScreen;
        this.allowedScreens = allowedScreens;
        this.actions = actions;
    }
    
    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String nextScreen;
        private String backScreen;
        private List<String> allowedScreens;
        private Map<String, NavigationAction> actions;
        
        public Builder nextScreen(String nextScreen) {
            this.nextScreen = nextScreen;
            return this;
        }
        
        public Builder backScreen(String backScreen) {
            this.backScreen = backScreen;
            return this;
        }
        
        public Builder allowedScreens(List<String> allowedScreens) {
            this.allowedScreens = allowedScreens;
            return this;
        }
        
        public Builder actions(Map<String, NavigationAction> actions) {
            this.actions = actions;
            return this;
        }
        
        public NavigationConfig build() {
            return new NavigationConfig(nextScreen, backScreen, allowedScreens, actions);
        }
    }
    
    // Getters and Setters
    public String getNextScreen() {
        return nextScreen;
    }
    
    public void setNextScreen(String nextScreen) {
        this.nextScreen = nextScreen;
    }
    
    public String getBackScreen() {
        return backScreen;
    }
    
    public void setBackScreen(String backScreen) {
        this.backScreen = backScreen;
    }
    
    public List<String> getAllowedScreens() {
        return allowedScreens;
    }
    
    public void setAllowedScreens(List<String> allowedScreens) {
        this.allowedScreens = allowedScreens;
    }
    
    public Map<String, NavigationAction> getActions() {
        return actions;
    }
    
    public void setActions(Map<String, NavigationAction> actions) {
        this.actions = actions;
    }
}
