package main.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicLong;

public class User {

    private Long id;

    @JsonProperty(value = "mail")
    private String mail;

    @JsonProperty(value = "password")
    private String password;

    @JsonProperty(value = "login")
    private String login;

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);

    public User() {
        this.id = ID_GENERATOR.getAndDecrement();
    }

    public User(String mail, String login, String password) {
        this.id = ID_GENERATOR.getAndDecrement();
        this.mail = mail;
        this.password = password;
        this.login = login;
    }

    public boolean sameMailAndPassword(User user) {
        return user.getPassword().equals(password) && user.getMail().equals(mail);
    }

    public Boolean update(User user) {

        final String currentMail = user.getMail();
        final String currentLogin = user.getLogin();
        final String currentPassword = user.getPassword();

        // нечего обновлять
        if (StringUtils.isEmpty(currentMail)
                && StringUtils.isEmpty(currentLogin)
                && StringUtils.isEmpty(currentPassword)) {
            return false;
        }

        if (!StringUtils.isEmpty(currentMail)) {
            this.mail = currentMail;
        }
        if (!StringUtils.isEmpty(currentLogin)) {
            this.login = currentLogin;
        }
        if (!StringUtils.isEmpty(currentPassword)) {
            this.password = currentPassword;
        }

        return true;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }


    // Сеттеры
    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
