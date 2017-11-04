package views;

import Controlers.AuctionDataBase;
import models.Auction;
import models.User;

import java.util.ArrayList;

public class AuctionView {


    public static void printUserAuctions(ArrayList<Auction> listOfuserAuctions, User user) {
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


}
