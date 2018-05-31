package project.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import project.models.UserModel;

import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class UserControllerTest {
    private UserModel user;
    private MockHttpSession session;
    @Autowired
    private UsersController usersController;

    @BeforeEach
    void createDefaultUser() {
        user = new UserModel("tester@mail.ru","tester", 0, "12345qwerty");
        session = new MockHttpSession();
    }

    @Test
    @DisplayName("Registration of same user")
    public void testSameRegistration () {
        usersController.register(user, session);
        final ResponseEntity response = usersController.register(user, session);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @DisplayName("Register user")
    public void testRegistration () {
        final ResponseEntity response = usersController.register(user, session);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Sign in test")
    public void testSignIn () {
        final ResponseEntity response1 = usersController.register(user, session);
        assertEquals(HttpStatus.CREATED, response1.getStatusCode());
        final ResponseEntity response2 = usersController.authorize(user, session);
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(session.getAttribute("mail"));
    }

    @Test
    @DisplayName("Logout test")
    public void testLogout () {
        usersController.register(user, session);
        usersController.authorize(user, session);
        usersController.logout(session);
        assertTrue(session.isInvalid());
    }
}
