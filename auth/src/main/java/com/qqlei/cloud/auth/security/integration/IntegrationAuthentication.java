package com.qqlei.cloud.auth.security.integration;


import java.util.Map;


public class IntegrationAuthentication {

    private String authType;
    private String username;
    private String findUserClassName;
    private Map<String,String[]> authParameters;

    public String getAuthParameter(String paramter){
        String[] values = this.authParameters.get(paramter);
        if(values != null && values.length > 0){
            return values[0];
        }
        return null;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthParameters(Map<String, String[]> authParameters) {
        this.authParameters = authParameters;
    }

    public String getFindUserClassName() {
        return findUserClassName;
    }

    public void setFindUserClassName(String findUserClassName) {
        this.findUserClassName = findUserClassName;
    }
}
