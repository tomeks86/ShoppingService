package interfaceWithUsers;

import Controlers.AuctionControler;
import Databases.AuctionDataBase;
import Helper.Blockers;
import Helper.Inputors;
import models.Auction;
import models.User;

import java.util.Scanner;

public class BidInterface {
    public boolean shouldContinueWithBid(Scanner scanner) {
        System.out.println("Would you like to make a bid ? Y/N ");
        String decision = "";
        while (!(decision.toLowerCase().equals("y") || decision.toLowerCase().equals("n"))) {
            decision = scanner.nextLine();
            decision = Blockers.emptyStringBlocker(decision, scanner);
        }
        return decision.equals("y");
    }

    public Auction returnAuction(Scanner scanner, AuctionDataBase auctionDataBase, User user, AuctionControler auctionControler) {

        Integer id = Inputors.creatingInteger(scanner, "Type id of auction you want to bid: ");
        return (auctionControler.checkAccessToBidAuction(auctionDataBase,id,user));
    }

    public Double returnPrice(Scanner scanner) {
        return Inputors.creatingDuble(scanner,"Type amount of your bid: ");
    }
}
