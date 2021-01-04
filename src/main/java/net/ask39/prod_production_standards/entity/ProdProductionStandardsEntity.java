package net.ask39.prod_production_standards.entity;

import java.time.LocalDateTime;

/**
 * <p>
 * 生产标准表
 * </p>
 *
 * @author zhangzheng
 * @since 2020-11-22
 */
public class ProdProductionStandardsEntity {

    private Long id;

    /**
     * 生产标准名称
     */
    private String standardsName;

    /**
     * 需求方
     */
    private Integer demandPosition;

    /**
     * 是否需要主述，0：不需要，1需要
     */
    private Integer needDetail;

    /**
     * 创建人
     */
    private String founderName;

    /**
     * 创建人ID
     */
    private Long founderId;

    /**
     * 创建时间
     */
    private LocalDateTime createOn;

    /**
     * 回复数量
     */
    private Integer replyCount;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人ID
     */
    private Long updateUser;

    /**
     * 是否被删除，1：是，0：否
     */
    private Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getNeedDetail() {
        return needDetail;
    }

    public void setNeedDetail(Integer needDetail) {
        this.needDetail = needDetail;
    }

    public String getFounderName() {
        return founderName;
    }

    public void setFounderName(String founderName) {
        this.founderName = founderName;
    }

    public Long getFounderId() {
        return founderId;
    }

    public void setFounderId(Long founderId) {
        this.founderId = founderId;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
