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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;



    @JsonCreator
    public UserModel(@JsonProperty("mail") String mail,
                     @JsonProperty("login") String login,
                     @JsonProperty("score") Integer score,
                     @JsonProperty("password") String password,
                     @JsonProperty("id") Long id) {
        this.mail = mail;
        this.password = password;
        this.login = login;
        this.score = score;
        this.id = id;
    }

    public UserModel(String mail, String login, Integer score, String password) {
        this.mail = mail;
        this.login = login;
        this.score = score;
        this.password = password;
    }

    public UserModel(String mail, String login, Integer score, Long id) {
        this.mail = mail;
        this.login = login;
        this.score = score;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public static UserModel getUser(ResultSet rs, int amount) throws SQLException {
        return new UserModel(
                rs.getString("email"),
                rs.getString("login"),
                rs.getInt("score"),
                rs.getLong("id"));
    }


    public static UserModel getUserLoginAndScore(ResultSet rs, int amount) throws SQLException  {
        return new UserModel(
                rs.getString("login"),
                rs.getInt("score"));
    }

    public static int compareScore(UserModel first, UserModel second) {
        if (first.getScore().equals(second.getScore())) {
            return 0;
        }
        return (first.getScore() < second.getScore()) ? 1 : -1;
    }

    @Override
    public boolean equals(Object other) {
        final UserModel otherUser = (UserModel) other;
        return login.equals(otherUser.getLogin())
                && mail.equals(otherUser.getMail())
                && score.equals(otherUser.getScore());
    }
}
