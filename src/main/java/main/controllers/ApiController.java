package main.controllers;


import main.models.User;
import main.models.Users;
import main.status.Message;
import main.status.StatusCodes;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


@RestController
public class ApiController {

    @PostMapping(value = "/register", produces = "application/json")
    public Message register(@RequestBody User user) {

        if (Users.wasUser(user)) {
            return StatusCodes.getErrorCode("USER_ALREADY_EXISTS");
        }

        System.out.println("Юзера не было");
        Users.addUser(user);
        return StatusCodes.getSuccessCode("SUCCESS_NEW_USER");
    }


    @PostMapping(value = "/signin", produces = "application/json")
    public Message authorize(@RequestBody User user, HttpSession session) {

        final String mail = user.getMail();
        final String password = user.getPassword();

        if (StringUtils.isEmpty(mail)) {
            return StatusCodes.getErrorCode("WITHOUT_MAIL");
        }

        if (StringUtils.isEmpty((password))) {
            return StatusCodes.getErrorCode("WITHOUT_PASSWORD");
        }

        if (!Users.wasUser(user)) {
            return StatusCodes.getErrorCode("NO_SUCH_MAIL");
        }

        final User userToCheck = Users.getUser(user);
        if (!userToCheck.sameMailAndPassword(user)) {
            return StatusCodes.getErrorCode("WRONG_PASSWORD");
        }

        // Единственный случай успеха
        session.setAttribute("mail", mail);
        return StatusCodes.getSuccessCode("SUCCESS_SIGNIN");
    }


    @PostMapping(value = "/user", produces = "application/json")
    public Message getUser(HttpSession session) {
        final String currentMail = (String) session.getAttribute("mail");

        if (StringUtils.isEmpty(currentMail)) {
            return StatusCodes.getErrorCode("NOT_LOGINED");
        }


        final Message responseMessage = StatusCodes.getSuccessCode("SUCCESS_GET_USER");
        responseMessage.setUser(Users.getUserByMail(currentMail));
        return responseMessage;
    }



    @PostMapping(value = "/edit", produces = "application/json")
    public Message changeUser(@RequestBody User user, HttpSession session) {
        final String currentMail = (String) session.getAttribute("mail");

        if (StringUtils.isEmpty(currentMail)) {
            return StatusCodes.getErrorCode("NOT_LOGINED");
        }

        final User userToBeChanged = Users.getUserByMail(user.getMail());

        // To do:
        // почта - уникальный ключ, ее пока нельзя менять
        if (userToBeChanged == null) {
            return StatusCodes.getErrorCode("NO_SUCH_MAIL");
        }

        if (!userToBeChanged.update(user)) {
            return StatusCodes.getErrorCode("NOTHING_TO_UPDATE");
        }

        return StatusCodes.getSuccessCode("SUCCESS_UPDATE_PROFILE");
    }


    @PostMapping(value = "/logout", produces = "application/json")
    public Message logout(HttpSession session) {
        final String currentMail = (String) session.getAttribute("mail");

        if (StringUtils.isEmpty(currentMail)) {
            return StatusCodes.getErrorCode("NOT_LOGINED");
        }

        session.invalidate();
        return StatusCodes.getSuccessCode("SUCCESS_LOGOUT");
    }

}
