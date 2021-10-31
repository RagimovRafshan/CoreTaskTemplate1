package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static final String URL = "jdbc:mysql://localhost:3306/usersschema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    Connection connection;


    public UserServiceImpl() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    public void createUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.execute(new StringBuilder().append("CREATE TABLE IF NOT EXISTS `usersschema`.`users` (\n")
                                                 .append("  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,\n")
                                                 .append("  `name` VARCHAR(80) NOT NULL,\n")
                                                 .append("  `lastName` VARCHAR(80) NOT NULL,\n")
                                                 .append("  `age` INT(3) NOT NULL,\n")
                                                 .append("  PRIMARY KEY (`id`));").toString());
            statement.execute("USE `users`;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Statement statement = connection.createStatement()) {
            String text = new StringBuilder().append("INSERT INTO users(name, lastName, age) VALUES('").append(name).append("', '").append(lastName).append("', ").append(age).append(");").toString();
            statement.execute(text);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Statement statement = connection.createStatement()) {
            String text = "DELETE FROM users WHERE id=" + id + ";";
            statement.execute(text);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM users;");) {
            User user;
            long id;
            while(rs.next()) {
                id = rs.getLong("id");
                user = new User(rs.getString("name"), rs.getString("lastName"), rs.getByte("age"));
                user.setId(id);
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM users;");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
