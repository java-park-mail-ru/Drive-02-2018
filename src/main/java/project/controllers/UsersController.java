package project.controllers;


import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.models.UserModel;
import project.services.UserService;
import project.status.Message;
import project.status.StatusCodes;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;



@RestController
public class UsersController {

    private UserService userService;


    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity register(@RequestBody UserModel user, HttpSession session) {
        try {
            userService.create(user);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(StatusCodes.getErrorCode("USER_EXISTS"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.getErrorCode("ERROR"));
        }

        session.setAttribute("mail", user.getMail());
        // Пароль null, чтобы он не отсылался в JSON-е
        user.setPassword(null);
        user.setScore(0);
        return ResponseEntity.status(HttpStatus.CREATED).body(StatusCodes.returnUser("CREATED", user));
    }


    @PostMapping(value = "/signin", produces = "application/json")
    public ResponseEntity authorize(@RequestBody UserModel user, HttpSession session) {
        final UserModel returnUser;

        try {
            returnUser = userService.signin(user);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.getErrorCode("NO_USER"));
        }

        session.setAttribute("mail", user.getMail());
        return ResponseEntity.status(HttpStatus.OK).body(StatusCodes.returnUser("SIGNIN", returnUser));
    }


    @GetMapping(value = "/user", produces = "application/json")
    public ResponseEntity getUser(HttpSession session) {
        final String currentMail = (String) session.getAttribute("mail");

        if (StringUtils.isEmpty(currentMail)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(StatusCodes.getErrorCode("NOT_LOGINED"));
        }

        final UserModel returnUser;
        try {
            returnUser = userService.getUserByMail(currentMail);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(StatusCodes.getErrorCode("WRONG_COOKIE"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(StatusCodes.returnUser("INFO", returnUser));
    }


    @PostMapping(value = "/edit", produces = "application/json")
    public ResponseEntity changeUserInfo(@RequestBody UserModel user, HttpSession session) {
        final String currentMail = (String) session.getAttribute("mail");

        if (StringUtils.isEmpty(currentMail)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(StatusCodes.getErrorCode("NOT_LOGINED"));
        }

        final UserModel returnedUser;
        try {
            returnedUser = userService.edit(user, currentMail);
        } catch (DuplicateKeyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(StatusCodes.getErrorCode("USER_EXISTS"));
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(StatusCodes.getErrorCode("NO_USER"));
        }

        final String newMail = user.getMail();
        if (!StringUtils.isEmpty(newMail)) {
            session.setAttribute("mail", user.getMail());
        }

        return ResponseEntity.status(HttpStatus.OK)
                .body(StatusCodes.returnUser("UPDATED", returnedUser));
    }


    @PostMapping(value = "/logout", produces = "application/json")
    public ResponseEntity logout(HttpSession session) {
        final String currentMail = (String) session.getAttribute("mail");

        if (StringUtils.isEmpty(currentMail)) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body(StatusCodes.getErrorCode("NOT_LOGINED"));
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.OK)
                .body(StatusCodes.getSuccessCode("SUCCESS_LOGOUT"));
    }


    @GetMapping(value = "/leaders/{startPos:[\\d]+}/{amount:[\\d]+}", produces = "application/json")
    public ResponseEntity loadLeaders(@PathVariable int startPos, @PathVariable int amount) {
        try {
            final UserModel[] users = userService.getLeaders(startPos, amount);
            final Message response = StatusCodes.getSuccessCode("GET_LEADERS");
            response.setUsers(users);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (DataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(StatusCodes.getErrorCode("ERROR"));
        }
    }

}
