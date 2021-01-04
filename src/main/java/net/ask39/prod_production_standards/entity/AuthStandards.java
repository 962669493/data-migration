package net.ask39.prod_production_standards.entity;

import java.io.Serializable;

/**
 * @author jianbin
 * @date 2020/3/3
 */
public class AuthStandards implements Serializable {

    private Boolean needAuth;

    private Integer doctorId;

    private DoctorTag doctorTag;

    private Integer score;

    public DoctorTag getDoctorTag() {
        return doctorTag;
    }

    public void setDoctorTag(DoctorTag doctorTag) {
        this.doctorTag = doctorTag;
    }

    public Boolean getNeedAuth() {
        return needAuth;
    }

    public void setNeedAuth(Boolean needAuth) {
        this.needAuth = needAuth;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
