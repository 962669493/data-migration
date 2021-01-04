package net.ask39.prod_production_standards.entity;

import java.io.Serializable;
import java.util.List;

/**
 * class
 *
 * @author jianbin
 * @date 2020/6/9
 */
public class AnswerScheme implements Serializable {

    private List<Integer> keys;

    private List<String> schemeContent;

    public List<Integer> getKeys() {
        return keys;
    }

    public void setKeys(List<Integer> keys) {
        this.keys = keys;
    }

    public List<String> getSchemeContent() {
        return schemeContent;
    }

    public void setSchemeContent(List<String> schemeContent) {
        this.schemeContent = schemeContent;
    }
}
