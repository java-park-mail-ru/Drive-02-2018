package main.models;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@Service
public class Users {

    private static final HashMap<String, User> USERS = new HashMap<String, User>() {
        {
            final User andrey = new User("andreyBabkov@mail.ru", "LoginA", "2131110");
            final User zhenya = new User("starina@mail.ru", "LoginZ", "21334*sds");
            final User masha  = new User("masha@mail.ru", "LoginM", "2133");

            put(andrey.getMail(), andrey);
            put(zhenya.getMail(), zhenya);
            put(masha.getMail(), masha);
        }
    };


    public static void addUser(User user) {
        USERS.put(user.getMail(), user);
    }

    public static boolean wasUser(User user) {
        return (!StringUtils.isEmpty(USERS.get(user.getMail())));
    }

    public static User getUser(User user) {
        return  USERS.get(user.getMail());
    }

    public static User getUserByMail(String mail) {
        return  USERS.get(mail);
    }

    public static boolean checkUsers(List<? extends User> users) {
        return true;
    }

    public static List<?> getUsers() {
        return Collections.emptyList();
    }

}
