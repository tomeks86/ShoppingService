package interfaceWithUsers;

import Controlers.AuctionControler;
import Databases.AuctionDataBase;
import Helper.Inputors;
import models.Auction;
import models.User;
import views.AuctionView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class AuctionInterface {
    private Scanner scanner = new Scanner(System.in);




    public Auction createAuctionToAdd(User user, AuctionControler auctionControler, AuctionInterface auctionInterface, AuctionDataBase auctionDataBase) {
        ArrayList<Auction> listOfAllAuction = auctionDataBase.getListOfAllAuction();
        int indexAuction = auctionDataBase.getIndexAuction();

        return (new Auction(Inputors.creatingString(scanner, "Description of auction: "),
                Inputors.creatingString(scanner, "Title :"),
                Inputors.creatingDuble(scanner, "Price: "), user, indexAuction,
                auctionControler.choseCategoryForAddedAuctions(auctionInterface)));
    }


    public Integer choseCategoryId(String text, HashSet<Integer> set) {
        Auction auction = new Auction();
        AuctionInterface auctionInterface = new AuctionInterface();
        int idCategory = Inputors.creatingInteger(scanner, text);

        while (!auction.isCategoryValid(set, idCategory)) {
            idCategory = Inputors.creatingInteger(scanner, text);
        }
        return idCategory;
    }

    public Auction searchIdOfAuctionToRemove(User user, AuctionDataBase auctionDataBase, AuctionControler auctionControler) {

        AuctionInterface auctionInterface = new AuctionInterface();
        AuctionView auctionView = new AuctionView();
        auctionView.printUserAuctions(auctionDataBase.getListOfAllAuction(), user);
        int indexToRemove = Inputors.creatingInteger(scanner, "Write id of auction which you would like to delete : ");
        return auctionControler.checkingAccesToRemoveAuction(user, auctionDataBase, indexToRemove);
    }


}
