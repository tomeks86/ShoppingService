package views;

import models.Auction;
import models.User;

import java.util.ArrayList;

public class AuctionViewer {


    public static void printUserAuctions(ArrayList<Auction> listOfuserAuctions, User user) {
        System.out.println("Active " + user.getUserName() + " auctions: ");
        for (Auction auction : listOfuserAuctions) {
            if (auction.getUser().equals(user) && auction.isActive() == true)
                System.out.println("Index : " + auction.getAuctionIndex() + "\ttitle: " + auction.getTitle());
            System.out.println("----------------------------------");
        }
    }

    public static void printAllAuctions(ArrayList<Auction> listofAllAuction) {
        for (Auction auction : listofAllAuction) {
            if (auction.isActive()) {
                System.out.println("Tytle: " + auction.getTitle() + "\nDescription : " + auction.getDescription() + "\nPrice : " + auction.getPrice());
                System.out.println("----------------------");
            }
        }
    }
}
