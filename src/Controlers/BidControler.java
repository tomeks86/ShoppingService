package Controlers;

import Databases.AuctionDataBase;
import models.Auction;

public class BidControler {
    public void bidAuction(Auction auction, Double price) {
        auction.bidPrice(price);
    }
}
