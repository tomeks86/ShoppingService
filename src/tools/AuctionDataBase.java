package tools;

import models.Auction;

import java.util.ArrayList;

public class AuctionDataBase {
    private ArrayList<Auction> listOfAllAuction;

    public AuctionDataBase() {
        listOfAllAuction = new ArrayList<>();
    }

    public boolean addAuction() {
        return true;
    }
}
