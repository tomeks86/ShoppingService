package Controlers;

import Databases.AuctionDataBase;
import models.Auction;
import models.User;

public class BidControler {
    public void bidAuction(User buyer, Auction auction, Double price) {
        auction.bidPrice(price);
        auction.setActualWinnerOfAuction(buyer);
    }
}
