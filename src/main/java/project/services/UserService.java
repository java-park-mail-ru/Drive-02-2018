package project.services;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import project.models.UserModel;
import project.rowmapper.ApiRowMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private JdbcTemplate jdbcTemplate;


    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(UserModel user) {
        final String sql =
            "INSERT INTO users (email, login, password) VALUES (?, ?, crypt(?, gen_salt('bf')));";
        jdbcTemplate.update(sql, user.getMail(), user.getLogin(), user.getPassword());
    }


    public UserModel signin(UserModel user) {
        final String sql =
            "SELECT email, login, score FROM users WHERE email = ? AND password = crypt(?, password);";
        return jdbcTemplate.queryForObject(sql, ApiRowMapper.getUser(), user.getMail(), user.getPassword());

    }


    public UserModel getUserByMail(String mail) {
        final String sql =
                "SELECT email, login, score FROM users WHERE email = ?";
        return jdbcTemplate.queryForObject(sql, ApiRowMapper.getUser(), mail);
    }


    public UserModel edit(UserModel user, String sessionMail) {
        String sql = "UPDATE users SET";
        final ArrayList<String> values = new ArrayList<>();

        String changedMail = sessionMail;
        if (user.getMail() != null) {
            sql += " email = ?,";
            values.add(user.getMail());
            changedMail = user.getMail();
        }

        if (user.getPassword() != null) {
            sql += " password = crypt(?, gen_salt('bf')),";
            values.add(user.getPassword());
        }

        if (user.getLogin() != null) {
            sql += " login = ?,";
            values.add(user.getLogin());
        }

        if (values.isEmpty()) {
            return getUserByMail(sessionMail);
        }

        sql = sql.substring(0, sql.length() - 1);
        sql += " WHERE email = ?";
        values.add(sessionMail);
        jdbcTemplate.update(sql, values.toArray());

        return getUserByMail(changedMail);

    }


    public UserModel[] getLeaders(int startPos, int amount) {
        final String sql = "SELECT login, score FROM users ORDER BY score DESC OFFSET ? LIMIT ?";
        final List<UserModel> users = jdbcTemplate.query(sql, ApiRowMapper.getUserLoginAndScore(), startPos, amount);
        final UserModel[] arrayOfUsers = new UserModel[users.size()];
        return users.toArray(arrayOfUsers);
    }

}
