package project.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AnswerModel {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    private Integer questionId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean correct;
    private String answer;

    @JsonCreator
    public AnswerModel(@JsonProperty("id") Integer id,
                       @JsonProperty("answer") String answer,
                       @JsonProperty("questionId") Integer questionId,
                       @JsonProperty("correct") Boolean correct) {
        this.id = id;
        this.answer = answer;
        this.questionId = questionId;
        this.correct = correct;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
