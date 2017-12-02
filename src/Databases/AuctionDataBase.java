package Databases;

import Helper.FileOperations;

import models.Auction;
import models.Category;
import models.User;


import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
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
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO auctions(categoryid,title,description,price,ownerid,isactive,bidcounter) VALUES (?,?,?,?,?,true,0)");
            preparedStatement.setInt(1, auction.getCategoryId());
            preparedStatement.setString(2, auction.getTitle());
            preparedStatement.setString(3, auction.getDescription());
            preparedStatement.setBigDecimal(4, auction.getPrice());
            preparedStatement.setInt(5, auction.getUser().getUserId(connection));
            preparedStatement.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean removeAuction(Connection connection, int idAuctionToRemove) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(" UPDATE auctions SET isactive = false WHERE id = ? ");
            preparedStatement.setInt(1, idAuctionToRemove);
            preparedStatement.executeUpdate();
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
                int auctionid = rs.getInt("id");
                int categoryid = rs.getInt("categoryid");
                int ownerid = rs.getInt("ownerid");
                boolean isActive = rs.getBoolean("isactive");
                int buyerId = rs.getInt("buyerid");
                int bidCounter = rs.getInt("bidcounter");
                allAuctions.add(new Auction(description, title, price, auctionid, categoryid, ownerid, isActive, buyerId, bidCounter));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAuctions;
    }

    public ArrayList<Auction> getListOfUserAuctions(User user, Connection connection) {
        ArrayList<Auction> listOfUsersAuctions = new ArrayList<>();
        try {

            ResultSet rs;
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM auctions WHERE ownerid = ?");
            preparedStatement.setInt(1, user.getUserId(connection));
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String description = rs.getString("description");
                String title = rs.getString("title");
                BigDecimal price = rs.getBigDecimal("price");
                int auctionid = rs.getInt("id");
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
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE auctions SET buyerid = ?,price =?,bidcounter =?,isactive =? WHERE id = ? ");
            preparedStatement.setInt(1, auction.getBuyerId());
            preparedStatement.setBigDecimal(2, auction.getPrice());
            preparedStatement.setInt(3, auction.getBidCounter());
            preparedStatement.setBoolean(4, auction.isActive());
            preparedStatement.setInt(5, auction.getAuctionIndex());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
