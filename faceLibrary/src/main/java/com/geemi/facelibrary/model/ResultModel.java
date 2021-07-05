package com.geemi.facelibrary.model;

public class ResultModel {


    /**
     * success : true
     * message : 录入成功
     * code : 200
     * result : null
     * timestamp : 1620458900790
     */

    private Boolean success;
    private String message;
    private Integer code;
    private Object result;
    private Long timestamp;

    public Boolean isSuccess() {
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
