package views;

import models.Auction;

import java.io.Serializable;
import java.math.BigDecimal;

public class AuctionView implements Serializable {
    public void presentAuction(Auction auction) {
        System.out.println(auction.toString());
    }

    public void presentAuctionLite(Auction auction) {
        if (auction.getWinnerID() != null) {
            System.out.printf("id: %d, title: %s, price: %s, winner: %s%n", auction.getAuctionID(), auction.getTitle(), auction.getPrice(), auction.getWinnerID());
        } else {
            System.out.printf("id: %d, title: %s, price: %s, no offers yet%n", auction.getAuctionID(), auction.getTitle(), auction.getPrice());
        }
    }

    public BigDecimal presentOffer(Auction auction, BigDecimal minimum) {
        BigDecimal offer = auction.getPrice().add(minimum);
        System.out.printf("Current price is: %s%n", auction.getPrice());
        System.out.printf("Current minimum offer is: %s%n", offer.toString());
        return offer;
    }

    public void presentSoldItem(Auction auction) {
        System.out.println("The item was bought!");
        presentAuctionLite(auction);
    }

    public void presentCancelledAuction(Auction auction) {
        System.out.println("The auction was cancelled!");
        presentAuctionLite(auction);
    }
}
