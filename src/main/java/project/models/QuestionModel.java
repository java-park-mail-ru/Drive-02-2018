package project.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


public class QuestionModel {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id;
    private String question;
    private String theme;

    @JsonCreator
    public QuestionModel(@JsonProperty("question") String question,
                         @JsonProperty("theme") String theme,
                         @JsonProperty("id") Integer id) {
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
    public boolean equals(Object o) {

        if (!(o instanceof QuestionModel)) {
            return false;
        }

        final QuestionModel rhs = (QuestionModel) o;
        return this.id.equals(rhs.getId());
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    Object as = new Object();

}
