package net.ask39.prod_production_standards.entity;

/**
 * <p>
 * 生产标准及回复的映射表
 * </p>
 *
 * @author zhangzheng
 * @since 2020-10-29
 */
public class ProdProductionReplyStandardMapEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 生产标准ID
     */
    private Long prodStandardId;

    /**
     * 回复标准ID
     */
    private Long replyStandardId;

    /**
     * 回复序号,即贴子的第几个回复，从1开始
     */
    private Integer replyNo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getProdStandardId() {
        return prodStandardId;
    }

    public void setProdStandardId(Long prodStandardId) {
        this.prodStandardId = prodStandardId;
    }

    public Long getReplyStandardId() {
        return replyStandardId;
    }

    public void setReplyStandardId(Long replyStandardId) {
        this.replyStandardId = replyStandardId;
    }

    public Integer getReplyNo() {
        return replyNo;
    }

    public void setReplyNo(Integer replyNo) {
        this.replyNo = replyNo;
    }
}
