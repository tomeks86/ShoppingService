package models;

import Databases.AuctionDataBase;
import views.AuctionView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Auction implements Serializable {

    private Integer categoryId, auctionIndex;
    private String description, title;
    private Double price;
    private User user;
    private boolean isActive;
    AuctionView auctionView = new AuctionView();

    public Auction() {
    }

    public String getDescription() {

        return description;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getCategoryId() {

        return categoryId;
    }

    public Integer getAuctionIndex() {
        return auctionIndex;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public User getUser() {
        return user;
    }

    public boolean isActive() {
        return isActive;
    }

    public Auction(String description, String title, Double price, User user, Integer auctionIndex, Integer categoryId) {
        this.description = description;
        this.title = title;
        this.price = price;
        this.user = user;
        this.auctionIndex = auctionIndex;
        this.isActive = true;
        this.categoryId = categoryId;
    }

    public boolean isCategoryValid(HashSet<Integer> categorySet, Integer idCategory) {
        if (categorySet.contains(idCategory))
            return true;
        else {
            auctionView.printsErrorWhenWrongCategoryChosen();
            return false;
        }

    }



    public void bidPrice(Double price) {
        setPrice(price);
    }

    private void setPrice(Double price) {
        this.price = price;
    }
}
