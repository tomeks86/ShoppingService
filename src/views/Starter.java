package views;

import Controlers.BidControler;
import Controlers.UsersControler;
import Databases.AuctionDataBase;
import interfaceWithUsers.AuctionInterface;
import interfaceWithUsers.BidInterface;
import models.Auction;
import models.User;
import Controlers.AuctionControler;
import Helper.Blockers;
import models.UserDataBase;

import java.security.AccessControlException;
import java.util.Scanner;

public class Starter {
    private Scanner scanner = new Scanner(System.in);
    private UsersControler usersControler = new UsersControler();
    private UserDataBase userDataBase = new UserDataBase();
    private AuctionDataBase auctionDataBase = new AuctionDataBase();

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
        Blockers.stringToIntBlocker(scanner);
        int value = scanner.nextInt();
        scanner.nextLine();
        while (value != 0 && value != 1 && value != 2) {
            System.out.println("Did you try to enter 0, 1 or 2?");
            Blockers.stringToIntBlocker(scanner);
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
                    User user = logIntoSystem(scanner);
                    userActionsInLoggedPanel(user);
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 2:
                try {
                    registerNewUser(scanner);
                } catch (IllegalArgumentException | AccessControlException e) {
                    System.out.println(e.getMessage());
                }
                break;

        }
    }


    private User logIntoSystem(Scanner scanner) {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = new User(login, password);

        if (usersControler.isUserPresentInDataBase(user)) {
            System.out.println("Logged in!");
            return new User(login, password);
        } else {
            throw new NullPointerException("Login error !");
        }

    }


    private void registerNewUser(Scanner scanner) {
        System.out.print("Create new login: ");
        String login = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = new User(login, password);
        if (usersControler.addNewUser(user)) {
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
            Blockers.stringToIntBlocker(scanner);
            int pick = scanner.nextInt();
            scanner.nextLine();
            while (pick > 5 || pick < 1) {
                System.out.println();
                System.out.println("Illegal argument, pick from 1 to 5");
                Blockers.stringToIntBlocker(scanner);
                pick = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.println("-------------------------------------");


            switch (pick) {
                case 1: {
                    AuctionControler auctionControler = new AuctionControler();
                    AuctionInterface auctionInterface = new AuctionInterface();
                    Auction auction = auctionInterface.createAuctionToAdd(user, auctionControler, auctionInterface, auctionDataBase);
                    auctionControler.addAuction(auctionDataBase, auction);
                    break;
                }
                case 2: {
                    AuctionControler auctionControler = new AuctionControler();
                    AuctionInterface auctionInterface = new AuctionInterface();
                    AuctionView auctionView = new AuctionView();
                    try {
                        Auction auction = auctionInterface.searchIdOfAuctionToRemove(user, auctionDataBase, auctionControler);
                        auctionControler.removeAuction(auctionDataBase, auction, user);
                    }catch (NullPointerException e){
                        auctionView.showComunicatWhenAuctionNotRemoved();
                    }
                    break;
                }
                case 3: {
                    AuctionControler auctionControler = new AuctionControler();
                    BidInterface bidInterface = new BidInterface();
                    BidControler bidControler = new BidControler();
                    auctionControler.getAuctions(auctionDataBase);

                    if (bidInterface.shouldContinueWithBid(scanner)) {
                        Auction auction = bidInterface.returnAuction(scanner, auctionDataBase,user,auctionControler);
                        Double price = bidInterface.returnPrice(scanner, auctionDataBase);
                        bidControler.bidAuction(user, auction, price);
                    }
                    break;
                }
            /*case 4: {
                categoryCont.printAuctionToRemove(user);
                break;
            }*/
                case 5:
                    end = true;
                default:
            }
        }
    }

}



