package com.taorusb.consolecrundusesjdbc.controller;

public class ResponseStatus {

    private String status;

    public void setSuccessful() {
        status = "successful";
    }

    public void setElementNotFoundStatus() {
        status = "elementNotFound";
    }

    public String getStatus() {
        return status;
    }
}
