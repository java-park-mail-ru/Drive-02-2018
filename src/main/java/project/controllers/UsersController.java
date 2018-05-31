package project.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.models.UserModel;
import project.services.UserService;
import project.status.TemplateMessage;
import project.status.StatusCodes;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;


@RestController
public class UsersController {

    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);


    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity register(@RequestBody UserModel user, HttpSession session) {
        try {
            userService.create(user);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(StatusCodes.ERRORS.USER_EXISTS);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.ERRORS.ERROR);
        }

        session.setAttribute("mail", user.getMail());
        user.setScore(0);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(StatusCodes.returnUser(StatusCodes.SUCCESSES.CREATED, user));
    }


    @PostMapping(value = "/signin", produces = "application/json")
    public ResponseEntity authorize(@RequestBody UserModel user, HttpSession session) {
        final UserModel returnUser;

        try {
            returnUser = userService.signin(user);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.ERRORS.NO_USER);
        }

        session.setAttribute("mail", user.getMail());
        return ResponseEntity.status(HttpStatus.OK)
                .body(StatusCodes.returnUser(StatusCodes.SUCCESSES.SIGNIN, returnUser));
    }


    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity getUser(HttpSession session) {
        final String currentMail = (String) session.getAttribute("mail");
        logger.info("In /user getUser() method. currentMail from COOKIES: " + currentMail);
        if (StringUtils.isEmpty(currentMail)) {
            logger.info("In /user getUser() method. currentMail is empty");
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(StatusCodes.ERRORS.NOT_LOGINED);
        }

        final UserModel returnUser;
        try {
            logger.info("In /user getUser() method. trying get returnUser");
            returnUser = userService.getUserByMail(currentMail);
            logger.info("In /user getUser() method. returnUser is ok");
        } catch (DataAccessException e) {
            logger.info("In /user getUser() method. Got error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.ERRORS.WRONG_COOKIE);
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(StatusCodes.returnUser(StatusCodes.SUCCESSES.INFO, returnUser));
    }



    @PostMapping(value = "/edit", produces = "application/json")
    public ResponseEntity changeUserInfo(@RequestBody UserModel user, HttpSession session) {
        final String currentMail = (String) session.getAttribute("mail");

        if (StringUtils.isEmpty(currentMail)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(StatusCodes.ERRORS.NOT_LOGINED);
        }

        final UserModel returnedUser;
        try {
            returnedUser = userService.edit(user, currentMail);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(StatusCodes.ERRORS.USER_EXISTS);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(StatusCodes.ERRORS.NO_USER);
        }

        final String newMail = user.getMail();
        if (!StringUtils.isEmpty(newMail)) {
            session.setAttribute("mail", user.getMail());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(StatusCodes.returnUser(StatusCodes.SUCCESSES.UPDATED, returnedUser));
    }


    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity logout(HttpSession session) {
        final String currentMail = (String) session.getAttribute("mail");

        if (StringUtils.isEmpty(currentMail)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(StatusCodes.ERRORS.NOT_LOGINED);
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK)
                .body(StatusCodes.SUCCESSES.SUCCESS_LOGOUT);
    }


    @GetMapping(value = "/leaders", produces = "application/json")
    public ResponseEntity loadLeaders(@RequestParam(value = "limit") int limit,
                                      @RequestParam(value = "offset") int offset) {
        try {
            final UserModel[] users = userService.getLeaders(offset, limit);
            final TemplateMessage response =
                new TemplateMessage(true, StatusCodes.SUCCESSES.GET_LEADERS.toString());
            response.setUsers(users);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(StatusCodes.ERRORS.ERROR);
        }
    }
}
