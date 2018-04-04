package project.status;

import project.models.UserModel;

import java.util.HashMap;


public class StatusCodes {

    private static final HashMap<String, String> ERRORS = new HashMap<String, String>() {
        {
            put("USER_EXISTS", "User already exists");
            put("ERROR", "Something goes wrong. Check your input information");
            put("NO_USER", "User with this email and password doesn't exists");
            put("WRONG_COOKIE", "Wrong session");
            put("NOT_LOGINED", "User isn't logined");
        }
    };


    private static final HashMap<String, String> SUCCESSES = new HashMap<String, String>() {
        {
            put("SUCCESS_LOGOUT", "Your have successfully logout");
            put("CREATED", "Success creation");
            put("SIGNIN", "You have successfull logined");
            put("INFO", "Your account information");
            put("UPDATED", "You have updated your account information");
            put("GET_LEADERS", "Leaders are here");
        }
    };

    public static Message getSuccessCode(String code) {
        final String answer = SUCCESSES.get(code);
        return new Message(true, answer);
    }

    public static Message getErrorCode(String code) {
        final String answer = ERRORS.get(code);
        return new Message(false, answer);
    }

    public static Message returnUser(String code, UserModel user) {
        return new Message(true, SUCCESSES.get(code), user);
    }

}
