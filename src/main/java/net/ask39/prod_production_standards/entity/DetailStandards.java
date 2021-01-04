package net.ask39.prod_production_standards.entity;

import java.io.Serializable;

/**
 * @author jianbin
 * @date 2020/3/3
 */
public class DetailStandards extends WordCount implements Serializable {

    private Boolean needDetail;

    public Boolean getNeedDetail() {
        return needDetail;
    }

    public void setNeedDetail(Boolean needDetail) {
        this.needDetail = needDetail;
    }
}
