package net.ask39.prod_topics.enums;

import java.util.Objects;

/**
 * 内容生产任务类型枚举
 *
 * @author zhangzheng
 * @date 2020-11-04
 **/
public enum TopicContentTaskTypeEnum {
    /** 造贴 */
    CREATE_TOPICS(1, "造贴"),
    /** 回复 */
    REPLY(2, "回复"),
    /** 审核 */
    AUDIT(3, "审核"),
    /** 授权 */
    AUTH(4, "授权");
    private final int value;
    private final String name;

    TopicContentTaskTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getNameByValue(int value){
        for (TopicContentTaskTypeEnum topicContentTaskTypeEnum : values()){
            if (Objects.equals(value, topicContentTaskTypeEnum.getValue())){
                return topicContentTaskTypeEnum.getName();
            }
        }
        return null;
    }

    public static TopicContentTaskTypeEnum getByValue(int value){
        for (TopicContentTaskTypeEnum topicContentTaskTypeEnum : values()){
            if (Objects.equals(value, topicContentTaskTypeEnum.getValue())){
                return topicContentTaskTypeEnum;
            }
        }
        return null;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
