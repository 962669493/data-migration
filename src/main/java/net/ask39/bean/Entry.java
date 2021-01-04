package net.ask39.bean;

/**
 * @author zhangzheng
 * @date 2021-01-04
 **/
public class Entry {
    private String columnName;
    private Object columnValue;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(Object columnValue) {
        this.columnValue = columnValue;
    }

    public Entry() {
    }

    public Entry(String columnName, Object columnValue) {
        this.columnName = columnName;
        this.columnValue = columnValue;
    }
}
