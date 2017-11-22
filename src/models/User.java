package models;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User implements Serializable {
    private String userName, password;
    int id;

    public User(String userName, String password) {
        if (isValid(userName, password)) {
            this.userName = userName;
            this.password = password;
        } else
            throw new IllegalArgumentException("Wrong password or login!\npassword longer than 3 characters\nspace not allowed in both Login and password");
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    private boolean isValid(String userName, String password) {
        return (password.length() > 3 && split(userName) == 1 && split(password) == 1 && userName.length() > 0);
    }

    private Integer split(String string) {
        return (string.split("[ ]").length); // String.conta
    }

    public boolean equals(User user) {
        return (this.getUserName().equals(user.getUserName()) && (this.getPassword().equals(user.getPassword())));
    }

    public int getUserId(Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery("SELECT id FROM users WHERE login = '" + this.getUserName() + "'");
            while (rs.next()) {
                this.id = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.id;
    }
}
