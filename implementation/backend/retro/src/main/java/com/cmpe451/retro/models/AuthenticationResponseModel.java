package com.cmpe451.retro.models;

public class AuthenticationResponseModel {

    private String JSessionID;

    public AuthenticationResponseModel() {
    }

    public AuthenticationResponseModel(String JSessionID) {
        this.JSessionID = JSessionID;
    }

    public String getJSessionID() {
        return JSessionID;
    }

    public void setJSessionID(String JSessionID) {
        this.JSessionID = JSessionID;
    }
}
