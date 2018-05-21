package project.mechanics.game;

import project.models.SetModel;


import java.util.ArrayList;


public class GameUser {

    private Long userId;
    private String selectedTheme;
    private ArrayList<SetModel> questions;
    private ArrayList<Integer> answers;
    private Integer correctAnswerAmount;

    public GameUser(Long userId) {
        this.userId = userId;
        answers = new ArrayList<Integer>();
        correctAnswerAmount = 0;
    }

    public Long getUserId() {
        return userId;
    }

    public String getSelectedTheme() {
        return selectedTheme;
    }

    public void setSelectedTheme(String selectedTheme) {
        this.selectedTheme = selectedTheme;
    }

    public void setQuestions(ArrayList<SetModel> questions) {
        this.questions = questions;
    }

    public ArrayList<SetModel> getQuestions() {
        return questions;
    }

    public void addAnswer(Integer answer) {
        answers.add(answer);
    }

    public Integer getAnswersSize() {
        return answers.size();
    }

    public Integer getLastAnswer() {
        return answers.get(answers.size() - 1);
    }

    public Integer getCorrectAnswerAmount() {
        return correctAnswerAmount;
    }

    public void setCorrectAnswerAmount(Integer correctAnswerAmount) {
        this.correctAnswerAmount = correctAnswerAmount;
    }

    public void incrementCorrectAnswers() {
        this.correctAnswerAmount++;
    }
}
