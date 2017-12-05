package models;

import views.AuctionView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashSet;

public class Auction {
    private Integer auctionID, ownerID, winnerID, categoryID, bidCounter;
    private String title, descritpion;
    private BigDecimal price;
    private boolean isactive;

    public Auction(Integer auctionID, Integer ownerID, Integer winnerID, Integer categoryID, Integer bidCounter, String title, String descritpion, BigDecimal price, boolean isactive) {
        this.auctionID = auctionID;
        this.ownerID = ownerID;
        this.winnerID = winnerID;
        this.categoryID = categoryID;
        this.bidCounter = bidCounter;
        this.title = title;
        this.descritpion = descritpion;
        this.price = price;
        this.isactive = isactive;
    }

    public Integer getAuctionID() {
        return auctionID;
    }

    public Integer getOwnerID() {
        return ownerID;
    }

    public Integer getWinnerID() {
        return winnerID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public Integer getBidCounter() {
        return bidCounter;
    }

    public String getTitle() {
        return title;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        //return super.toString();
        return String.format("auction id: %d%n" +
                "owner id: %d%n" +
                "winner id: %d%n" +
                "category id: %d%n" +
                "title: %s%n" +
                "description: %s%n" +
                "price: %s%n" +
                "active: %b%n", this.auctionID, this.ownerID, this.winnerID, this.categoryID, this.title, this.descritpion, this.price.toString(), this.isactive);
    }

    /*public static void main(String[] args) {
        Auction auction = new Auction(1, 3, 4, 4, 0, "dsfds", "dsdsf", new BigDecimal("32.3"), true);
        System.out.println(auction.toString());
    }*/
}
