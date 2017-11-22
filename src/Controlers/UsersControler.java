package Controlers;

import models.User;
import models.UserDataBase;

import java.sql.Connection;

public class UsersControler {
    private UserDataBase userDataBase;

    UsersControler(String userDataBaseFileName) {
        userDataBase = new UserDataBase(userDataBaseFileName);
    }

    public UsersControler() {
        userDataBase = new UserDataBase();
    }

    public boolean addNewUser(User user, Connection connection) {
        return userDataBase.addNewUser(user,connection);
    }

    public boolean isUserPresentInDataBase(User user, Connection connection) {
        return userDataBase.isUserPresentInDataBase(user,connection);
    }

}
