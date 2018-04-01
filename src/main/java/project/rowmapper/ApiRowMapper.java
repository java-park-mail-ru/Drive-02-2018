package project.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import project.models.UserModel;

public class ApiRowMapper {

    public static RowMapper<UserModel> getUser = (rs, rowNum) ->
        new UserModel (
                rs.getString("email"),
                rs.getString("login"),
                rs.getInt("score"));

    public static RowMapper<UserModel> getUserLoginAndScore = (rs, rowNum) ->
            new UserModel (
                    rs.getString("login"),
                    rs.getInt("score"));

}
