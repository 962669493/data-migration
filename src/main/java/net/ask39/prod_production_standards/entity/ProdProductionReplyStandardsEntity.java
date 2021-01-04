package net.ask39.prod_production_standards.entity;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 生产回复标准表
 * </p>
 *
 * @author zhangzheng
 * @since 2020-11-22
 */
public class ProdProductionReplyStandardsEntity {

    private Long id;

    /**
     * 生产标准id
     */
    private Long productionStandardsId;

    /**
     * 最小字数
     */
    private Integer lowWordCount;

    /**
     * 最大字数
     */
    private Integer highWordCount;

    /**
     * 回复标准
     */
    private String replyStandardsDetail;

    /**
     * 标题demo
     */
    private String titleDemo;

    /**
     * 内容demo
     */
    private String contentDemo;

    /**
     * 段落
     */
    private String section;

    /**
     * 段落提示内容
     */
    private String schemeContent;

    /**
     * 是否需要授权
     */
    private Integer needAuth;

    /**
     * 创建时间
     */
    private LocalDateTime createOn;

    /**
     * 创建人
     */
    private String createName;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateName;

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

    public Long getProductionStandardsId() {
        return productionStandardsId;
    }

    public void setProductionStandardsId(Long productionStandardsId) {
        this.productionStandardsId = productionStandardsId;
    }

    public Integer getLowWordCount() {
        return lowWordCount;
    }

    public void setLowWordCount(Integer lowWordCount) {
        this.lowWordCount = lowWordCount;
    }

    public Integer getHighWordCount() {
        return highWordCount;
    }

    public void setHighWordCount(Integer highWordCount) {
        this.highWordCount = highWordCount;
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

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSchemeContent() {
        return schemeContent;
    }

    public void setSchemeContent(String schemeContent) {
        this.schemeContent = schemeContent;
    }

    public Integer getNeedAuth() {
        return needAuth;
    }

    public void setNeedAuth(Integer needAuth) {
        this.needAuth = needAuth;
    }

    public LocalDateTime getCreateOn() {
        return createOn;
    }

    public void setCreateOn(LocalDateTime createOn) {
        this.createOn = createOn;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
