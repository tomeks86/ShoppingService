package Controlers;

import Databases.AuctionDataBase;
import Helper.FileOperations;
import models.Auction;
import models.User;
import views.AuctionView;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;

public class BidControler {
    public boolean bidAuction(User buyer, Auction auction, BigDecimal price, Connection connection, AuctionDataBase auctionDataBase) {
        boolean flag = auction.bidPrice(price);
        auction.setActualWinnerOfAuction(buyer, connection);
        auctionDataBase.updateWinnerOfAuction(connection, auction);
        return flag;
    }

    public void messageWhenNoSuchAuctionToBid() {
        AuctionView auctionView = new AuctionView();
        System.out.println(auctionView.printErrorWhenWrongAuctionChosenToBid());
    }
}
