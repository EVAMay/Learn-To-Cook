package com.feicui.learntocook.entity;

/**
 * Created by lenovo on 2016/9/15.
 */
public class JsonInfo {
    public JsonInfo() {
    }

    String jsonData;

    public JsonInfo(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }
}
