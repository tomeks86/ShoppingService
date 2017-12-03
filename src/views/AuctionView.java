package views;

import models.Auction;

import java.io.Serializable;
import java.util.ArrayList;

public class AuctionView implements Serializable {
    public void presentAuction(Auction auction) {
        System.out.println(auction.toString());
    }
}
