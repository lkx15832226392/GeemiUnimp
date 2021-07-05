package com.geemi.facelibrary.model;

import java.util.List;

public class TempFaceInfoModel {

    /**
     * returnCode : 0
     * version : 0
     * faceThumbnail : https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi1.sinaimg.cn%2FIT%2F2010%2F0419%2F201041993511.jpg&refer=http%3A%2F%2Fi1.sinaimg.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1622169957&t=f8c22be7edccde134fc87c6a8d7aa4d1
     * faceInfoData : [{"faceLeftTextString":"姓名","faceCenterTextString":"张三","faceRightTextString":"在线","faceLeftTextSize":12,"faceRightTextSize":12,"faceCenterTextSize":12},{"faceLeftTextString":"年龄","faceCenterTextString":"35岁","faceRightTextString":"","faceLeftTextSize":12,"faceRightTextSize":12,"faceCenterTextSize":12},{"faceLeftTextString":"所属单位","faceCenterTextString":"吉米办公室","faceRightTextString":"","faceLeftTextSize":12,"faceRightTextSize":12,"faceCenterTextSize":12},{"faceLeftTextString":"工种","faceCenterTextString":"钢筋工","faceRightTextString":"","faceLeftTextSize":12,"faceRightTextSize":12,"faceCenterTextSize":12},{"faceLeftTextString":"手机号","faceCenterTextString":"15832226392","faceRightTextString":"","faceLeftTextSize":12,"faceRightTextSize":12,"faceCenterTextSize":12},{"faceLeftTextString":"身份证号","faceCenterTextString":"123456*************4475111","faceRightTextString":"","faceLeftTextSize":12,"faceRightTextSize":12,"faceCenterTextSize":12},{"faceLeftTextString":"打卡日期","faceCenterTextString":"2021年4月28日","faceRightTextString":"","faceLeftTextSize":12,"faceRightTextSize":12,"faceCenterTextSize":12}]
     */

    private Integer returnCode;
    private Integer version;
    private String faceThumbnail;
    private List<FaceInfoDataDTO> faceInfoData;

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

    public String getFaceThumbnail() {
        return faceThumbnail;
    }

    public void setFaceThumbnail(String faceThumbnail) {
        this.faceThumbnail = faceThumbnail;
    }

    public List<FaceInfoDataDTO> getFaceInfoData() {
        return faceInfoData;
    }

    @Override
    public String toString() {
        return "TempFaceInfoModel{" +
                "returnCode=" + returnCode +
                ", version=" + version +
                ", faceThumbnail='" + faceThumbnail + '\'' +
                ", faceInfoData=" + faceInfoData +
                '}';
    }

    public void setFaceInfoData(List<FaceInfoDataDTO> faceInfoData) {
        this.faceInfoData = faceInfoData;
    }

    public static class FaceInfoDataDTO {
        /**
         * faceLeftTextString : 姓名
         * faceCenterTextString : 张三
         * faceRightTextString : 在线
         * faceLeftTextSize : 12
         * faceRightTextSize : 12
         * faceCenterTextSize : 12
         */
        private String companyID;//单位ID
        private String teamId;//班组ID
        private String faceLeftTextString;
        private String faceCenterTextString;
        private String faceRightTextString;
        private Integer faceLeftTextSize;
        private Integer faceRightTextSize;
        private Integer faceCenterTextSize;
        private String faceIsEnabled;
        private Integer faceInfoId;
        private String jobs;

        @Override
        public String toString() {
            return "FaceInfoDataDTO{" +
                    "companyID='" + companyID + '\'' +
                    ", teamId='" + teamId + '\'' +
                    ", faceLeftTextString='" + faceLeftTextString + '\'' +
                    ", faceCenterTextString='" + faceCenterTextString + '\'' +
                    ", faceRightTextString='" + faceRightTextString + '\'' +
                    ", faceLeftTextSize=" + faceLeftTextSize +
                    ", faceRightTextSize=" + faceRightTextSize +
                    ", faceCenterTextSize=" + faceCenterTextSize +
                    ", faceIsEnabled='" + faceIsEnabled + '\'' +
                    ", faceInfoId=" + faceInfoId +
                    ", jobs='" + jobs + '\'' +
                    '}';
        }

        public String getJobs() {
            return jobs;
        }

        public void setJobs(String jobs) {
            this.jobs = jobs;
        }

        public String getTeamId() {
            return teamId;
        }

        public void setTeamId(String teamId) {
            this.teamId = teamId;
        }

        public String getCompanyID() {
            return companyID;
        }

        public void setCompanyID(String companyID) {
            this.companyID = companyID;
        }

        public Integer getFaceInfoId() {
            return faceInfoId;
        }

        public void setFaceInfoId(Integer faceInfoId) {
            this.faceInfoId = faceInfoId;
        }

        public String getFaceIsEnabled() {
            return faceIsEnabled;
        }

        public void setFaceIsEnabled(String faceIsEnabled) {
            this.faceIsEnabled = faceIsEnabled;
        }

        public String getFaceLeftTextString() {
            return faceLeftTextString;
        }

        public void setFaceLeftTextString(String faceLeftTextString) {
            this.faceLeftTextString = faceLeftTextString;
        }

        public String getFaceCenterTextString() {
            return faceCenterTextString;
        }

        public void setFaceCenterTextString(String faceCenterTextString) {
            this.faceCenterTextString = faceCenterTextString;
        }

        public String getFaceRightTextString() {
            return faceRightTextString;
        }

        public void setFaceRightTextString(String faceRightTextString) {
            this.faceRightTextString = faceRightTextString;
        }

        public Integer getFaceLeftTextSize() {
            return faceLeftTextSize;
        }

        public void setFaceLeftTextSize(Integer faceLeftTextSize) {
            this.faceLeftTextSize = faceLeftTextSize;
        }

        public Integer getFaceRightTextSize() {
            return faceRightTextSize;
        }

        public void setFaceRightTextSize(Integer faceRightTextSize) {
            this.faceRightTextSize = faceRightTextSize;
        }

        public Integer getFaceCenterTextSize() {
            return faceCenterTextSize;
        }

        public void setFaceCenterTextSize(Integer faceCenterTextSize) {
            this.faceCenterTextSize = faceCenterTextSize;
        }
    }
}
