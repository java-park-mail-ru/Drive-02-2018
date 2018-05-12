package project.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerModel {

    private Integer answerNum;
    private Integer questionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean correct;
    private String answer;

    @JsonCreator
    public AnswerModel(@JsonProperty("answerNum") Integer answerNum,
                       @JsonProperty("answer") String answer,
                       @JsonProperty("questionId") Integer questionId,
                       @JsonProperty("correct") Boolean correct) {
        this.answerNum = answerNum;
        this.answer = answer;
        this.questionId = questionId;
        this.correct = correct;
    }

    public AnswerModel(Integer answerNum, String answer, Integer questionId) {
        this.answerNum = answerNum;
        this.answer = answer;
        this.questionId = questionId;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static AnswerModel getAnswerMapper(ResultSet rs, int amount) throws SQLException {
        return new AnswerModel(rs.getInt("answer_num"),
                rs.getString("answer"),
                rs.getInt("question_id"),
                rs.getBoolean("correct"));
    }
}
