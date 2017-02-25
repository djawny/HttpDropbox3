package com.sdaacademy.jawny.daniel.httpdropbox3;

import org.json.JSONObject;

public class GetFilesListResult {
    private JSONObject jsonObject;
    private boolean isSuccess;
    private String errorMessage;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}