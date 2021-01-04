package net.ask39.prod_production_standards.entity;

import java.io.Serializable;

/**
 * @author jianbin
 * @date 2020/3/3
 */
public class AnswerStandards extends WordCount implements Serializable {
    private Integer replyPersonType;

    private DoctorTag doctorTag;

    private Integer score;

    private String replyStandardsDetail;

    private String titleDemo;

    private String contentDemo;

    public DoctorTag getDoctorTag() {
        return doctorTag;
    }

    public void setDoctorTag(DoctorTag doctorTag) {
        this.doctorTag = doctorTag;
    }

    public Integer getReplyPersonType() {
        return replyPersonType;
    }

    public void setReplyPersonType(Integer replyPersonType) {
        this.replyPersonType = replyPersonType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getReplyStandardsDetail() {
        return replyStandardsDetail;
    }

    public void setReplyStandardsDetail(String replyStandardsDetail) {
        this.replyStandardsDetail = replyStandardsDetail;
    }

    public String getTitleDemo() {
        return titleDemo;
    }

    public void setTitleDemo(String titleDemo) {
        this.titleDemo = titleDemo;
    }

    public String getContentDemo() {
        return contentDemo;
    }

    public void setContentDemo(String contentDemo) {
        this.contentDemo = contentDemo;
    }
}
