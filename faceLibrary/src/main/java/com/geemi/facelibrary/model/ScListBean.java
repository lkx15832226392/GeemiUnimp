package com.geemi.facelibrary.model;

import java.io.Serializable;
import java.util.List;

public class ScListBean implements Serializable {


    /**
     * success : true
     * message : 操作成功！
     * code : 200
     * result : [{"id":"1289546577340026882","name":"厂区东北角、工人生活区","url":"rtmp://60.205.95.50:9785/live/deb6745ea99a4c73a9c4389cba591dbe","projectId":"cbbc2a5406134a4983d588c055b7c3ad","projectName":null,"createTime":"2020-08-01 21:00:37","sysUserCode":null,"stn":"d2662c30233112326933161811180919","iccid":"0000","installUser":null,"installTeam":null,"devlng":null,"devlat":null,"onLineState":"0"},{"id":"1288766522884132865","name":"项目部办公区、大门口","url":"rtmp://60.205.95.50:9785/live/0ae8ab03324f4753a71fa095332d9f3d","projectId":"cbbc2a5406134a4983d588c055b7c3ad","projectName":null,"createTime":"2020-07-30 17:20:58","sysUserCode":null,"stn":"d2662c30232b2d35693316181115161a","iccid":"8986061900001821764","installUser":null,"installTeam":null,"devlng":null,"devlat":null,"onLineState":"0"},{"id":"1288766411508584450","name":"厂区西南角、西北角钢筋棚","url":"rtmp://60.205.95.50:9785/live/a638bf471f0f404ca23aa5b939130332","projectId":"cbbc2a5406134a4983d588c055b7c3ad","projectName":null,"createTime":"2020-07-30 17:20:31","sysUserCode":null,"stn":"d2662c30235c1d3669331618112e0e1b","iccid":"8986061900001821782","installUser":null,"installTeam":null,"devlng":null,"devlat":null,"onLineState":"0"}]
     * timestamp : 1623224554192
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

    public static class ResultDTO {
        /**
         * id : 1289546577340026882
         * name : 厂区东北角、工人生活区
         * url : rtmp://60.205.95.50:9785/live/deb6745ea99a4c73a9c4389cba591dbe
         * projectId : cbbc2a5406134a4983d588c055b7c3ad
         * projectName : null
         * createTime : 2020-08-01 21:00:37
         * sysUserCode : null
         * stn : d2662c30233112326933161811180919
         * iccid : 0000
         * installUser : null
         * installTeam : null
         * devlng : null
         * devlat : null
         * onLineState : 0
         */

        private String id;
        private String name;
        private String url;
        private String projectId;
        private Object projectName;
        private String createTime;
        private Object sysUserCode;
        private String stn;
        private String iccid;
        private Object installUser;
        private Object installTeam;
        private Object devlng;
        private Object devlat;
        private String onLineState;
        private String volume;

        public String getVolume() {
            return volume;
        }

        public void setVolume(String volume) {
            this.volume = volume;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public Object getProjectName() {
            return projectName;
        }

        public void setProjectName(Object projectName) {
            this.projectName = projectName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public Object getSysUserCode() {
            return sysUserCode;
        }

        public void setSysUserCode(Object sysUserCode) {
            this.sysUserCode = sysUserCode;
        }

        public String getStn() {
            return stn;
        }

        public void setStn(String stn) {
            this.stn = stn;
        }

        public String getIccid() {
            return iccid;
        }

        public void setIccid(String iccid) {
            this.iccid = iccid;
        }

        public Object getInstallUser() {
            return installUser;
        }

        public void setInstallUser(Object installUser) {
            this.installUser = installUser;
        }

        public Object getInstallTeam() {
            return installTeam;
        }

        public void setInstallTeam(Object installTeam) {
            this.installTeam = installTeam;
        }

        public Object getDevlng() {
            return devlng;
        }

        public void setDevlng(Object devlng) {
            this.devlng = devlng;
        }

        public Object getDevlat() {
            return devlat;
        }

        public void setDevlat(Object devlat) {
            this.devlat = devlat;
        }

        public String getOnLineState() {
            return onLineState;
        }

        public void setOnLineState(String onLineState) {
            this.onLineState = onLineState;
        }
    }
}
