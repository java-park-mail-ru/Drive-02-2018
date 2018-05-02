package project.models;


public class QuestionAndAnswer {

    private Integer answerId;
    private Integer questionId;
    private Boolean correct;
    private String answer;
    private String question;
    private String theme;


    public QuestionAndAnswer(Integer answerId, Integer questionId,
                             Boolean correct, String answer,
                             String question, String theme) {
        this.answerId = answerId;
        this.questionId = questionId;
        this.correct = correct;
        this.answer = answer;
        this.question = question;
        this.theme = theme;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}
