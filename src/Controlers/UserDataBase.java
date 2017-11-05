package Controlers;

import Helper.FileOperations;
import models.User;

import java.util.ArrayList;

public class UserDataBase {
    private ArrayList<User> listOfUsers;

    public UserDataBase() {
        listOfUsers = FileOperations.loadUserList("User.bin"); //  FIXME load/save -- nie static, przyjmuje w parametrze scie

    }

    public ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public boolean addNewUser(User user) {
        listOfUsers.add(user);  // FIXME tutaj dac zapis listy
        return true;

    }

    public boolean isUserPresentInDataBase(User user) {
        for (User userSearch : listOfUsers) {
            if (userSearch.getPassword().equals(user.getPassword()) && userSearch.getUserName().equals(user.getUserName()))
                return true;
        }
        return false;
    }

    public boolean isLoginAlreadyInUse(String login) {
        for (User user: listOfUsers) {
            if (user.getUserName().equals(login))
                return true;
        }
        return false;
    }

}
