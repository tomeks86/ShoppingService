package models;

import Helper.FileOperations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDataBase {
    private ArrayList<User> listOfUsers;
    private String fileName;

    public UserDataBase() {
        fileName = "Users.bin";
        listOfUsers = FileOperations.loadUserList(fileName);
    }

    public UserDataBase(String fileName) {
        this.fileName = fileName;
        listOfUsers = FileOperations.loadUserList(fileName);
    }

    public ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public boolean addNewUser(User user, Connection connection) {
        String login = user.getUserName();

        if (!isLoginAlreadyInUse(login, connection)) {
            try {
                Statement statement = connection.createStatement();
                statement.executeUpdate("INSERT INTO users (login,password) VALUES ('" + user.getUserName() + "'" + ",'" + user.getPassword() + "')");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }

    public boolean isUserPresentInDataBase(User user, Connection connection) {
        String login = null, password = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSetLogin;
            ResultSet rspassword;


            resultSetLogin = statement.executeQuery("SELECT login FROM users WHERE login = '" + user.getUserName() + "'");
            while (resultSetLogin.next()) {
                login = resultSetLogin.getString("login");
            }
            rspassword = statement.executeQuery("SELECT password FROM users WHERE password = '" + user.getPassword() + "'");
            while (rspassword.next()) {
                password = rspassword.getString("password");
            }

            if (login.equals(user.getUserName()) && password.equals(user.getPassword()))
                return true;
            else
                return false;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("database problem");
            return false;
        }
    }

    private boolean isLoginAlreadyInUse(String login, Connection connection) { // jezeli jest to true
        String loginCheck = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet isLoginInBase;
            isLoginInBase = statement.executeQuery("SELECT login FROM users WHERE login = '" + login + "'");
            while (isLoginInBase.next()) {
                loginCheck = isLoginInBase.getString("login");
            }

            if (loginCheck != null)
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
