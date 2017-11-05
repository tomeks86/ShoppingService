package views;

import models.Auction;
import models.Category;
import models.User;

import java.io.Serializable;
import java.util.ArrayList;

public class AuctionView implements Serializable{


    public void printUserAuctions(ArrayList<Auction> listOfuserAuctions, User user) {
        System.out.println("Active " + user.getUserName() + " auctions: \n--");
        for (Auction auction : listOfuserAuctions) {
            if (auction.getUser().getUserName().equals(user.getUserName())
                    && auction.getUser().getPassword().equals(user.getPassword())
                    && auction.isActive()) {
                System.out.println("Index : " + auction.getAuctionIndex() + "\ttitle: " + auction.getTitle());
                System.out.println("-----");
            }
        }
    }

    public void printAllAuctions(ArrayList<Auction> listofAllAuction) {
        Category category = new Category();
        CategoryView categoryView = new CategoryView();
        categoryView.viewAllCategories(category.mainCategory, "");
        for (Auction auction : listofAllAuction) {
            if (auction.isActive()) {
                System.out.println("ID: " + auction.getAuctionIndex() + "\nTytle: " + auction.getTitle() + "\nDescription : " + auction.getDescription() + "\nPrice : " + auction.getPrice());
                System.out.println("----------------------");
            }
        }
    }

    public void printTooLowOffer(Auction auction) {
        System.out.println("Your offer was too low, you need to give more than " + auction.getPrice());
    }

    public void printsErrorWhenWrongCategoryChosen() {
        System.out.println("There is no such category ! ");
    }

    public void showComunicatWhenAuctionAdded() {
        System.out.println("Auction added! ");
    }

    public void printingMessages(String text) {
        System.out.println(text);
    }

    public void showComunicatWhenAuctionNotAdded() {
        System.out.println("Problem occurred while adding auction, auction not added");
    }

    public void showComunicatWhenAuctionRemoved() {
        System.out.println("Auction removed");
    }

    public void showComunicatWhenAuctionNotRemoved() {
        System.out.println("Problems occurred, auction not removed! Try again. ");
    }

    public void printCongratulationMessage(Auction auction, User user) {
        System.out.println("Congratulations " + user.getUserName() + "! You've just bouhgt: " + auction.toString());
    }

    public void printCurrentOffer(Auction auction) {
        System.out.println("You've made a bid, the current for: " + auction.toString());
    }
}
