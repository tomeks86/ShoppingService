package interfaceWithUsers;

import Controlers.AuctionControler;
import Databases.AuctionDataBase;
import Helper.Inputors;
import models.Auction;
import models.User;
import views.AuctionView;

import java.sql.Connection;
import java.util.HashSet;
import java.util.Scanner;

public class AuctionInterface {
    private Scanner scanner = new Scanner(System.in);




    public Auction createAuctionToAdd(User user,AuctionControler auctionControler,AuctionInterface auctionInterface,AuctionDataBase auctionDataBase) {
        int indexAuction = auctionDataBase.getIndexAuction();

        return (new Auction(Inputors.creatingString(scanner, "Description of auction: "),
                Inputors.creatingString(scanner, "Title :"),
                Inputors.creatingDuble(scanner, "Price: "), user, indexAuction,
                auctionControler.choseCategoryForAddedAuctions(auctionInterface)));
    }


    public Integer choseCategoryId(String text, HashSet<Integer> set) {
        Auction auction = new Auction();
        int idCategory = Inputors.creatingInteger(scanner, text);

        while (!auction.isCategoryValid(set, idCategory)) {
            idCategory = Inputors.creatingInteger(scanner, text);
        }
        return idCategory;
    }

    public int searchIdOfAuctionToRemove(User user, AuctionDataBase auctionDataBase, AuctionControler auctionControler, Connection connection) {

        AuctionView auctionView = new AuctionView();
        auctionView.printUserAuctions(auctionDataBase.getListOfUserAuctions(user,connection),user);
        int idAuctioToRemove = Inputors.creatingInteger(scanner, "Write id of auction which you would like to delete : ");
        return auctionControler.checkingAccesToRemoveAuction(auctionDataBase.getListOfUserAuctions(user,connection),idAuctioToRemove);
    }


}
