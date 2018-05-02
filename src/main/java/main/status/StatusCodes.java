package main.status;

import java.util.HashMap;


public class StatusCodes {

    private static final HashMap<String, String> ERRORS = new HashMap<String, String>() {
        {
            put("USER_ALREADY_EXISTS", "User already exists");
            put("WITHOUT_MAIL", "You need to write mail");
            put("WITHOUT_PASSWORD", "You need to write password");
            put("NO_SUCH_MAIL", "User with this mail doesn't exist");
            put("WRONG_PASSWORD", "Wrong password");
            put("NOT_LOGINED", "User isn't logined");
            put("INCORRECT_SESSION", "Bad cookie");
            put("NOTHING_TO_UPDATE", "You need to change something");
        }
    };


    private static final HashMap<String, String> SUCCESSES = new HashMap<String, String>() {
        {
            put("SUCCESS_NEW_USER", "User was successfully added");
            put("SUCCESS_SIGNIN", "You have successfully logined");
            put("SUCCESS_GET_USER", "Your user is here");
            put("SUCCESS_GET_LEADERS", "Top players are sent");
            put("SUCCESS_LOGOUT", "Your have successfully logout");
            put("SUCCESS_UPDATE_PROFILE", "Your have successfully update your profile");
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

}
