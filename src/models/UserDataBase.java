package models;

import Helper.FileOperations;

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

    public boolean addNewUser(User user) {
        String login = user.getUserName();
        if (!isLoginAlreadyInUse(login)) {
            listOfUsers.add(user);
            FileOperations.saveUserList(listOfUsers, fileName);
            return true;
        }
        return false;
    }

    public boolean isUserPresentInDataBase(User user) {
        for (User userSearch : listOfUsers) {
            if (userSearch.getPassword().equals(user.getPassword()) && userSearch.getUserName().equals(user.getUserName()))
                return true;
        }
        return false;
    }

    private boolean isLoginAlreadyInUse(String login) {
        for (User user: listOfUsers) {
            if (user.getUserName().equals(login))
                return true;
        }
        return false;
    }

}
