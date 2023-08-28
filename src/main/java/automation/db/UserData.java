package automation.db;

import automation.Session;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class UserData {
    static public class User
    {
        final public int id;
        final public String first_name;
        final public String last_name;
        final public String login;
        final public String password_hash;
        final public String date_of_birth;

        public User(int id,
                    String first_name,
                    String last_name,
                    String login,
                    String password_hash,
                    String date_of_birth
                    ) {
            this.id = id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.login = login;
            this.password_hash = password_hash;
            this.date_of_birth = date_of_birth;
        }
    }

    public void addUser(UserData.User user) {
        try {
            String query = "INSERT INTO homework_user_data (`id`, `first_name`, `last_name`, `login`, " +
                    "`password_hash`, `date_of_birth`) VALUES (?, ?, ?, ?, ?, STR_TO_DATE(?, '%d-%m-%Y'));";
            PreparedStatement statement = Session.get().mysql().preparedStatement(query);
            statement.setInt(1, user.id);
            statement.setString(2, user.first_name);
            statement.setString(3, user.last_name);
            statement.setString(4, user.login);
            statement.setString(5, user.password_hash);
            statement.setString(6, user.date_of_birth);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUserData (int id) {
        try {
            String query = "SELECT * FROM homework_user_data WHERE id=?;";
            PreparedStatement statement = Session.get().mysql().preparedStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("login"),
                        resultSet.getString("password_hash"),
                        resultSet.getString("date_of_birth"));
            } else {
                return new User(0, null,null, null, null, null);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAll () {
        List<User> result = new LinkedList<>();
        try {
            String query = "SELECT * FROM homework_user_data;";
            PreparedStatement statement = Session.get().mysql().preparedStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                result.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("login"),
                        resultSet.getString("password_hash"),
                        resultSet.getString("date_of_birth")
                        ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public void updateLogin (String newLogin, int id) {
        try {
            String query = "UPDATE homework_user_data " +
                    "SET login = ? " +
                    "WHERE id = ?;";
            PreparedStatement statement = Session.get().mysql().preparedStatement(query);
            statement.setString(1, newLogin);
            statement.setInt(2, id);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser (int id) {
        try {
            String query = "DELETE FROM `homework_user_data` WHERE id = ?;";
            PreparedStatement statement = Session.get().mysql().preparedStatement(query);
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
