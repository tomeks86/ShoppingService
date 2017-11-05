package Controlers;

import Databases.AuctionDataBase;
import models.Auction;

public class BidControler {
    public boolean bidAuction(Auction auction, Double price) {
        return auction.bidPrice(price);
    }
}
