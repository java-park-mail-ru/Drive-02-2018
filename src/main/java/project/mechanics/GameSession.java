package project.mechanics;


import org.jetbrains.annotations.Nullable;
import project.mechanics.game.GameUser;
import project.mechanics.messages.CheckedAnswer;
import project.mechanics.messages.NewAnswer;
import project.mechanics.messages.ThemeSelected;
import project.models.SetModel;
import project.services.SingleplayerService;
import project.services.UserService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class GameSession {

    private enum States {
        AWAITING,
        ONE_PLAYER_SELECT_THEME,
        BOTH_SELECTED_THEMES
    }

    private GameUser gameUser1;
    private GameUser gameUser2;
    private final SingleplayerService singleplayerService;
    private States state;
    private ArrayList<SetModel> questions;
    private ArrayList<Integer> correctAnswers;
    private Integer questionsAnswered;
    private UserService userService;
    private Boolean alreadyUpdateScore;

    public GameSession(@NotNull Long userId1,
                       @NotNull Long userId2,
                       @NotNull SingleplayerService singleplayerService,
                       @NotNull String userLogin1,
                       @NotNull String userLogin2,
                       @NotNull UserService userService) {
        this.gameUser1 = new GameUser(userId1, userLogin1);
        this.gameUser2 = new GameUser(userId2, userLogin2);
        this.singleplayerService = singleplayerService;
        this.state = States.AWAITING;
        this.questions = new ArrayList<SetModel>();
        this.correctAnswers = new ArrayList<Integer>();
        this.userService = userService;
        questionsAnswered = 0;
        alreadyUpdateScore = false;
    }

    public void start() {

    }

    private GameUser getUser(Long userId) {
        if (gameUser1.getUserId().equals(userId)) {
           return gameUser1;
        }
        return gameUser2;
    }


    public boolean selectTheme(ThemeSelected themeMessage, Long userId) {
        final GameUser gamer = getUser(userId);
        gamer.setSelectedTheme(themeMessage.getPayLoad());
        gamer.setQuestions(singleplayerService.getSet(4, gamer.getSelectedTheme()));

        if (this.state == States.AWAITING) {
            this.state = States.ONE_PLAYER_SELECT_THEME;
            this.questions.addAll(gamer.getQuestions());
            this.questions.addAll(randomQuestions());
            return false;
        }
        this.state = States.BOTH_SELECTED_THEMES;
        this.questions.addAll(gamer.getQuestions());
        return true;
    }


    private ArrayList<SetModel> randomQuestions() {
        final List<String> themes = singleplayerService.getAllThemes();
        final int randomNum = ThreadLocalRandom.current().nextInt(0, themes.size());
        return singleplayerService.getSet(3, themes.get(randomNum));
    }


    public boolean addAnswer(NewAnswer answer, Long userId) {
        final GameUser gamer = getUser(userId);
        gamer.addAnswer(answer.getPayLoad());

        if ((gameUser1.getAnswersSize().equals(gameUser2.getAnswersSize())) && gameUser1.getAnswersSize() > questionsAnswered) {
            final Integer correctNum = singleplayerService.getCorrectNum(questions.get(questionsAnswered).getQuestionId());
            questionsAnswered++;
            correctAnswers.add(correctNum);

            if (gameUser1.getLastAnswer().equals(correctNum)) {
                gameUser1.incrementCorrectAnswers();
            }
            if (gameUser2.getLastAnswer().equals(correctNum)) {
                gameUser2.incrementCorrectAnswers();
            }
            return true;
        }
        return false;
    }

    public boolean needUpdateScore() {
        if (!alreadyUpdateScore) {
           alreadyUpdateScore = true;
           return true;
        }
        return false;
    }

    public void saveResults() {
        if (gameUser1.getCorrectAnswerAmount() > gameUser2.getCorrectAnswerAmount()) {
            userService.incrementScoreById(gameUser1.getUserId());
        } else if (gameUser1.getCorrectAnswerAmount() < gameUser2.getCorrectAnswerAmount()) {
            userService.incrementScoreById(gameUser2.getUserId());
        }
    }

    public Long getUserId2() {
        return gameUser2.getUserId();
    }

    public Long getUserId1() {
        return gameUser1.getUserId();
    }

    public ArrayList<SetModel> getUser1Questions() {
        return gameUser1.getQuestions();
    }

    public ArrayList<SetModel> getUser2Questions() {
        return gameUser2.getQuestions();
    }

    public ArrayList<SetModel> getQuestions() {
        return questions;
    }

    public Integer getLastCorrectAnswer() {
        return correctAnswers.get(correctAnswers.size() - 1);
    }

    public Integer getLastUserAnswer(Long userId) {
        final GameUser gameUser = this.getUser(userId);
        return gameUser.getLastAnswer();
    }

    public Integer getCorrectAnswersAmount(Long userId) {
        final GameUser gamer = this.getUser(userId);
        return gamer.getCorrectAnswerAmount();
    }

    public String getLoginById(Long id) {
        final GameUser user = this.getUser(id);
        return user.getLogin();
    }
}
