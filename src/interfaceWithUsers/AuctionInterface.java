package interfaceWithUsers;

import Controlers.AuctionControler;
import Controlers.CategoryControler;
import Databases.AuctionDataBase;
import Helper.Blockers;
import Helper.FileOperations;
import models.Auction;
import models.Category;
import models.User;
import views.AuctionView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AuctionInterface {
    private Scanner scanner = new Scanner(System.in);


    public String creatingString(String order) {

        System.out.println(order);
        return scanner.nextLine();
    }

    public Double creatingDuble(String order) {
        System.out.println(order);
        Blockers.stringToDoubleBlocker(scanner);
        Double doubleValue = scanner.nextDouble();
        scanner.nextLine();
        return doubleValue;
    }

    public Integer creatingInteger(String order) {
        System.out.println(order);
        Blockers.stringToIntBlocker(scanner);
        Integer integerValue = scanner.nextInt();
        scanner.nextLine();
        return integerValue;
    }

    public Auction createAuctionToAdd(User user,AuctionControler auctionControler,AuctionInterface auctionInterface,AuctionDataBase auctionDataBase) {
        ArrayList<Auction> listOfAllAuction = auctionDataBase.getListOfAllAuction();
        int indexAuction = auctionDataBase.getIndexAuction();

        return (new Auction(auctionInterface.creatingString("Description of auction: "),
                auctionInterface.creatingString("Title :"),
                auctionInterface.creatingDuble("Price: "), user, indexAuction,
                auctionControler.choseCategoryForAddedAuctions(auctionInterface)));
    }


    public Integer choseCategoryId(String text, HashSet<Integer> set) {
        Auction auction = new Auction();
        AuctionInterface auctionInterface = new AuctionInterface();
        int idCategory = auctionInterface.creatingInteger(text);

        while (!auction.isCategoryValid(set, idCategory)) {
            idCategory = auctionInterface.creatingInteger(text);
        }
        return idCategory;
    }

    public Auction searchIdOfAuctionToRemove(User user, AuctionDataBase auctionDataBase) {
        Auction auction = new Auction();
        AuctionInterface auctionInterface = new AuctionInterface();
        AuctionView auctionView = new AuctionView();
        auctionView.printUserAuctions(auctionDataBase.getListOfAllAuction(), user);
        int indexToRemove = auctionInterface.creatingInteger("Write id of auction which you would like to delete : ");
        return auction.checkingAccesToRemoveAuction(user, auctionDataBase, indexToRemove);
    }
}
