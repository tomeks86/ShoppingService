package views;

import models.Auction;
import models.User;

import java.io.Serializable;
import java.util.ArrayList;

public class AuctionView implements Serializable {


    public void printUserAuctions(ArrayList<Auction> listOfuserAuctions, User user) {
        System.out.println("Active " + user.getUserName() + " auctions: \n--");
        for (Auction auction : listOfuserAuctions) {

            System.out.println("Index : " + auction.getAuctionIndex() + "\ttitle: " + auction.getTitle());
            //System.out.println((auction.getBuyer() == null) ? "\nNo bids" : "\nActual highest bid by: " + auction.getBuyer().getUserName());
            System.out.println("-----");

        }
    }

    public static void printAllAuctions(ArrayList<Auction> allAuctions) {

        for (Auction auction : allAuctions) {
            if (auction.isActive()) {
                System.out.println("ID: " + auction.getAuctionIndex() + "\nTytle: " + auction.getTitle() + "\nDescription : " + auction.getDescription() + "\nPrice : " + auction.getPrice());
                //System.out.println((auction.getBuyer() == null) ? "\nNo bids" : "\nActual highest bid by: " + auction.getBuyer().getUserName());
                System.out.println("----------------------");
            }
        }
    }

    String printTooLowOffer(Auction auction) {
        return "Your offer was too low, you need to give more than " + auction.getPrice();

    }

    public String printsErrorWhenWrongCategoryChosen() {
        return "There is no such category ! ";
    }

    public String showComunicatWhenAuctionAdded() {

        return "Auction added! ";
    }

    public String showComunicatWhenAuctionNotAdded() {
        return "Problem occurred while adding auction, auction not added";
    }

    public String showComunicatWhenAuctionRemoved() {

        return "Auction removed";
    }

    public String showComunicatWhenAuctionNotRemoved() {
        return "Problems occurred, auction not removed! Try again. ";
    }


    public String showComunicatWhenFileNotSaved() {

        return "Cannot save file !";
    }

    String printCongratulationMessage(Auction auction, User user) {
        return ("Congratulations " + user.getUserName() + "! You've just bouhgt: " + auction.toString());
    }

    String printCurrentOffer(Auction auction) {
        return "You've made a bid, the current for: " + auction.toString();

    }

    String printUserExpiredAuctions(ArrayList<Auction> expiredAuctions) {
        StringBuilder sb = new StringBuilder();
        for (Auction auction : expiredAuctions) {
            if (auction.getBidCounter() < 3) {
                sb.append(auction.toString() + ": (removed by user)");
            } else {
                sb.append(auction.toString() + ": bought " );//by user: " + auction.getBuyer().getUserName());
            }
            if (!(expiredAuctions.indexOf(auction) == expiredAuctions.size() - 1))
                sb.append("\n");
        }
        return sb.toString();
    }

    public String printErrorWhenWrongAuctionChosenToBid() {
        return "There is no such auction, bid impossible, you are owner of auction or already have highest bid";
    }
}
