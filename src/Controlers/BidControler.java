package Controlers;

import Databases.AuctionDataBase;
import Helper.FileOperations;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import models.Auction;
import models.User;
import views.AuctionView;

import java.io.IOException;

public class BidControler {
    public boolean bidAuction(User buyer, Auction auction, Double price, AuctionDataBase auctionDataBase) {
        AuctionView auctionView = new AuctionView();
        auction.bidPrice(price);
        auction.setActualWinnerOfAuction(buyer);
        try {
            FileOperations.saveAuctionList(auctionDataBase.getListOfAllAuction(), "Auction.bin");
            return true;
        }catch (IOException e ){
         auctionView.showComunicatWhenFileNotSaved();
        }
        return false;
    }
}
