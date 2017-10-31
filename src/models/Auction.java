package models;

public class Auction {

    private Integer categoryId, auctionIndex;
    private String description, title;
    private Double price;
    private User user;
    private boolean isActive;

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

    public Auction(String description, String title, Double price, User user, Integer auctionIndex) {
        this.description = description;
        this.title = title;
        this.price = price;
        this.user = user;
        this.auctionIndex = auctionIndex;
        this.isActive = true;
    }

    public void setCategory(Integer category) {
        this.categoryId = category;       // To tak na wypadek jakbysmy chcieli ustawiac kategorie dla aukcji

    }
}
