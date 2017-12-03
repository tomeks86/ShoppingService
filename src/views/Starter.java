package views;

import Controllers.AuctionController;
import Controllers.BidController;
import Controllers.CategoryController;
import models.Auction;
import org.postgresql.util.PSQLException;

import java.security.AccessControlException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Starter {
    private static Scanner scanner;
    private static Connection connection;

    public Starter() {
        run();
    }

    private void run() {

        connection = getConnection();
        scanner = getScanner();

        Integer end = -1;
        while (end != 0) {
            int value = mainMenu();
            if (value > 0)
                switcherMainMenu(value);
            else
                end = value;
        }
    }

    public static Scanner getScanner() {
        if (scanner == null) scanner = new Scanner(System.in);
        return scanner;
    }

    public static void closeScanner() {
        if (scanner != null) scanner.close();
    }

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL JDBC Driver? "
                    + "Include in your library path!");
            e.printStackTrace();
            //return;
        }

        System.out.println("PostgreSQL JDBC Driver Registered!");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/zenek", "zenek",
                    "zenek");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            //return;
        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }

        return connection;
    }

    public static void closeConnection() {
        if (connection == null) {
            return;
        }

        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private int mainMenu() {
        System.out.println("-------------------------------------\nTo log in choose 1\nTo register new User choose 2\nTo exit program choose 0");
        int value = -1;
        while (value == -1) {
            try {
                value = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("incorrect number, try again!");
            }
            if (value != 0 && value != 1 && value != 2) {
                System.out.println("Try again with options: 0, 1 or 2");
                value = -1;
            }
        }
        System.out.println("-------------------------------------");
        return value;
    }


    private void switcherMainMenu(int value) {
        switch (value) {
            case 1:
                int userID = logIntoSystem();
                userActionsInLoggedPanel(userID);
                break;
            case 2:
                try {
                    registerNewUser();
                } catch (IllegalArgumentException | AccessControlException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 0:
                System.out.println("Bye!");
                closeScanner();
                closeConnection();
                System.exit(0);
                break;
        }
    }


    private int logIntoSystem() {
        PreparedStatement getPassHash = null;
        ResultSet rs = null;
        int id = 0;
        String getPassHashString = "SELECT id FROM users WHERE login = ? AND password = crypt(?, salt)";

        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        try {
            getPassHash = connection.prepareStatement(getPassHashString);
            getPassHash.setString(1, login);
            getPassHash.setString(2, password);
            rs = getPassHash.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            } else {
                throw new NullPointerException("Login error !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }


    private void registerNewUser() {
        PreparedStatement userPassInsert = null;
        ResultSet rs = null;
        String salt = null;
        String userPassInsertString = "INSERT INTO users (login, password, salt) VALUES (?, crypt(?, ?), ?)";

        System.out.print("Create new login: ");
        String login = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        try {
            rs = connection.createStatement().executeQuery("SELECT gen_salt('bf')");
            if (rs.next()) {
                salt = rs.getString("gen_salt");
                userPassInsert = connection.prepareStatement(userPassInsertString);
                userPassInsert.setString(1, login);
                userPassInsert.setString(2, password);
                userPassInsert.setString(3, salt);
                userPassInsert.setString(4, salt);
                if (userPassInsert.executeUpdate() > 0) return;
                else throw new AccessControlException("User registration failed!");
            }
        } catch (PSQLException e) {
            throw new AccessControlException("User registration failed! (login incorrect or already exists)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void userActionsInLoggedPanel(int userID) {
        boolean end = false;
        while (!end) {
            Integer pick = -1;
            Integer[] picks = new Integer[]{1, 2, 3, 4, 5};
            while (pick == -1) {
                System.out.printf("-------------------------------------\nPick action(1-5):\n1:Add new auction\n2:Delete auction\n3:Show existing auctions\n4:Show my expired auctions\n5:Log Out\nType your pick: ");
                try {
                    pick = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("wrong input!");
                }
                if (!Arrays.asList(picks).contains(pick)) {
                    System.out.println("chose from: 1 - 5");
                    pick = -1;
                }
            }

            CategoryController categoryController = new CategoryController();
            AuctionController auctionController = new AuctionController();
            BidController bidController = new BidController();
            Integer auctionID;

            switch (pick) {
                case 1:
                    categoryController.showAllCategories();
                    Auction auction = auctionController.prepareAuction(userID);
                    auctionID = auctionController.dumpAuction(auction);
                    if (auctionID != null) {
                        auction = auctionController.getAuction(auctionID);
                        System.out.println("Auction added: ");
                        auctionController.showAuction(auctionID);
                    }
                    break;
                case 2:
                    ArrayList<Integer> usersAuctionsIDs = auctionController.showUserActiveAuctions(userID);
                    auctionID = -1;
                    boolean removed = false;
                    if (usersAuctionsIDs.size() != 0) {
                        System.out.println("Select auction to remove:");
                        try {
                            auctionID = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.printf("Wrong number passed!");
                        }
                        if (auctionID > -1) {
                            auctionController.removeAuction(auctionID);
                        }
                    }
                    break;
                case 3: {
                    categoryController.showAllCategories();
                    ArrayList<Integer> auctionsID = auctionController.notUsersAuctions(userID);
                    boolean toBid = bidController.toBidOrNot(auctionsID);
                    if (toBid) {
                        //try {
                            auctionID = bidController.getAucitionID(auctionsID);
                            if (auctionID > -1) {
                                int winnerID = auctionController.getAuctionByID(auctionID);
                                if (winnerID != userID) {

                                } else {
                                    System.out.println("You are the current winner, bid blocked.");
                                }
                            }
                        //}
                    }
                }
                break;
                case 4:
                    /*AuctionControler auctionControler = new AuctionControler();
                    AuctionView auctionView = new AuctionView();
                    ArrayList<Auction> expiredAuctions = auctionControler.getUserExpiredAuctions(user, auctionDataBase, this.connection);
                    System.out.println(auctionView.printUserExpiredAuctions(expiredAuctions));*/
                    break;
                case 5:
                    end = true;
                default:
            }
        }
    }
}