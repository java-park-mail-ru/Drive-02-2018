package project.services;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.models.UserModel;

import javax.validation.constraints.NotNull;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserServiceTest {
    @Autowired
    private UserService userService;
    private UserModel user;

    public UserServiceTest() {
        this.user = new UserModel("tester@mail.ru","tester", 0, "12345qwerty");
    }


    private void assertDifference(@NotNull UserModel expected, @NotNull UserModel actual) {
        assertEquals("Wrong Mail", expected.getMail(), actual.getMail());
        assertEquals("Wrong Login", expected.getLogin(), actual.getLogin());
        assertEquals("Wrong Score", expected.getScore(), actual.getScore());
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

    @Test(expected = DuplicateKeyException.class)
    @DisplayName("Register user with same mail")
    public void insertWithSameMail() {
        final UserModel user2 =
            new UserModel(user.getMail(), changeString(user.getLogin()), user.getScore(), user.getPassword());
        userService.create(user);
        userService.create(user2);
    }

    @Test(expected = DuplicateKeyException.class)
    @DisplayName("Register user with same login")
    public void insertWithSameLogin() {
        final UserModel user2 =
           new UserModel(changeString(user.getMail()), user.getLogin(), user.getScore(), user.getPassword());
        userService.create(user);
        userService.create(user2);
    }

    @Test(expected = DataAccessException.class)
    @DisplayName("Get nonexistent user")
    public void testGetEmtyUser() {
        final UserModel userFromTable= userService.getUserByMail("random_mail");
        assertEquals("Nonexistent users exists", userFromTable, null);
    }

    @Test
    @DisplayName("Sigining")
    public void testSignIn() {
        userService.create(user);
        final UserModel returnedUser = userService.signin(user);
        assertDifference(user, returnedUser);
    }

    @Test(expected = DataAccessException.class)
    @DisplayName("Sign in nonexistent")
    public void testSignInNonexistent() {
        final UserModel newUser = new UserModel("default", "default", 2);
        userService.signin(newUser);
    }

    @Test
    @DisplayName("Editing user mail")
    public void EditUserMail() {
        userService.create(user);
        final String oldMail = user.getMail();
        final String newMail = changeString(user.getMail());
        final UserModel newUser = new UserModel(newMail, user.getLogin(), user.getScore());
        assertDifference(newUser, userService.edit(newUser, oldMail));
    }

    @Test
    @DisplayName("Editing user login")
    public void EditUserLogin() {
        userService.create(user);
        final String newLogin = changeString(user.getLogin());
        final UserModel newUser = new UserModel(user.getMail(), newLogin, user.getScore());
        assertDifference(newUser, userService.edit(newUser, user.getMail()));
    }

    @Test
    @DisplayName("Editing user login")
    public void EditUserPassword() {
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
