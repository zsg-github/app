package com.paas.app.dto;

import java.util.HashMap;
import java.util.Map;

public class CommonResult {

    private Integer state;
    private boolean success;
    private String message;
    private final Map<String, Object> data = new HashMap<>();

    public CommonResult() {
    }

    public CommonResult(Integer state, boolean success, String message) {
        this.state = state;
        this.success = success;
        this.message = message;
    }

    public CommonResult(Integer state, boolean success) {
        this.state = state;
        this.success = success;
    }

    public CommonResult(Integer state, String message) {
        this.state = state;
        this.message = message;
    }

    public CommonResult(boolean success) {
        this.success = success;
    }

    /**
     * 增加属性
     *
     * @param key
     * @param obj
     */
    public void addAttribute(String key, Object obj) {
        data.put(key, obj);
    }

    /**
     * 获得属性
     * ArrayList<Map<String, Object>> totalList = (ArrayList<Map<String, Object>>) resultSoaRest.getAttribute("totalList");
     *
     */
    public Object getAttribute(String key) {
        return data.get(key);
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
