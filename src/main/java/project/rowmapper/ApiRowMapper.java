package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import project.models.UserModel;

public class ApiRowMapper {

    private static RowMapper<UserModel> user = (rs, rowNum) ->
        new UserModel(
                rs.getString("email"),
                rs.getString("login"),
                rs.getInt("score"));

    private static RowMapper<UserModel> loginAndScore = (rs, rowNum) ->
            new UserModel(
                    rs.getString("login"),
                    rs.getInt("score"));

    @SuppressWarnings("unused")
    public static RowMapper<UserModel> getUserLoginAndScore() {
        return loginAndScore;
    }

    @SuppressWarnings("unused")
    public static RowMapper<UserModel> getUser() {
        return user;
    }
}
