package project.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.models.UserModel;

import javax.validation.constraints.NotNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class UserServiceTest {
    @Autowired
    private UserService userService;
    private UserModel user;

    public UserServiceTest() {
        this.user = new UserModel("tester@mail.ru","tester", 0, "12345qwerty");
    }


    private static void assertDifference(@NotNull UserModel expected, @NotNull UserModel actual) {
        assertEquals( expected.getMail(), actual.getMail(), "Wrong Mail");
        assertEquals(expected.getLogin(), actual.getLogin(), "Wrong Login");
        assertEquals( expected.getScore(), actual.getScore(), "Wrong Score");
    }


    private String changeString(String str) {
      return str + '1';
    }

    @Test
    @DisplayName("User creation")
    public void testCreate() {
        userService.create(user);
        final UserModel userFromTable= userService.getUserByMail(user.getMail());
        assertDifference(user, userFromTable);
    }

    @Test
    @DisplayName("Register user with same mail")
    public void insertWithSameMail() {
        final UserModel user2 =
            new UserModel(user.getMail(), changeString(user.getLogin()), user.getScore(), user.getPassword());
        userService.create(user);
        userService.create(user2);
    }

    @Test
    @DisplayName("Register user with same login")
    public void insertWithSameLogin() {
        final UserModel user2 =
           new UserModel(changeString(user.getMail()), user.getLogin(), user.getScore(), user.getPassword());
        userService.create(user);
        userService.create(user2);
    }

    @Test
    @DisplayName("Get nonexistent user")
    public void testGetEmtyUser() {
        final UserModel userFromTable= userService.getUserByMail("random_mail");
        assertNull(userFromTable);
    }

    @Test
    @DisplayName("Sigining")
    public void testSignIn() {
        userService.create(user);
        final UserModel returnedUser = userService.signin(user);
        assertDifference(user, returnedUser);
    }

    @Test
    @DisplayName("Sign in nonexistent")
    public void testSignInNonexistent() {
        final UserModel newUser = new UserModel("default", "default", 2);
        userService.signin(newUser);
    }

    @Test
    @DisplayName("Editing user mail")
    public void editUserMail() {
        userService.create(user);
        final String oldMail = user.getMail();
        final String newMail = changeString(user.getMail());
        final UserModel newUser = new UserModel(newMail, user.getLogin(), user.getScore());
        assertDifference(newUser, userService.edit(newUser, oldMail));
    }

    @Test
    @DisplayName("Editing user login")
    public void editUserLogin() {
        userService.create(user);
        final String newLogin = changeString(user.getLogin());
        final UserModel newUser = new UserModel(user.getMail(), newLogin, user.getScore());
        assertDifference(newUser, userService.edit(newUser, user.getMail()));
    }

    @Test
    @DisplayName("Editing user login")
    public void editUserPassword() {
        userService.create(user);
        final String newPassword = changeString(user.getPassword());
        final UserModel newUser = new UserModel(user.getMail(), user.getLogin(), user.getScore(), newPassword);
        userService.edit(newUser, user.getMail());
        assertDifference(newUser, userService.signin(newUser));
    }

// TO DO сеттор для счета)))
//    @Test
//    @DisplayName("Showing leaders")
//    public void getLeasersTest() {
//        final UserModel newUser = new UserModel(changeString(user.getMail()),
//                changeString(user.getLogin()),
//                user.getScore() + 1,
//                changeString(user.getPassword()));
//
//        userService.create(user);
//        userService.create(newUser);
//
//        //userService.edit()
//
//        ArrayList<UserModel> users = new ArrayList<>();
//        users.add(newUser);
//        users.add(user);
//        Collections.sort(users, (UserModel u1, UserModel u2) -> (u1.getScore() < u2.getScore()) ? 1 : 0);
//
//        final UserModel[] leaders = userService.getLeaders(0, users.size());
//        int i = 0;
//        for (UserModel u : users) {
//            assertEquals(u.getScore(), leaders[i].getScore());
//            assertEquals(u.getLogin(), leaders[i].getLogin());
//            i++;
//        }
//
//    }
}
