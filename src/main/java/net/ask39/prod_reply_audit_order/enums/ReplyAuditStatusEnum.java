package net.ask39.prod_reply_audit_order.enums;

/**
 * 回复审核状态枚举（审核业务操作专用）
 */
public enum ReplyAuditStatusEnum {
    Wait(3, "待审核"),
    Low(2,"审核不合格"),
    Pass(1, "审核合格"),
    High(4, "审核优质"),
    NullValue(0, "-"),
    Unqualified(5, "已失效");

    private int value;
    private String name;

    public static ReplyAuditStatusEnum getByValue(int value) {
        for(ReplyAuditStatusEnum replyAuditStatusEnum : ReplyAuditStatusEnum.values()) {
            if (replyAuditStatusEnum.getValue() == value) {
                return replyAuditStatusEnum;
            }
        }
        return NullValue;
    }

    public static String nameOf(int value) {
        for(ReplyAuditStatusEnum replyAuditStatusEnum : ReplyAuditStatusEnum.values()) {
            if (replyAuditStatusEnum.getValue() == value) {
                return replyAuditStatusEnum.getName();
            }
        }
        return "";
    }

    ReplyAuditStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
