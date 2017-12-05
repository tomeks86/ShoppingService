package Controllers;

import models.Auction;
import org.postgresql.util.PSQLException;
import views.AuctionView;
import views.Starter;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AuctionController {
    public AuctionView auctionView = new AuctionView();
    private CategoryController categoryController  = new CategoryController();
    private Auction auction;

    public Auction getAuction(Integer auctionID) {
        return auction;
    }

    public Auction prepareAuction(int userID) {
        int categoryID = -1;
        String title = "", descritpion;
        BigDecimal price = BigDecimal.ZERO;
        System.out.println("Provide category id:");
        while (categoryID == -1) {
            try {
                categoryID = Integer.parseInt(Starter.getScanner().nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Provide correct number!");
            }
            if (!categoryController.getCategoriesIDtoAdd().contains(categoryID)) {
                System.out.println("Invalid id category! Chose from between" + categoryController.getCategoriesIDtoAdd());
                categoryID = -1;
            }
        }
        while (title.length() == 0) {
            System.out.println("Provide non-empty auction title:");
            title = Starter.getScanner().nextLine();
        }
        System.out.println("Provide auction description:");
        descritpion = Starter.getScanner().nextLine();
        while (price.equals(BigDecimal.ZERO)) {
            System.out.println("Provide item price:");
            price = new BigDecimal(Starter.getScanner().nextLine());
        }
        this.auction = new Auction(null, userID, null, categoryID, 0, title, descritpion, price, true);
        return this.auction;
    }

    public Integer dumpAuction(Auction auction) {
        Integer auctionID = null;
        if (auction != null) {
            try {
                PreparedStatement auctionAdd = Starter.getConnection().prepareStatement("INSERT INTO auctions (title, description, price, ownerid, categoryid) VALUES (?, ?, ?, ?, ?)");
                auctionAdd.setString(1, auction.getTitle());
                auctionAdd.setString(2, auction.getDescritpion());
                auctionAdd.setBigDecimal(3, auction.getPrice());
                auctionAdd.setInt(4, auction.getOwnerID());
                auctionAdd.setInt(5, auction.getCategoryID());
                auctionAdd.executeUpdate();
                PreparedStatement getAuctionID = Starter.getConnection().prepareStatement("SELECT * FROM auctions WHERE ownerid = ? ORDER BY id DESC LIMIT 1");
                getAuctionID.setInt(1, auction.getOwnerID());
                ResultSet rs = getAuctionID.executeQuery();
                if (rs.next()) {
                    auctionID = rs.getInt("id");
                }
            } catch (PSQLException | NumberFormatException e) {
                System.out.printf("Auction insertion failed!");
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        return auctionID;
    }

    public void showAuction(Integer auctionID) {
        if (this.auction != null) this.auction = null;
        try {
            PreparedStatement getAuction = Starter.getConnection().prepareStatement("SELECT id, title, description, price::numeric, ownerid, categoryid, isactive, winnerid, bidcounter FROM auctions WHERE id = ?");
            getAuction.setInt(1, auctionID);
            ResultSet rs = getAuction.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                BigDecimal price = new BigDecimal(rs.getString("price"));
                Integer ownerID = rs.getInt("ownerid");
                Integer categoryID = rs.getInt("categoryid");
                boolean isactive = rs.getBoolean("isactive");
                Integer winnerID = rs.getInt("winnerid");
                Integer bidCounter = rs.getInt("bidcounter");

                this.auction = new Auction(auctionID, ownerID, winnerID, categoryID, bidCounter, title, description, price, isactive);
            }
            auctionView.presentAuction(auction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> showUserActiveAuctions(int ownerID) {
        ArrayList<Integer> auctionsID = new ArrayList();
        try {
            PreparedStatement getAuction = Starter.getConnection().prepareStatement("SELECT id, title, description, price::numeric, ownerid, categoryid, isactive, winnerid, bidcounter FROM auctions WHERE ownerid = ? AND isactive");
            getAuction.setInt(1, ownerID);
            ResultSet rs = getAuction.executeQuery();
            while (rs.next()) {
                Integer auctionID = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                BigDecimal price = new BigDecimal(rs.getString("price"));
                Integer categoryID = rs.getInt("categoryid");
                boolean isactive = rs.getBoolean("isactive");
                Integer winnerID = rs.getInt("winnerid");
                Integer bidCounter = rs.getInt("bidcounter");

                this.auction = new Auction(auctionID, ownerID, winnerID, categoryID, bidCounter, title, description, price, isactive);
                auctionsID.add(this.auction.getAuctionID());
                auctionView.presentAuctionLite(auction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (auctionsID.size() != 0) {
            System.out.println("ID of user's auctions:");
            auctionsID.forEach(id -> System.out.print(id + " "));
            System.out.println();
        } else System.out.println("You don't have any auctions.");
        return auctionsID;
    }

    public void showUserInactiveAuctions(int ownerID) {
        try {
            PreparedStatement getAuction = Starter.getConnection().prepareStatement("SELECT id, title, description, price::numeric, ownerid, categoryid, isactive, winnerid, bidcounter FROM auctions WHERE ownerid = ? AND NOT isactive");
            getAuction.setInt(1, ownerID);
            ResultSet rs = getAuction.executeQuery();
            while (rs.next()) {
                Integer auctionID = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                Integer categoryID = rs.getInt("categoryid");
                Integer winnerID = rs.getInt("winnerid");
                Integer bidCounter = rs.getInt("bidcounter");

                this.auction = new Auction(auctionID, ownerID, winnerID, categoryID, bidCounter, title, description, price, false);
                if (bidCounter == 3) {
                    auctionView.presentSoldItem(auction);
                } else {
                    auctionView.presentCancelledAuction(auction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeAuction(int auctionID) {
        try {
            PreparedStatement delAuction = Starter.getConnection().prepareStatement("UPDATE auctions SET isactive = false WHERE id = ?");
            delAuction.setInt(1, auctionID);
            delAuction.executeUpdate();
        } catch (PSQLException e) {
            System.out.println("Remove failed for some reason...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> notUsersAuctions(int userID) {
        ArrayList<Integer> auctionsID = new ArrayList<>();
        try {
            PreparedStatement notUsersAuctionsQuery = Starter.getConnection().prepareStatement("SELECT id, title, description, price::numeric, ownerid, categoryid, isactive, winnerid, bidcounter FROM auctions WHERE isactive AND ownerid != ?");
            notUsersAuctionsQuery.setInt(1, userID);
            ResultSet rs = notUsersAuctionsQuery.executeQuery();
            while (rs.next()) {
                Integer auctionID = rs.getInt("id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                BigDecimal price = rs.getBigDecimal("price");
                Integer ownerID = rs.getInt("ownerid");
                Integer categoryID = rs.getInt("categoryid");
                boolean isactive = rs.getBoolean("isactive");
                Integer winnerID = rs.getInt("winnerid");
                Integer bidCounter = rs.getInt("bidcounter");

                this.auction = new Auction(auctionID, ownerID, winnerID, categoryID, bidCounter, title, description, price, isactive);
                auctionView.presentAuctionLite(auction);
                auctionsID.add(this.auction.getAuctionID());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (auctionsID.size() != 0) {
            System.out.println("ID of auctions to bid:");
            auctionsID.forEach(id -> System.out.print(id + " "));
            System.out.println();
        } else System.out.println("You cannot bid any auctions.");
        return auctionsID;
    }

    public Auction getAuctionByID(int auctionID) {
        if (this.auction != null) this.auction = null;
        try {
            PreparedStatement auctionByIDQuery = Starter.getConnection().prepareStatement("SELECT id, title, description, price::numeric, ownerid, categoryid, isactive, winnerid, bidcounter FROM auctions WHERE isactive AND id = ?");
            auctionByIDQuery.setInt(1, auctionID);
            ResultSet rs = auctionByIDQuery.executeQuery();
            if (rs.next()) {
                String title = rs.getString("title");
                String description = rs.getString("description");
                BigDecimal price = new BigDecimal(rs.getString("price"));
                Integer ownerID = rs.getInt("ownerid");
                Integer categoryID = rs.getInt("categoryid");
                boolean isactive = rs.getBoolean("isactive");
                Integer winnerID = rs.getInt("winnerid");
                Integer bidCounter = rs.getInt("bidcounter");

                this.auction = new Auction(auctionID, ownerID, winnerID, categoryID, bidCounter, title, description, price, isactive);
            }
            auctionView.presentAuction(auction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this.auction;
    }

    public boolean updateAuction(Auction auction, int userID) {
        try {
            if (auction.getBidCounter()  == 2) {
                PreparedStatement auctionUpdateQuery = Starter.getConnection().prepareStatement("UPDATE auctions SET price = ?::numeric, winnerID = ?, bidcounter = bidcounter + 1, isactive = FALSE WHERE ID = ?");
                auctionUpdateQuery.setString(1, auction.getPrice().toString());
                auctionUpdateQuery.setInt(2, userID);
                auctionUpdateQuery.setInt(3, auction.getAuctionID());
                int n = auctionUpdateQuery.executeUpdate();
                if (n > 0) {
                    System.out.println("Congratulations, you won the auction!");
                    return true;
                } else return false;
            } else {
                PreparedStatement auctionUpdateQuery = Starter.getConnection().prepareStatement("UPDATE auctions SET price = ?::numeric, winnerID = ?, bidcounter = bidcounter + 1 WHERE ID = ?");
                auctionUpdateQuery.setString(1, auction.getPrice().toString());
                auctionUpdateQuery.setInt(2, userID);
                auctionUpdateQuery.setInt(3, auction.getAuctionID());
                int n = auctionUpdateQuery.executeUpdate();
                if (n > 0) {
                    System.out.println("You are the current winner!");
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
