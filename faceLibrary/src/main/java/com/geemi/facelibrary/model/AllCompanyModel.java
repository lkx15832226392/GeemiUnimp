package com.geemi.facelibrary.model;

import com.zyyoona7.wheel.IWheelEntity;

import java.util.List;

public class AllCompanyModel {

    /**
     * success : true
     * message : 操作成功！
     * code : 200
     * result : [{"name":"安徽三建","id":"f855c92249cd4c65bf8387166239b988"},{"name":"吉米物联","id":"fe50763fe7bb431a8983daf521e70288"},{"name":"北京恒吉建筑工程有限公司","id":"fb7b32aad3ff4fd89b2eaccda2ce8188"}]
     * timestamp : 1620444922287
     */

    private Boolean success;
    private String message;
    private Integer code;
    private Long timestamp;
    private List<ResultDTO> result;

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

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<ResultDTO> getResult() {
        return result;
    }

    public void setResult(List<ResultDTO> result) {
        this.result = result;
    }

    public static class ResultDTO implements IWheelEntity {
        /**
         * name : 安徽三建
         * id : f855c92249cd4c65bf8387166239b988
         */

        private String name;
        private String id;

        @Override
        public String toString() {
            return "ResultDTO{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String getWheelText() {
            return  name == null ? "" : name;
        }
    }
}
