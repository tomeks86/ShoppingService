package Controlers;

import Databases.AuctionDataBase;
import Helper.FileOperations;
import models.Auction;
import models.User;
import views.AuctionView;

import java.io.IOException;
import java.math.BigDecimal;

public class BidControler {
    public boolean bidAuction(User buyer, Auction auction, BigDecimal price, AuctionDataBase auctionDataBase) {
        AuctionView auctionView = new AuctionView();
        boolean flag = auction.bidPrice(price);
        auction.setActualWinnerOfAuction(buyer);
        try {
            FileOperations.saveAuctionList(auctionDataBase.getListOfAllAuctions(), "Auction.bin");
        }catch (IOException e ){
            System.out.println(auctionView.showComunicatWhenFileNotSaved());
        }
        return flag;
    }

    public void messageWhenNoSuchAuctionToBid() {
        AuctionView auctionView = new AuctionView();
        System.out.println(auctionView.printErrorWhenWrongAuctionChosenToBid());
    }
}
