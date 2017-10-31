package models;

public class Auction {

    private Integer categoryId;
    private String description, title;
    private Double price;
    private User user;
    private boolean isActive;

    public Auction(String description, String title, Double price, User user) {
        this.description = description;
        this.title = title;
        this.price = price;
        this.user = user;
        this.isActive = true;
    } // Aukcja wie jakiego ma usera - chyba tak to chcielismy zrobic nie ?

    public void setCategory(Integer category) {
        this.categoryId = category;       // To tak na wypadek jakbysmy chcieli ustawiac kategorie dla aukcji

    }
}
