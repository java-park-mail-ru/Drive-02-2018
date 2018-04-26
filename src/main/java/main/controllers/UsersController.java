package main.controllers;


import main.models.User;
import main.models.Users;
import main.status.Message;
import main.status.StatusCodes;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;


@RestController
public class UsersController {

    @PostMapping(value = "/register", produces = "application/json")
    public Message register(@RequestBody User user, HttpSession session) {

        if (StringUtils.isEmpty(user.getMail())) {
            return StatusCodes.getErrorCode("WITHOUT_MAIL");
        }

        if (StringUtils.isEmpty(user.getPassword())) {
            return StatusCodes.getErrorCode("WITHOUT_PASSWORD");
        }

        if (StringUtils.isEmpty(user.getLogin())) {
            return StatusCodes.getErrorCode("WITHOUT_LOGIN");
        }

        if (Users.wasUser(user)) {
            return StatusCodes.getErrorCode("USER_ALREADY_EXISTS");
        }

        Users.addUser(user);
        session.setAttribute("mail", user.getMail());
        return StatusCodes.getSuccessCode("SUCCESS_NEW_USER");
    }


    @PostMapping(value = "/signin", produces = "application/json")
    public Message authorize(@RequestBody User user, HttpSession session) {

        final String mail = user.getMail();

        if (StringUtils.isEmpty(mail)) {
            return StatusCodes.getErrorCode("WITHOUT_MAIL");
        }

        if (StringUtils.isEmpty((user.getPassword()))) {
            return StatusCodes.getErrorCode("WITHOUT_PASSWORD");
        }

        if (!Users.wasUser(user)) {
            System.out.println("in no such mail");
            return StatusCodes.getErrorCode("NO_SUCH_MAIL");
        }

        final User userToCheck = Users.getUser(user);

        if (!userToCheck.sameMailAndPassword(user)) {
            return StatusCodes.getErrorCode("WRONG_PASSWORD");
        }

        System.out.println("going to set cookie");
        session.setAttribute("mail", mail);
        return StatusCodes.getSuccessCode("SUCCESS_SIGNIN");
    }


    @GetMapping(value = "/user", produces = "application/json")
    public Message getUser(HttpSession session) {
        final String currentMail = (String) session.getAttribute("mail");

        if (StringUtils.isEmpty(currentMail)) {
            return StatusCodes.getErrorCode("NOT_LOGINED");
        }

        final User returnUser = Users.getUserByMail(currentMail);

        if (returnUser == null) {
            return StatusCodes.getErrorCode("INCORRECT_SESSION");
        }

        final Message responseMessage = StatusCodes.getSuccessCode("SUCCESS_GET_USER");
        responseMessage.setUser(returnUser);
        return responseMessage;
    }


    @PostMapping(value = "/edit", produces = "application/json")
    public Message changeUserInfo(@RequestBody User user, HttpSession session) {
        final String currentMail = (String) session.getAttribute("mail");

        if (StringUtils.isEmpty(currentMail)) {
            return StatusCodes.getErrorCode("NOT_LOGINED");
        }

        User editableUser = Users.getUserByMail(currentMail);

        if (editableUser == null) {
            return StatusCodes.getErrorCode("NO_SUCH_MAIL");
        }

        final String newMail = user.getMail();

        if (!StringUtils.isEmpty(newMail)) {
            final User userWithNewMail = new User(newMail,
                                                  editableUser.getLogin(),
                                                  editableUser.getPassword(), editableUser.getScore());
            Users.deleteUser(editableUser);
            Users.addUser(userWithNewMail);
            editableUser = userWithNewMail;
            session.setAttribute("mail", newMail);
        }

        if (!editableUser.update(user)) {
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


    @GetMapping(value = "/leaders", produces = "application/json")
    public Message loadLeaders(@RequestParam(value = "limit") int limit,
                               @RequestParam(value = "offset") int offset) {
        final User[] users = Users.getArrayOfUsers();
        final int allUsersLength = users.length;

        if (offset + limit > users.length) {
            limit = users.length - offset;
            if (limit < 0) {
                return StatusCodes.getErrorCode("WRONG_PARAMS");
            }
        }

        Arrays.sort(users, (User o1, User o2) -> User.compareThem(o1, o2));
        final User[] sortedPart = Arrays.copyOfRange(users, offset, offset + limit);

        final Message responseMessage = StatusCodes.getSuccessCode("SUCCESS_GET_LEADERS");
        responseMessage.setUsers(sortedPart);

        final int usersLeft = allUsersLength - offset - sortedPart.length;
        responseMessage.setUsersLeft(usersLeft);
        return responseMessage;
    }

}
