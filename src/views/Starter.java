package views;

import Helper.FileOperations;
import models.User;
import Controlers.AuctionDataBase;
import Helper.Tools;
import Controlers.UserDataBase;

import java.security.AccessControlException;
import java.util.Scanner;

public class Starter {
    private Scanner scanner = new Scanner(System.in);
    private UserDataBase userDataBase = new UserDataBase();

    public Starter() {
        run();
    }

    private void run() {

        Integer end = -1;
        while (end != 0) {
            int value = mainMenu();
            if (value > 0)
                switcherMainMenu(value);
            else
                end = value;
        }
    }


    private int mainMenu() {
        System.out.println("-------------------------------------\nTo log in choose 1\nTo register new User choose 2\nTo exit program choose 0");
        Tools.stringToIntBlocker(scanner);
        int value = scanner.nextInt();
        scanner.nextLine();
        while (value != 0 && value != 1 && value != 2) {
            System.out.println("Did you try to enter 0, 1 or 2?");
            Tools.stringToIntBlocker(scanner);
            value = scanner.nextInt();
            scanner.nextLine();
        }
        System.out.println("-------------------------------------");
        return value;
    }


    private void switcherMainMenu(int value) {
        switch (value) {
            case 1:
                try {
                    User user = logIntoSystem(scanner, userDataBase);
                    userActionsInLoggedPanel(user);
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 2:
                try {
                    registerNewUser(scanner, userDataBase);
                } catch (IllegalArgumentException | AccessControlException e) {
                    System.out.println(e.getMessage());
                }
                break;

        }
    }


    private User logIntoSystem(Scanner scanner, UserDataBase userDataBase) {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = new User(login, password);

        if (userDataBase.isUserPresentInDataBase(user)) {
            System.out.println("Logged in!");
            return user;
        } else {
            throw new NullPointerException("Login error !");
        }

    }


    private void registerNewUser(Scanner scanner, UserDataBase userDataBase) {
        System.out.print("Create new login: ");
        String login = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (!userDataBase.isLoginAlreadyInUse(login)) {
            userDataBase.addNewUser(new User(login, password));
            System.out.println("Register successful!");
        } else {
            throw new AccessControlException("Login is not available!");
        }
    }

    private void userActionsInLoggedPanel(User user) {
        AuctionDataBase auctionDataBase = new AuctionDataBase();
        boolean end = false;
        while (!end) {
            System.out.printf("-------------------------------------\nPick action(1-5):\n1:Add new auction\n2:Delete auction\n3:Show existing auction\n4:Show my expired auctions\n5:Log Out\nType your pick: ");
            Tools.stringToIntBlocker(scanner);
            int pick = scanner.nextInt();
            scanner.nextLine();
            while (pick > 5 || pick < 1) {
                System.out.println();
                System.out.println("Illegal argument, pick from 1 to 5");
                Tools.stringToIntBlocker(scanner);
                pick = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.println("-------------------------------------");


            switch (pick) {
                case 1: {
                    auctionDataBase.addAuction(user);
                    break;
                }
                case 2: {
                    auctionDataBase.removeAuction(user);
                    break;
                }
                case 3: {
                    auctionDataBase.printAllAuctions();
                    break;
                }
            /*case 4: {
                categoryCont.printAuctionToRemove(user);
                break;
            }*/
                case 5:
                    FileOperations.saveAuctionList(auctionDataBase.getListOfAllAuction());
                    FileOperations.saveUserList(userDataBase.getListOfUsers());
                    end = true;
                default:
            }
        }
    }

}



