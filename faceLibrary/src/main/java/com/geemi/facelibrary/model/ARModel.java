package com.geemi.facelibrary.model;

import java.util.List;

public class ARModel {

    /**
     * msg : 成功
     * code : 0
     * data : {"records":[{"id":"1374761862719950850","zipUrl":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/project/bc4ea826b77748d8a991b4f4ed7f3d92GEEMI3a44cd0398774643962cf24da8ca8741.zip","thumbnail":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/project/d4ead0b6389b4268b17c7411f4918048GEEMI党内政治生活若干准则.png","size":23293703,"createTime":"2021-03-25 00:35:45","createUserName":"zhongjian","createUserId":"95df2f885e7148cda6c2a019fa9a4547","title":"4","projectId":"cbd69ee44bfc4e5892f4d6866897171c","fileName":"3a44cd0398774643962cf24da8ca8741","sizeStr":"22.2MB"}],"total":1,"size":100,"current":1,"orders":[],"searchCount":true,"pages":1}
     * success : true
     * timestamp : 1622007085236
     */

    private String msg;
    private Integer code;
    private DataDTO data;
    private Boolean success;
    private Long timestamp;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public static class DataDTO {
        /**
         * records : [{"id":"1374761862719950850","zipUrl":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/project/bc4ea826b77748d8a991b4f4ed7f3d92GEEMI3a44cd0398774643962cf24da8ca8741.zip","thumbnail":"http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/project/d4ead0b6389b4268b17c7411f4918048GEEMI党内政治生活若干准则.png","size":23293703,"createTime":"2021-03-25 00:35:45","createUserName":"zhongjian","createUserId":"95df2f885e7148cda6c2a019fa9a4547","title":"4","projectId":"cbd69ee44bfc4e5892f4d6866897171c","fileName":"3a44cd0398774643962cf24da8ca8741","sizeStr":"22.2MB"}]
         * total : 1
         * size : 100
         * current : 1
         * orders : []
         * searchCount : true
         * pages : 1
         */

        private Integer total;
        private Integer size;
        private Integer current;
        private Boolean searchCount;
        private Integer pages;
        private long progress;
        private List<RecordsDTO> records;
        private List<?> orders;

        public long getProgress() {
            return progress;
        }

        public void setProgress(long progress) {
            this.progress = progress;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "total=" + total +
                    ", size=" + size +
                    ", current=" + current +
                    ", searchCount=" + searchCount +
                    ", pages=" + pages +
                    ", progress=" + progress +
                    ", records=" + records +
                    ", orders=" + orders +
                    '}';
        }

        public Boolean getSearchCount() {
            return searchCount;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public Integer getCurrent() {
            return current;
        }

        public void setCurrent(Integer current) {
            this.current = current;
        }

        public Boolean isSearchCount() {
            return searchCount;
        }

        public void setSearchCount(Boolean searchCount) {
            this.searchCount = searchCount;
        }

        public Integer getPages() {
            return pages;
        }

        public void setPages(Integer pages) {
            this.pages = pages;
        }

        public List<RecordsDTO> getRecords() {
            return records;
        }

        public void setRecords(List<RecordsDTO> records) {
            this.records = records;
        }

        public List<?> getOrders() {
            return orders;
        }

        public void setOrders(List<?> orders) {
            this.orders = orders;
        }

        public static class RecordsDTO {
            /**
             * id : 1374761862719950850
             * zipUrl : http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/project/bc4ea826b77748d8a991b4f4ed7f3d92GEEMI3a44cd0398774643962cf24da8ca8741.zip
             * thumbnail : http://timeloit-zhgd.oss-cn-beijing.aliyuncs.com/project/d4ead0b6389b4268b17c7411f4918048GEEMI党内政治生活若干准则.png
             * size : 23293703
             * createTime : 2021-03-25 00:35:45
             * createUserName : zhongjian
             * createUserId : 95df2f885e7148cda6c2a019fa9a4547
             * title : 4
             * projectId : cbd69ee44bfc4e5892f4d6866897171c
             * fileName : 3a44cd0398774643962cf24da8ca8741
             * sizeStr : 22.2MB
             */

            private String id;
            private String zipUrl;
            private String thumbnail;
            private Integer size;
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

            public Integer getSize() {
                return size;
            }

            public void setSize(Integer size) {
                this.size = size;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getCreateUserName() {
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
