package com.geemi.facelibrary.model;

import java.util.List;

public class DownListBean {


    /**
     * msg : 成功
     * code : 200
     * data : {"records":[{"id":"1293374397241032706","zipUrl":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/partyBuilding/2c501fc4d38a49e8bd98384b466f6ba6.zip","thumbnail":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/partyBuilding/c1413ec136964f168134cf87c6539c75.jpg","size":23330330,"createTime":"2020-08-12 10:31:01","createUserName":null,"createUserId":null,"title":"党内政治生活若干准则","projectId":"","fileName":"党内政治生活若干准则","sizeStr":"22.2MB"},{"id":"1293377043494498306","zipUrl":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/partyBuilding/81da0e437b56402484d5a1b0e76deb00.zip","thumbnail":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/partyBuilding/c5a82f494b2f49cfba5c91f9ca4221d2.jpg","size":44673421,"createTime":"2020-08-12 10:41:32","createUserName":null,"createUserId":null,"title":"党章的地位和作用","projectId":"","fileName":"党章的地位和作用","sizeStr":"42.6MB"}],"total":2,"size":100,"current":1,"orders":[],"searchCount":true,"pages":1}
     * success : true
     * timestamp : 1597204265470
     */

    private String msg;
    private int code;
    private DataBean data;
    private boolean success;
    private long timestamp;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DownListBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                ", success=" + success +
                ", timestamp=" + timestamp +
                '}';
    }

    public static class DataBean {
        /**
         * records : [{"id":"1293374397241032706","zipUrl":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/partyBuilding/2c501fc4d38a49e8bd98384b466f6ba6.zip","thumbnail":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/partyBuilding/c1413ec136964f168134cf87c6539c75.jpg","size":23330330,"createTime":"2020-08-12 10:31:01","createUserName":null,"createUserId":null,"title":"党内政治生活若干准则","projectId":"","fileName":"党内政治生活若干准则","sizeStr":"22.2MB"},{"id":"1293377043494498306","zipUrl":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/partyBuilding/81da0e437b56402484d5a1b0e76deb00.zip","thumbnail":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/partyBuilding/c5a82f494b2f49cfba5c91f9ca4221d2.jpg","size":44673421,"createTime":"2020-08-12 10:41:32","createUserName":null,"createUserId":null,"title":"党章的地位和作用","projectId":"","fileName":"党章的地位和作用","sizeStr":"42.6MB"}]
         * total : 2
         * size : 100
         * current : 1
         * orders : []
         * searchCount : true
         * pages : 1
         */

        private String total;
        private String size;
        private String current;
        private boolean searchCount;
        private int pages;
        private List<RecordsBean> records;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(boolean searchCount) {
            this.searchCount = searchCount;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<RecordsBean> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsBean> records) {
            this.records = records;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "total='" + total + '\'' +
                    ", size='" + size + '\'' +
                    ", current='" + current + '\'' +
                    ", searchCount=" + searchCount +
                    ", pages=" + pages +
                    ", records=" + records +
                    '}';
        }


        public static class RecordsBean {
            /**
             * id : 1293374397241032706
             * zipUrl : http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/partyBuilding/2c501fc4d38a49e8bd98384b466f6ba6.zip
             * thumbnail : http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/partyBuilding/c1413ec136964f168134cf87c6539c75.jpg
             * size : 23330330
             * createTime : 2020-08-12 10:31:01
             * createUserName : null
             * createUserId : null
             * title : 党内政治生活若干准则
             * projectId :
             * fileName : 党内政治生活若干准则
             * sizeStr : 22.2MB
             */

            private String id;
            private String zipUrl;
            private String thumbnail;
            private String size;
            private String createTime;
            private String createUserName;
            private String createUserId;
            private String title;
            private String projectId;
            private String fileName;
            private String sizeStr;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getZipUrl() {
                return zipUrl;
            }

            public void setZipUrl(String zipUrl) {
                this.zipUrl = zipUrl;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getCreateUserName() {
                return createUserName;
            }

            public void setCreateUserName(String createUserName) {
                this.createUserName = createUserName;
            }

            public String getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(String createUserId) {
                this.createUserId = createUserId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getProjectId() {
                return projectId;
            }

            public void setProjectId(String projectId) {
                this.projectId = projectId;
            }

            public String getFileName() {
                return fileName;
            }

            public void setFileName(String fileName) {
                this.fileName = fileName;
            }

            public String getSizeStr() {
                return sizeStr;
            }

            public void setSizeStr(String sizeStr) {
                this.sizeStr = sizeStr;
            }
        }
    }
}
