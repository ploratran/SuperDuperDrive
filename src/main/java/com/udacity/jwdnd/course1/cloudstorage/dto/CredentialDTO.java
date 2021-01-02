package com.udacity.jwdnd.course1.cloudstorage.dto;

public class CredentialDTO {

    // fields:
    private Integer credentialID;
    private String credentialUrl;
    private String credentialUsername;
    private String credentialPassword;

    // constructor:
    public CredentialDTO() {}

    // getters and setters:

    public Integer getCredentialID() {
        return credentialID;
    }

    public void setCredentialID(Integer credentialID) {
        this.credentialID = credentialID;
    }

    public String getCredentialUrl() {
        return credentialUrl;
    }

    public void setCredentialUrl(String credentialUrl) {
        this.credentialUrl = credentialUrl;
    }

    public String getCredentialUsername() {
        return credentialUsername;
    }

    public void setCredentialUsername(String credentialUsername) {
        this.credentialUsername = credentialUsername;
    }

    public String getCredentialPassword() {
        return credentialPassword;
    }

    public void setCredentialPassword(String credentialPassword) {
        this.credentialPassword = credentialPassword;
    }
}
