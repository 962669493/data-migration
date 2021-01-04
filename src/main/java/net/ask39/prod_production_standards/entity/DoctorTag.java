package net.ask39.prod_production_standards.entity;

import java.io.Serializable;

/**
 * class
 *
 * @author jianbin
 * @date 2020/3/4
 */
public class DoctorTag implements Serializable {
    private Integer key;

    private String label;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
