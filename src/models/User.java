package models;

import java.io.Serializable;

public class User implements Serializable {
    private String userName, password;

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

        if (password.length() > 3 || split(userName) < 1 || split(password) < 1)
        return true;

        return false;

    }

    private Integer split(String string) {

        return (string.split("[ ]").length);
    }
}
