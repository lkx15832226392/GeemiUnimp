package com.geemi.facelibrary.model;

import java.io.Serializable;

public class PersonInfoByidCardModel {


    /**
     * success : true
     * message : 操作成功！
     * code : 200
     * result : {"notes":null,"education":null,"sys_org_code":null,"idCard":"130631199312180010","helmet_binding":"N","county":null,"pid":"[-999,-999]","type":null,"salary":null,"province_code":null,"cardNo":null,"personCode":"101110101010011100110101010001000111010000000000000000000001","xcxOpenid":null,"dimission_file":null,"serviceteam_name":"班组1","person_type_code":null,"province":null,"serviceteam_id":1390868820112969729,"project_id":"8896dc05c16d43aa9eb88bbcd8a0ec02","workersType":null,"person_int_id":21824,"id":"d1d67e4e06be440f824e3ec745dfc5de","licenseIssuing":null,"depart":null,"create_time":"2021-05-08 15:28:21","equipment":null,"create_how":"hand","clockType":"start","identity_org":null,"native_place":null,"phone":"15832226392","poloticalLandscape":null,"company_name":"安徽三建","name":"李克祥","personId":840611086267842560,"position":null,"status":"none","code":null,"city":null,"facePhoto":"http://oss.geemi.cn/person/78814355e78e427784678f0fca5f8b80.jpg","county_code":null,"certificate":null,"city_code":null,"faceId":"d1d67e4e06be440f824e3ec745dfc5de","jgpt_photo":null,"expired_at":null,"issued_at":null,"idcardPhoto":null,"create_by":null,"trainingSituation":null,"update_time":null,"attendanceCard":null,"national":null,"company":"f855c92249cd4c65bf8387166239b988","update_by":null,"qzy_code":null,"dimission_date":null,"createDate":null,"laborContract":null,"address":null,"company_id":"f855c92249cd4c65bf8387166239b988","jobs":"sg_mg","sex":null,"personstate":"0","isTeamLeader":"0","updateTime":null,"register_date":null,"isApp":null,"failure_reason":null,"team_code":null,"pay_wage":null,"idcardReversePhoto":null,"createUser":null,"age":25,"create_name":null}
     * timestamp : 1620527254585
     */

    private Boolean success;
    private String message;
    private Integer code;
    private ResultDTO result;
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

    public ResultDTO getResult() {
        return result;
    }

    public void setResult(ResultDTO result) {
        this.result = result;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PersonInfoByidCardModel{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", code=" + code +
                ", result=" + result +
                ", timestamp=" + timestamp +
                '}';
    }

    public static class ResultDTO implements Serializable {

        /**
         * notes : null
         * education : null
         * sys_org_code : null
         * idCard : 130631199312180010
         * helmet_binding : N
         * county : null
         * pid : [-999,-999]
         * type : null
         * salary : null
         * province_code : null
         * cardNo : null
         * personCode : 101110101010011100110101010001000111010000000000000000000001
         * xcxOpenid : null
         * dimission_file : null
         * serviceteam_name : 班组1
         * person_type_code : null
         * province : null
         * serviceteam_id : 1390868820112969729
         * project_id : 8896dc05c16d43aa9eb88bbcd8a0ec02
         * workersType : null
         * person_int_id : 21824
         * id : d1d67e4e06be440f824e3ec745dfc5de
         * licenseIssuing : null
         * depart : null
         * create_time : 2021-05-08 15:28:21
         * equipment : null
         * create_how : hand
         * clockType : start
         * identity_org : null
         * native_place : null
         * phone : 15832226392
         * poloticalLandscape : null
         * company_name : 安徽三建
         * name : 李克祥
         * personId : 840611086267842560
         * position : null
         * status : none
         * code : null
         * city : null
         * facePhoto : http://oss.geemi.cn/person/78814355e78e427784678f0fca5f8b80.jpg
         * county_code : null
         * certificate : null
         * city_code : null
         * faceId : d1d67e4e06be440f824e3ec745dfc5de
         * jgpt_photo : null
         * expired_at : null
         * issued_at : null
         * idcardPhoto : null
         * create_by : null
         * trainingSituation : null
         * update_time : null
         * attendanceCard : null
         * national : null
         * company : f855c92249cd4c65bf8387166239b988
         * update_by : null
         * qzy_code : null
         * dimission_date : null
         * createDate : null
         * laborContract : null
         * address : null
         * company_id : f855c92249cd4c65bf8387166239b988
         * jobs : sg_mg
         * sex : null
         * personstate : 0
         * isTeamLeader : 0
         * updateTime : null
         * register_date : null
         * isApp : null
         * failure_reason : null
         * team_code : null
         * pay_wage : null
         * idcardReversePhoto : null
         * createUser : null
         * age : 25
         * create_name : null
         */
        private String jobsName;
        private Object notes;
        private Object education;
        private Object sys_org_code;
        private String idCard;
        private String helmet_binding;
        private Object county;
        private String pid;
        private Object type;
        private Object salary;
        private Object province_code;
        private Object cardNo;
        private String personCode;
        private Object xcxOpenid;
        private Object dimission_file;
        private String serviceteam_name;
        private Object person_type_code;
        private Object province;
        private Long serviceteam_id;
        private String project_id;
        private Object workersType;
        private Integer person_int_id;
        private String id;
        private Object licenseIssuing;
        private Object depart;
        private String create_time;
        private Object equipment;
        private String create_how;
        private String clockType;
        private Object identity_org;
        private Object native_place;
        private String phone;
        private Object poloticalLandscape;
        private String company_name;
        private String name;
        private Long personId;
        private Object position;
        private String status;
        private Object code;
        private Object city;
        private String facePhoto;
        private Object county_code;
        private Object certificate;
        private Object city_code;
        private String faceId;
        private Object jgpt_photo;
        private Object expired_at;
        private Object issued_at;
        private Object idcardPhoto;
        private Object create_by;
        private Object trainingSituation;
        private Object update_time;
        private Object attendanceCard;
        private Object national;
        private String company;
        private Object update_by;
        private Object qzy_code;
        private Object dimission_date;
        private Object createDate;
        private Object laborContract;
        private String address;
        private String company_id;
        private String jobs;
        private Integer sex;
        private String personstate;
        private String isTeamLeader;
        private Object updateTime;
        private Object register_date;
        private Object isApp;
        private Object failure_reason;
        private Object team_code;
        private Object pay_wage;
        private Object idcardReversePhoto;
        private Object createUser;
        private Integer age;
        private Object create_name;
        @Override
        public String toString() {
            return "ResultDTO{" +
                    "JobsName='" + jobsName + '\'' +
                    ", notes=" + notes +
                    ", education=" + education +
                    ", sys_org_code=" + sys_org_code +
                    ", idCard='" + idCard + '\'' +
                    ", helmet_binding='" + helmet_binding + '\'' +
                    ", county=" + county +
                    ", pid='" + pid + '\'' +
                    ", type=" + type +
                    ", salary=" + salary +
                    ", province_code=" + province_code +
                    ", cardNo=" + cardNo +
                    ", personCode='" + personCode + '\'' +
                    ", xcxOpenid=" + xcxOpenid +
                    ", dimission_file=" + dimission_file +
                    ", serviceteam_name='" + serviceteam_name + '\'' +
                    ", person_type_code=" + person_type_code +
                    ", province=" + province +
                    ", serviceteam_id=" + serviceteam_id +
                    ", project_id='" + project_id + '\'' +
                    ", workersType=" + workersType +
                    ", person_int_id=" + person_int_id +
                    ", id='" + id + '\'' +
                    ", licenseIssuing=" + licenseIssuing +
                    ", depart=" + depart +
                    ", create_time='" + create_time + '\'' +
                    ", equipment=" + equipment +
                    ", create_how='" + create_how + '\'' +
                    ", clockType='" + clockType + '\'' +
                    ", identity_org=" + identity_org +
                    ", native_place=" + native_place +
                    ", phone='" + phone + '\'' +
                    ", poloticalLandscape=" + poloticalLandscape +
                    ", company_name='" + company_name + '\'' +
                    ", name='" + name + '\'' +
                    ", personId=" + personId +
                    ", position=" + position +
                    ", status='" + status + '\'' +
                    ", code=" + code +
                    ", city=" + city +
                    ", facePhoto='" + facePhoto + '\'' +
                    ", county_code=" + county_code +
                    ", certificate=" + certificate +
                    ", city_code=" + city_code +
                    ", faceId='" + faceId + '\'' +
                    ", jgpt_photo=" + jgpt_photo +
                    ", expired_at=" + expired_at +
                    ", issued_at=" + issued_at +
                    ", idcardPhoto=" + idcardPhoto +
                    ", create_by=" + create_by +
                    ", trainingSituation=" + trainingSituation +
                    ", update_time=" + update_time +
                    ", attendanceCard=" + attendanceCard +
                    ", national=" + national +
                    ", company='" + company + '\'' +
                    ", update_by=" + update_by +
                    ", qzy_code=" + qzy_code +
                    ", dimission_date=" + dimission_date +
                    ", createDate=" + createDate +
                    ", laborContract=" + laborContract +
                    ", address=" + address +
                    ", company_id='" + company_id + '\'' +
                    ", jobs='" + jobs + '\'' +
                    ", sex=" + sex +
                    ", personstate='" + personstate + '\'' +
                    ", isTeamLeader='" + isTeamLeader + '\'' +
                    ", updateTime=" + updateTime +
                    ", register_date=" + register_date +
                    ", isApp=" + isApp +
                    ", failure_reason=" + failure_reason +
                    ", team_code=" + team_code +
                    ", pay_wage=" + pay_wage +
                    ", idcardReversePhoto=" + idcardReversePhoto +
                    ", createUser=" + createUser +
                    ", age=" + age +
                    ", create_name=" + create_name +
                    '}';
        }

        public String getJobsName() {
            return jobsName;
        }

        public void setJobsName(String jobsName) {
            this.jobsName = jobsName;
        }

        public Object getNotes() {
            return notes;
        }

        public void setNotes(Object notes) {
            this.notes = notes;
        }

        public Object getEducation() {
            return education;
        }

        public void setEducation(Object education) {
            this.education = education;
        }

        public Object getSys_org_code() {
            return sys_org_code;
        }

        public void setSys_org_code(Object sys_org_code) {
            this.sys_org_code = sys_org_code;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getHelmet_binding() {
            return helmet_binding;
        }

        public void setHelmet_binding(String helmet_binding) {
            this.helmet_binding = helmet_binding;
        }

        public Object getCounty() {
            return county;
        }

        public void setCounty(Object county) {
            this.county = county;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public Object getType() {
            return type;
        }

        public void setType(Object type) {
            this.type = type;
        }

        public Object getSalary() {
            return salary;
        }

        public void setSalary(Object salary) {
            this.salary = salary;
        }

        public Object getProvince_code() {
            return province_code;
        }

        public void setProvince_code(Object province_code) {
            this.province_code = province_code;
        }

        public Object getCardNo() {
            return cardNo;
        }

        public void setCardNo(Object cardNo) {
            this.cardNo = cardNo;
        }

        public String getPersonCode() {
            return personCode;
        }

        public void setPersonCode(String personCode) {
            this.personCode = personCode;
        }

        public Object getXcxOpenid() {
            return xcxOpenid;
        }

        public void setXcxOpenid(Object xcxOpenid) {
            this.xcxOpenid = xcxOpenid;
        }

        public Object getDimission_file() {
            return dimission_file;
        }

        public void setDimission_file(Object dimission_file) {
            this.dimission_file = dimission_file;
        }

        public String getServiceteam_name() {
            return serviceteam_name;
        }

        public void setServiceteam_name(String serviceteam_name) {
            this.serviceteam_name = serviceteam_name;
        }

        public Object getPerson_type_code() {
            return person_type_code;
        }

        public void setPerson_type_code(Object person_type_code) {
            this.person_type_code = person_type_code;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public Long getServiceteam_id() {
            return serviceteam_id;
        }

        public void setServiceteam_id(Long serviceteam_id) {
            this.serviceteam_id = serviceteam_id;
        }

        public String getProject_id() {
            return project_id;
        }

        public void setProject_id(String project_id) {
            this.project_id = project_id;
        }

        public Object getWorkersType() {
            return workersType;
        }

        public void setWorkersType(Object workersType) {
            this.workersType = workersType;
        }

        public Integer getPerson_int_id() {
            return person_int_id;
        }

        public void setPerson_int_id(Integer person_int_id) {
            this.person_int_id = person_int_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getLicenseIssuing() {
            return licenseIssuing;
        }

        public void setLicenseIssuing(Object licenseIssuing) {
            this.licenseIssuing = licenseIssuing;
        }

        public Object getDepart() {
            return depart;
        }

        public void setDepart(Object depart) {
            this.depart = depart;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public Object getEquipment() {
            return equipment;
        }

        public void setEquipment(Object equipment) {
            this.equipment = equipment;
        }

        public String getCreate_how() {
            return create_how;
        }

        public void setCreate_how(String create_how) {
            this.create_how = create_how;
        }

        public String getClockType() {
            return clockType;
        }

        public void setClockType(String clockType) {
            this.clockType = clockType;
        }

        public Object getIdentity_org() {
            return identity_org;
        }

        public void setIdentity_org(Object identity_org) {
            this.identity_org = identity_org;
        }

        public Object getNative_place() {
            return native_place;
        }

        public void setNative_place(Object native_place) {
            this.native_place = native_place;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Object getPoloticalLandscape() {
            return poloticalLandscape;
        }

        public void setPoloticalLandscape(Object poloticalLandscape) {
            this.poloticalLandscape = poloticalLandscape;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getPersonId() {
            return personId;
        }

        public void setPersonId(Long personId) {
            this.personId = personId;
        }

        public Object getPosition() {
            return position;
        }

        public void setPosition(Object position) {
            this.position = position;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getCode() {
            return code;
        }

        public void setCode(Object code) {
            this.code = code;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public String getFacePhoto() {
            return facePhoto;
        }

        public void setFacePhoto(String facePhoto) {
            this.facePhoto = facePhoto;
        }

        public Object getCounty_code() {
            return county_code;
        }

        public void setCounty_code(Object county_code) {
            this.county_code = county_code;
        }

        public Object getCertificate() {
            return certificate;
        }

        public void setCertificate(Object certificate) {
            this.certificate = certificate;
        }

        public Object getCity_code() {
            return city_code;
        }

        public void setCity_code(Object city_code) {
            this.city_code = city_code;
        }

        public String getFaceId() {
            return faceId;
        }

        public void setFaceId(String faceId) {
            this.faceId = faceId;
        }

        public Object getJgpt_photo() {
            return jgpt_photo;
        }

        public void setJgpt_photo(Object jgpt_photo) {
            this.jgpt_photo = jgpt_photo;
        }

        public Object getExpired_at() {
            return expired_at;
        }

        public void setExpired_at(Object expired_at) {
            this.expired_at = expired_at;
        }

        public Object getIssued_at() {
            return issued_at;
        }

        public void setIssued_at(Object issued_at) {
            this.issued_at = issued_at;
        }

        public Object getIdcardPhoto() {
            return idcardPhoto;
        }

        public void setIdcardPhoto(Object idcardPhoto) {
            this.idcardPhoto = idcardPhoto;
        }

        public Object getCreate_by() {
            return create_by;
        }

        public void setCreate_by(Object create_by) {
            this.create_by = create_by;
        }

        public Object getTrainingSituation() {
            return trainingSituation;
        }

        public void setTrainingSituation(Object trainingSituation) {
            this.trainingSituation = trainingSituation;
        }

        public Object getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(Object update_time) {
            this.update_time = update_time;
        }

        public Object getAttendanceCard() {
            return attendanceCard;
        }

        public void setAttendanceCard(Object attendanceCard) {
            this.attendanceCard = attendanceCard;
        }

        public Object getNational() {
            return national;
        }

        public void setNational(Object national) {
            this.national = national;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public Object getUpdate_by() {
            return update_by;
        }

        public void setUpdate_by(Object update_by) {
            this.update_by = update_by;
        }

        public Object getQzy_code() {
            return qzy_code;
        }

        public void setQzy_code(Object qzy_code) {
            this.qzy_code = qzy_code;
        }

        public Object getDimission_date() {
            return dimission_date;
        }

        public void setDimission_date(Object dimission_date) {
            this.dimission_date = dimission_date;
        }

        public Object getCreateDate() {
            return createDate;
        }

        public void setCreateDate(Object createDate) {
            this.createDate = createDate;
        }

        public Object getLaborContract() {
            return laborContract;
        }

        public void setLaborContract(Object laborContract) {
            this.laborContract = laborContract;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getJobs() {
            return jobs;
        }

        public void setJobs(String jobs) {
            this.jobs = jobs;
        }

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public String getPersonstate() {
            return personstate;
        }

        public void setPersonstate(String personstate) {
            this.personstate = personstate;
        }

        public String getIsTeamLeader() {
            return isTeamLeader;
        }

        public void setIsTeamLeader(String isTeamLeader) {
            this.isTeamLeader = isTeamLeader;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRegister_date() {
            return register_date;
        }

        public void setRegister_date(Object register_date) {
            this.register_date = register_date;
        }

        public Object getIsApp() {
            return isApp;
        }

        public void setIsApp(Object isApp) {
            this.isApp = isApp;
        }

        public Object getFailure_reason() {
            return failure_reason;
        }

        public void setFailure_reason(Object failure_reason) {
            this.failure_reason = failure_reason;
        }

        public Object getTeam_code() {
            return team_code;
        }

        public void setTeam_code(Object team_code) {
            this.team_code = team_code;
        }

        public Object getPay_wage() {
            return pay_wage;
        }

        public void setPay_wage(Object pay_wage) {
            this.pay_wage = pay_wage;
        }

        public Object getIdcardReversePhoto() {
            return idcardReversePhoto;
        }

        public void setIdcardReversePhoto(Object idcardReversePhoto) {
            this.idcardReversePhoto = idcardReversePhoto;
        }

        public Object getCreateUser() {
            return createUser;
        }

        public void setCreateUser(Object createUser) {
            this.createUser = createUser;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Object getCreate_name() {
            return create_name;
        }

        public void setCreate_name(Object create_name) {
            this.create_name = create_name;
        }
    }
}
