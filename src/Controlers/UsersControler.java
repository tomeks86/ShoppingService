package Controlers;

import Helper.FileOperations;
import models.User;
import models.UserDataBase;

import java.util.ArrayList;

public class UsersControler {
    private UserDataBase userDataBase;

    public UsersControler(String userDataBaseFileName) {
        userDataBase = new UserDataBase(userDataBaseFileName);
    }

    public UsersControler() {
        userDataBase = new UserDataBase();
    }

    public boolean addNewUser(User user) {
        return userDataBase.addNewUser(user);
    }

    public boolean isUserPresentInDataBase(User user) {
        return userDataBase.isUserPresentInDataBase(user);
    }

    public void saveUserList(String fileName) {
        FileOperations.saveUserList(userDataBase.getListOfUsers(), fileName);
    }
}
