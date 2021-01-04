package net.ask39.prod_topics.entity;

import java.util.Date;

public class TopicExt {
    private Integer tid;

    private Integer sex;

    private String age;

    private Integer height;

    private Double weight;

    private Date readanswertime;

    private Boolean isanonymous;

    private Boolean isyunfu;

    private String newkeyname;

    private String imgkeyid;

    private Integer mhid;

    private String pregnancy;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Date getReadanswertime() {
        return readanswertime;
    }

    public void setReadanswertime(Date readanswertime) {
        this.readanswertime = readanswertime;
    }

    public Boolean getIsanonymous() {
        return isanonymous;
    }

    public void setIsanonymous(Boolean isanonymous) {
        this.isanonymous = isanonymous;
    }

    public Boolean getIsyunfu() {
        return isyunfu;
    }

    public void setIsyunfu(Boolean isyunfu) {
        this.isyunfu = isyunfu;
    }

    public String getNewkeyname() {
        return newkeyname;
    }

    public void setNewkeyname(String newkeyname) {
        this.newkeyname = newkeyname == null ? null : newkeyname.trim();
    }

    public String getImgkeyid() {
        return imgkeyid;
    }

    public void setImgkeyid(String imgkeyid) {
        this.imgkeyid = imgkeyid == null ? null : imgkeyid.trim();
    }

    public Integer getMhid() {
        return mhid;
    }

    public void setMhid(Integer mhid) {
        this.mhid = mhid;
    }

    public String getPregnancy() {
        return pregnancy;
    }

    public void setPregnancy(String pregnancy) {
        this.pregnancy = pregnancy == null ? null : pregnancy.trim();
    }
}