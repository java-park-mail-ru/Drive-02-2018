package project.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SetModel {
    private QuestionModel question;
    private List<AnswerModel> answers;


    @JsonCreator
    public SetModel(@JsonProperty("question") QuestionModel question,
                    @JsonProperty("answers") List<AnswerModel> answers) {
        this.question = question;
        this.answers = answers;
    }

    public SetModel(QuestionModel question) {
        this.question = question;
    }

    public QuestionModel getQuestion() {
        return question;
    }

    public void setQuestion(QuestionModel question) {
        this.question = question;
    }

    public List<AnswerModel> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerModel> answers) {
        this.answers = answers;
    }

    public Integer getQuestionId() {
        return question.getId();
    }
}
