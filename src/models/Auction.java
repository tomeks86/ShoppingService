package models;

import views.AuctionView;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;

public class Auction implements Serializable {

    private Integer categoryId, auctionIndex;
    private String description, title;
    private BigDecimal price;
    private int bidCounter;
    private User user, buyer;
    private boolean isActive;
    private AuctionView auctionView = new AuctionView();

    public Auction() {
    }

    public String getDescription() {

        return description;
    }

    public int getBidCounter() {
        return bidCounter;
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

    public BigDecimal getPrice() {
        return price;
    }

    public User getUser() {
        return user;
    }

    public boolean isActive() {
        return isActive;
    }

    private void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getBuyer() {
        return buyer;
    }

    public Auction(String description, String title, BigDecimal price, User user, Integer auctionIndex, Integer categoryId) {
        this.description = description;
        this.title = title;
        this.price = price;
        this.bidCounter = 0;
        this.user = user;
        this.auctionIndex = auctionIndex;
        this.isActive = true;
        this.categoryId = categoryId;
    }

    public boolean isCategoryValid(HashSet<Integer> categorySet, Integer idCategory) {
        if (categorySet.contains(idCategory))
            return true;
        else {
            System.out.println(auctionView.printsErrorWhenWrongCategoryChosen());
            return false;
        }

    }

    public void setActualWinnerOfAuction(User buyer) {
        setBuyer(buyer);
    }


    public boolean bidPrice(BigDecimal price) {

        if (price.compareTo(this.getPrice()) == 1) {
            setPrice(price);
            this.bidCounter++;
            if (this.bidCounter == 3) {
                this.isActive = false;
                return true;
            } else return false;

        } else {
            throw new IllegalStateException("Price is too low");
        }
    }


    private void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return this.description + " price: " + this.getPrice();
    }
}
