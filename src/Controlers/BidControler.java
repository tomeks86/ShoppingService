package Controlers;

import Databases.AuctionDataBase;
import Helper.FileOperations;
import models.Auction;
import models.User;
import views.AuctionView;

import java.io.IOException;

public class BidControler {
    public boolean bidAuction(User buyer, Auction auction, Double price, AuctionDataBase auctionDataBase) {
        AuctionView auctionView = new AuctionView();
        boolean flag = auction.bidPrice(price);
        auction.setActualWinnerOfAuction(buyer);
        try {
            FileOperations.saveAuctionList(auctionDataBase.getListOfAllAuction(), "Auction.bin");
        }catch (IOException e ){
         auctionView.showComunicatWhenFileNotSaved();
        }
        return flag;
    }

    public void messageWhenNoSuchAuctionToBid() {
        AuctionView auctionView = new AuctionView();
        auctionView.printErrorWhenWrongAuctionChosenToBid();
    }
}
