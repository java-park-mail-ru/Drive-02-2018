package project.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.ResultSet;
import java.sql.SQLException;


@SuppressWarnings("unused")
public class UserModel {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String mail;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String login;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer score;


    @JsonCreator
    public UserModel(@JsonProperty("mail") String mail,
                     @JsonProperty("login") String login,
                     @JsonProperty("score") Integer score,
                     @JsonProperty("password") String password) {
        this.mail = mail;
        this.password = password;
        this.login = login;
        this.score = score;
    }

    public UserModel(String mail, String login, Integer score) {
        this.mail = mail;
        this.login = login;
        this.score = score;
    }

    public UserModel(String login, Integer score) {
        this.login = login;
        this.score = score;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public static UserModel getUser(ResultSet rs, int amount) throws SQLException {
        return new UserModel(
                rs.getString("email"),
                rs.getString("login"),
                rs.getInt("score"));
    }


    public static UserModel getUserLoginAndScore(ResultSet rs, int amount) throws SQLException  {
        return new UserModel(
                rs.getString("login"),
                rs.getInt("score"));
    }

}
