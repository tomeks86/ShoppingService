package views;

import models.Auction;
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

    public static void printAllAuctions(ArrayList<Auction> listofAllAuction) {
        for (Auction auction : listofAllAuction) {
            if (auction.isActive()) {
                System.out.println("ID: " + auction.getAuctionIndex() + "\nTytle: " + auction.getTitle() + "\nDescription : " + auction.getDescription() + "\nPrice : " + auction.getPrice());
                System.out.println("----------------------");
            }
        }

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
}
