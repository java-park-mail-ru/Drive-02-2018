package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import project.models.AnswerModel;
import project.models.QuestionAndAnswer;
import project.models.QuestionModel;
import project.models.UserModel;

public class ApiRowMapper {

    private static RowMapper<UserModel> user = (rs, rowNum) ->
        new UserModel(
                rs.getString("email"),
                rs.getString("login"),
                rs.getInt("score"));

    private static RowMapper<UserModel> loginAndScore = (rs, rowNum) ->
            new UserModel(
                    rs.getString("login"),
                    rs.getInt("score"));

    @SuppressWarnings("unused")
    public static RowMapper<UserModel> getUserLoginAndScore() {
        return loginAndScore;
    }

    @SuppressWarnings("unused")
    public static RowMapper<UserModel> getUser() {
        return user;
    }

    private static RowMapper<QuestionModel> getQuestion = (rs, rowNum) ->
            new QuestionModel(rs.getString("question"),
                              rs.getString("theme"),
                              rs.getInt("id"));

    private static RowMapper<AnswerModel> getAnswer = (rs, rowNum) ->
            new AnswerModel(rs.getInt("answer_num"),
                    rs.getString("answer"),
                    rs.getInt("question_id"),
                    rs.getBoolean("correct"));


    private static RowMapper<QuestionAndAnswer> getQuestionWithAnser = (rs, rowNum) ->
            new QuestionAndAnswer(rs.getInt("answer_num"),
                    rs.getInt("question_id"),
                    rs.getString("answer"),
                    rs.getString("question"),
                    rs.getString("theme"));
}
