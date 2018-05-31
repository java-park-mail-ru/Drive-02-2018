package project.status;

import project.models.UserModel;


public class StatusCodes {
    public enum ERRORS {
        USER_EXISTS("User already exists"),
        NO_USER("User with this email and password doesn't exists"),
        ERROR("Something goes wrong. Check your input information"),
        WRONG_COOKIE("Wrong session"),
        NOT_LOGINED("User isn't logined");

        private String code;

        ERRORS(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return this.code;
        }
    }

    public enum SUCCESSES {
        SUCCESS_LOGOUT("Your have successfully logout"),
        CREATED("Success creation"),
        SIGNIN("You have successfull logined"),
        INFO("Your account information"),
        UPDATED("You have updated your account information"),
        GET_LEADERS("Leaders are here");

        private String code;

        SUCCESSES(String code) {
            this.code = code;
        }

        @Override
        public String toString() {
            return code;
        }
    }

    public static TemplateMessage returnUser(SUCCESSES code, UserModel user) {
        return new TemplateMessage(true, code.toString(), user);
    }
}
