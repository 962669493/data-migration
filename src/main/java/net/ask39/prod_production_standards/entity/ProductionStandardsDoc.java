package net.ask39.prod_production_standards.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(collection="ProductionStandards")
public class ProductionStandardsDoc implements Serializable {

    @Id
    private String id;

    private String standardsName;

    private Integer demandPosition;

    private DetailStandards detailStandards;

    private List<ReplyStandards> replyStandardsList;

    private String founderName;

    private Integer founderId;

    private Date createOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFounderName() {
        return founderName;
    }

    public void setFounderName(String founderName) {
        this.founderName = founderName;
    }

    public Integer getFounderId() {
        return founderId;
    }

    public void setFounderId(Integer founderId) {
        this.founderId = founderId;
    }

    public Date getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Date createOn) {
        this.createOn = createOn;
    }

    public String getStandardsName() {
        return standardsName;
    }

    public void setStandardsName(String standardsName) {
        this.standardsName = standardsName;
    }

    public Integer getDemandPosition() {
        return demandPosition;
    }

    public void setDemandPosition(Integer demandPosition) {
        this.demandPosition = demandPosition;
    }

    public DetailStandards getDetailStandards() {
        return detailStandards;
    }

    public void setDetailStandards(DetailStandards detailStandards) {
        this.detailStandards = detailStandards;
    }

    public List<ReplyStandards> getReplyStandardsList() {
        return replyStandardsList;
    }

    public void setReplyStandardsList(List<ReplyStandards> replyStandardsList) {
        this.replyStandardsList = replyStandardsList;
    }
}
