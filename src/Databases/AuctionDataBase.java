package Databases;

import Helper.FileOperations;

import models.Auction;
import models.Category;
import models.User;


import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AuctionDataBase implements Serializable {

    private int indexAuction = 1;
    private ArrayList<Auction> listOfAllAuctions;

    // NA CHWILE
    public ArrayList<Auction> getListOfAllAuctions() {
        return listOfAllAuctions;
    }

    public int getIndexAuction() {
        if (!listOfAllAuctions.isEmpty())
            return (listOfAllAuctions.get(listOfAllAuctions.size() - 1).getAuctionIndex() + 1);
        else
            return indexAuction;
    }

    public AuctionDataBase() {
        listOfAllAuctions = FileOperations.loadAuctionList("Auction.bin");
    }


    public boolean addAuction(Auction auction, Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO auctions(categoryid,title,description,price,ownerid,isactiv,bidcounter) VALUES (" + auction.getCategoryId() + ",'" + auction.getTitle() + "','" + auction.getDescription() + "'," +
                    "" + auction.getPrice() + "," + auction.getUser().getUserId(connection) + ",true,0)");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean removeAuction(Connection connection, int idAuctionToRemove) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM auctions WHERE auctionid = " + idAuctionToRemove);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }


    public ArrayList<Auction> filterListToCategory(Integer categoryId, Connection connection) {
        Category category = new Category();
        ArrayList<Integer> ids = category.getSubCategoriesIds(categoryId);
        return getListOfAllAuctions(connection).stream()
                .filter(a -> ids.contains(a.getCategoryId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Auction> getListOfAllAuctions(Connection connection) {
        ArrayList<Auction> allAuctions = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery("SELECT * FROM auctions");
            while (rs.next()) {
                String description = rs.getString("description");
                String title = rs.getString("title");
                BigDecimal price = rs.getBigDecimal("price");
                int auctionid = rs.getInt("auctionid");
                int categoryid = rs.getInt("categoryid");
                int ownerid = rs.getInt("ownerid");
                boolean isActive = rs.getBoolean("isactiv");
                int buyerId = rs.getInt("buyerid");
                int bidCounter = rs.getInt("bidcounter");
                allAuctions.add(new Auction(description, title, price, auctionid, categoryid, ownerid, isActive,buyerId,bidCounter));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAuctions;
    }

    public ArrayList<Auction> getListOfUserAuctions(User user, Connection connection) {
        ArrayList<Auction> listOfUsersAuctions = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs;
            rs = statement.executeQuery("SELECT * FROM auctions WHERE ownerid =" + user.getUserId(connection));
            while (rs.next()) {
                String description = rs.getString("description");
                String title = rs.getString("title");
                BigDecimal price = rs.getBigDecimal("price");
                int auctionid = rs.getInt("auctionid");
                int categoryid = rs.getInt("categoryid");
                listOfUsersAuctions.add(new Auction(description, title, price, user, auctionid, categoryid));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listOfUsersAuctions;
    }

    public void updateWinnerOfAuction(Connection connection, Auction auction) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE auctions SET buyerid ="+auction.getBuyerId() +" WHERE auctionid =" +auction.getAuctionIndex());
            statement.executeUpdate("UPDATE auctions SET price ="+auction.getPrice() +" WHERE auctionid =" +auction.getAuctionIndex());
            statement.executeUpdate("UPDATE auctions SET bidcounter ="+auction.getBidCounter() +" WHERE auctionid =" +auction.getAuctionIndex());
            statement.executeUpdate("UPDATE auctions SET isactiv ="+auction.isActive() +" WHERE auctionid =" +auction.getAuctionIndex());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
