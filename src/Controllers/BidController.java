package Controllers;

import views.AuctionView;
import views.Starter;

import java.util.ArrayList;

public class BidController {
    AuctionView auctionView = new AuctionView();

    public boolean toBidOrNot(ArrayList<Integer> auctionsID) {
        System.out.println("Would you like to make a bid? (y/n)");
        String response = null;
        while (!(response = Starter.getScanner().nextLine()).matches("^[yYnN]$")) {
            System.out.println("Try again: y/n");
        }
        if (response.matches("^[yY]$")) return true;
        else return false;
    }

    public int getAucitionID(ArrayList<Integer> auctionsID) {
        int auctionID = -1;
        System.out.println("Chose auction id from:");
        auctionsID.forEach(id -> System.out.print(id + " "));
        System.out.println();
        System.out.println("Chose auction id:");
        try {
            auctionID = Integer.parseInt(Starter.getScanner().nextLine());
        } catch (NumberFormatException e) {
            System.out.printf("Wrong auction id!");
        }
        return auctionID;
    }
}
