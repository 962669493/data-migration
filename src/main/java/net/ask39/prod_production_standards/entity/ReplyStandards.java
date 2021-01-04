package net.ask39.prod_production_standards.entity;

import java.io.Serializable;

/**
 * @author jianbin
 * @date 2020/3/3
 */
public class ReplyStandards implements Serializable {

    private AnswerStandards answerStandards;

    private AuthStandards authStandards;

    private AnswerScheme answerScheme;

    public AnswerScheme getAnswerScheme() {
        return answerScheme;
    }

    public void setAnswerScheme(AnswerScheme answerScheme) {
        this.answerScheme = answerScheme;
    }

    public AnswerStandards getAnswerStandards() {
        return answerStandards;
    }

    public void setAnswerStandards(AnswerStandards answerStandards) {
        this.answerStandards = answerStandards;
    }

    public AuthStandards getAuthStandards() {
        return authStandards;
    }

    public void setAuthStandards(AuthStandards authStandards) {
        this.authStandards = authStandards;
    }

}
