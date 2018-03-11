package main.models;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


@Service
public class Users {

    private static final HashMap<String, User> USERS = new HashMap<String, User>() {
        {
            final User andrey = new User("andreyBabkov@mail.ru", "LoginA", "2131110", 25);
            final User zhenya = new User("starina@mail.ru", "LoginZ", "21334*sds", 1);
            final User masha  = new User("masha@mail.ru", "LoginM", "2133", 12);

            put(andrey.getMail(), andrey);
            put(zhenya.getMail(), zhenya);
            put(masha.getMail(), masha);
        }
    };


    public static void addUser(User user) {
        USERS.put(user.getMail(), user);
    }

    public static void deleteUser(User user) {
        USERS.remove(user.getMail());
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

    public static User[] getArrayOfUsers() {
        final Collection<User> values = USERS.values();
        User[] targetArray = values.toArray(new User[values.size()]);
        return targetArray;
    }

}
