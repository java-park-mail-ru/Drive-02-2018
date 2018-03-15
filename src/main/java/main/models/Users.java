package main.models;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashMap;


@Service
public class Users {

    private static final HashMap<String, User> USERS = new HashMap<String, User>() {
        {
            final User andrey = new User("andreyBabkov@mail.ru", "Andrey", "2131110aaaa", 102);
            final User zhenya = new User("starina@mail.ru", "Starina", "21334sds", 102);
            final User masha  = new User("masha@mail.ru", "Masha", "2133qqqqq", 1340);
            final User user4 = new User("user4@mail.ru", "LoginA", "12345abc", 25);
            final User user5 = new User("user5@mail.ru", "LoginB", "12345abc", 1);
            final User user6 = new User("user6@mail.ru", "LoginC", "12345abc", 12);
            final User user7 = new User("user7@mail.ru", "LoginD", "12345abc", 4);
            final User user8 = new User("user8@mail.ru", "LoginE", "12345abc", 70);
            final User user9 = new User("user9@mail.ru", "LoginF", "12345abc", 13);
            final User user10 = new User("user10@mail.ru", "LoginG", "12345abc", 23);
            final User user11 = new User("user11@mail.ru", "LoginH", "12345abc", 33);
            final User user12 = new User("user12@mail.ru", "LoginJ", "12345abc", 47);
            final User user13 = new User("user13@mail.ru", "LoginK", "12345abc", 33);

            put(andrey.getMail(), andrey);
            put(zhenya.getMail(), zhenya);
            put(masha.getMail(), masha);

            put(user4.getMail(), user4);
            put(user5.getMail(), user5);
            put(user6.getMail(), user6);
            put(user7.getMail(), user7);
            put(user8.getMail(), user8);
            put(user9.getMail(), user9);
            put(user10.getMail(), user10);
            put(user11.getMail(), user11);
            put(user12.getMail(), user12);
            put(user13.getMail(), user13);
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
