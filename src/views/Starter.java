package views;

import Controlers.AuctionControler;
import Controlers.BidControler;
import Controlers.UsersControler;
import Databases.AuctionDataBase;
import Helper.Blockers;
import interfaceWithUsers.AuctionInterface;
import interfaceWithUsers.BidInterface;
import models.Auction;
import models.User;

import java.math.BigDecimal;
import java.security.AccessControlException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Starter {
    private Scanner scanner = new Scanner(System.in);
    private UsersControler usersControler = new UsersControler();
    private Connection connection;

    public Starter() {
        run();
    }

    private void run() {

        connection = createConnection();

        Integer end = -1;
        while (end != 0) {
            int value = mainMenu();
            if (value > 0)
                switcherMainMenu(value);
            else
                end = value;
        }
    }

    private Connection createConnection() {
        System.out.println("POSTGRESQL CONNECTION TESTING...");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL driver, add dependency to maven !!");
            e.printStackTrace();
        }

        System.out.println("Postgre JDBC Driver Registred!");
        Connection connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/zenek", "zenek", "123");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("You've made it, take control your database now");
        } else
            System.out.println("Failed to make connection with database");

        return connection;
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
                    User user = logIntoSystem(scanner, connection);
                    userActionsInLoggedPanel(user);
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println("Login problems accrued");
                }
                break;

            case 2:
                try {
                    registerNewUser(scanner, connection);
                } catch (IllegalArgumentException | AccessControlException e) {
                    System.out.println(e.getMessage());
                }
                break;

        }
    }


    private User logIntoSystem(Scanner scanner, Connection connection) {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = new User(login, password);

        if (usersControler.isUserPresentInDataBase(user, connection)) {
            System.out.println("Logged in!");
            return new User(login, password);
        } else {
            throw new NullPointerException("Login error !");
        }

    }


    private void registerNewUser(Scanner scanner, Connection connection) {
        System.out.print("Create new login: ");
        String login = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        User user = new User(login, password);
        if (usersControler.addNewUser(user, connection)) {
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
                    auctionControler.addAuction(auctionDataBase, auction, connection);
                    break;
                }
                case 2: {
                    AuctionControler auctionControler = new AuctionControler();
                    AuctionInterface auctionInterface = new AuctionInterface();
                    try {
                        int idAuctionToRemove = auctionInterface.searchIdOfAuctionToRemove(user, auctionDataBase, auctionControler, connection);
                        auctionControler.removeAuction(idAuctionToRemove, connection, auctionDataBase);
                    } catch (NullPointerException e) {
                        auctionControler.getMessageWhenCannotRemoveAuction();
                    }
                    break;
                }
                case 3: {
                    AuctionControler auctionControler = new AuctionControler();
                    AuctionView auctionView = new AuctionView();
                    BidInterface bidInterface = new BidInterface();
                    BidControler bidControler = new BidControler();
                    auctionControler.getAuctions(auctionDataBase, connection);

                    if (bidInterface.shouldContinueWithBid(scanner)) {
                        try {
                            Auction auction = bidInterface.returnAuction(scanner, auctionDataBase, user, auctionControler, connection);
                            BigDecimal price = bidInterface.returnPrice(scanner);
                            try {
                                boolean Sold = bidControler.bidAuction(user, auction, price, connection, auctionDataBase);
                                if (Sold) System.out.println(auctionView.printCongratulationMessage(auction, user));
                                else System.out.println(auctionView.printCurrentOffer(auction));
                            } catch (IllegalStateException e) {
                                System.out.println(auctionView.printTooLowOffer(auction));
                            }
                        } catch (NullPointerException e) {
                            bidControler.messageWhenNoSuchAuctionToBid();
                        }
                    }
                    break;
                }
                case 4: {
                    AuctionControler auctionControler = new AuctionControler();
                    AuctionView auctionView = new AuctionView();
                    ArrayList<Auction> expiredAuctions = auctionControler.getUserExpiredAuctions(user, auctionDataBase, connection);
                    System.out.println(auctionView.printUserExpiredAuctions(expiredAuctions));
                    break;
                }
                case 5:
                    end = true;
                default:
            }
        }
    }

}



