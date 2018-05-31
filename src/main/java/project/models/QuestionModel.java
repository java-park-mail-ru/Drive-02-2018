package project.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.ResultSet;
import java.sql.SQLException;


public class QuestionModel {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;
    private String question;
    private String theme;

    @JsonCreator
    public QuestionModel(@JsonProperty("question") String question,
                         @JsonProperty("theme") String theme,
                         @JsonProperty("id") int id) {
        this.question = question;
        this.theme = theme;
        this.id = id;
    }

    public QuestionModel(@JsonProperty("question") String question,
                         @JsonProperty("theme") String theme) {
        this.question = question;
        this.theme = theme;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof QuestionModel)) {
            return false;
        }

        final QuestionModel rhs = (QuestionModel) object;
        return this.id == rhs.getId();
    }

    @Override
    public int hashCode() {
        return this.id;
    }


    public static QuestionModel getQuestionMapper(ResultSet rs, int amount) throws SQLException {
       return new QuestionModel(rs.getString("question"),
                rs.getString("theme"),
                rs.getInt("id"));
    }
}
