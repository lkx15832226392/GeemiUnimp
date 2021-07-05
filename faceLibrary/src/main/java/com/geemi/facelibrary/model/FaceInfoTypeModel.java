package com.geemi.facelibrary.model;

import java.util.List;

public class FaceInfoTypeModel {

    /**
     * returnCode : 0
     * version : 0
     * faceInfoTypeData : [{"typeId":1,"typeTextString":"工种2131"},{"typeId":2,"faceTextString":"工种435345"},{"typeId":31,"faceTextString":"工种56756"},{"typeId":4,"faceTextString":"工种65765"},{"typeId":5,"faceTextString":"工种435345767"},{"typeId":6,"faceTextString":"工种435345-90-90"},{"typeId":7,"faceTextString":"工种435345987897"}]
     */

    private Integer returnCode;
    private Integer version;
    private List<FaceInfoTypeDataDTO> faceInfoTypeData;

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<FaceInfoTypeDataDTO> getFaceInfoTypeData() {
        return faceInfoTypeData;
    }

    public void setFaceInfoTypeData(List<FaceInfoTypeDataDTO> faceInfoTypeData) {
        this.faceInfoTypeData = faceInfoTypeData;
    }

    public static class FaceInfoTypeDataDTO {
        /**
         * typeId : 1
         * typeTextString : 工种2131
         * faceTextString : 工种435345
         */

        private Integer typeId;
        private String typeTextString;
        private String faceTextString;

        public Integer getTypeId() {
            return typeId;
        }

        public void setTypeId(Integer typeId) {
            this.typeId = typeId;
        }

        public String getTypeTextString() {
            return typeTextString;
        }

        public void setTypeTextString(String typeTextString) {
            this.typeTextString = typeTextString;
        }

        public String getFaceTextString() {
            return faceTextString;
        }

        public void setFaceTextString(String faceTextString) {
            this.faceTextString = faceTextString;
        }
    }
}
