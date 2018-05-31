package project.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.models.UserModel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class UserServiceTest {
    @Autowired
    private UserService userService;
    private UserModel user;

    @BeforeEach
    void createDefaultUser() {
        this.user = new UserModel("tester@mail.ru","tester", 0, "12345qwerty");
    }


    private String changeString(String str) {
        return str + '1';
    }

    @Test
    @DisplayName("User creation")
    public void testCreate() {
        userService.create(user);
        final UserModel userFromTable= userService.getUserByMail(user.getMail());
        assertEquals(user, userFromTable);
    }

    @Test
    @DisplayName("Register user with same mail")
    public void insertWithSameMail() {
        userService.create(user);
        final UserModel user2 =
            new UserModel(user.getMail(), changeString(user.getLogin()), user.getScore(), user.getPassword());
        final Executable getSameUser = () -> userService.create(user2);
        assertThrows(DuplicateKeyException.class, getSameUser);
    }

    @Test
    @DisplayName("Register user with same login")
    public void insertWithSameLogin() {
        userService.create(user);
        final UserModel user2 =
            new UserModel(changeString(user.getMail()), user.getLogin(), user.getScore(), user.getPassword());
        final Executable sameLogin = () ->userService.create(user2);
        assertThrows(DuplicateKeyException.class, sameLogin);
    }

    @Test
    @DisplayName("Get nonexistent user")
    public void testGetEmtyUser() {
        final Executable getNulluser = () -> userService.getUserByMail("random_mail");
        assertThrows(DataAccessException.class, getNulluser);
    }

    @Test
    @DisplayName("Sigining")
    public void testSignIn() {
        userService.create(user);
        final UserModel returnedUser = userService.signin(user);
        assertEquals(user, returnedUser);
    }

    @Test
    @DisplayName("Sign in nonexistent")
    public void testSignInNonexistent() {
        final UserModel newUser = new UserModel("default", "default", 2);
        final Executable signInFunc = () -> userService.signin(newUser);
       assertThrows(EmptyResultDataAccessException.class, signInFunc);
    }

    @Test
    @DisplayName("Editing user mail")
    public void editUserMail() {
        userService.create(user);
        final String oldMail = user.getMail();
        final String newMail = changeString(user.getMail());
        final UserModel newUser = new UserModel(newMail, user.getLogin(), user.getScore());
        System.out.println('3');
        assertEquals(newUser, userService.edit(newUser, oldMail));
    }

    @Test
    @DisplayName("Editing user login")
    public void editUserLogin() {
        userService.create(user);
        final String newLogin = changeString(user.getLogin());
        final UserModel newUser = new UserModel(user.getMail(), newLogin, user.getScore());
        assertEquals(newUser, userService.edit(newUser, user.getMail()));
    }

    @Test
    @DisplayName("Editing user login")
    public void editUserPassword() {
        userService.create(user);
        final String newPassword = changeString(user.getPassword());
        final UserModel newUser = new UserModel(user.getMail(), user.getLogin(), user.getScore(), newPassword);
        userService.edit(newUser, user.getMail());
        assertEquals(newUser, userService.signin(newUser));
    }


    @Test
    @DisplayName("Showing leaders")
    public void getLeasersTest() {
        final UserModel newUser =
            new UserModel(changeString(user.getMail()), changeString(user.getLogin()),
            user.getScore() + 1, changeString(user.getPassword()));

        userService.create(user);
        userService.create(newUser);
        userService.updateScore(newUser.getMail());

        final List<UserModel> users = new ArrayList<>();
        users.add(newUser);
        users.add(user);
        Collections.sort(users, UserModel::compareScore);

        final UserModel[] leaders = userService.getLeaders(0, users.size());
        int i = 0;
        for (UserModel u : users) {
            assertEquals(u.getScore(), leaders[i].getScore());
            assertEquals(u.getLogin(), leaders[i].getLogin());
            i++;
        }
    }
}