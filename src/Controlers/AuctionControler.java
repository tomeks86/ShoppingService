package Controlers;

import Databases.AuctionDataBase;
import interfaceWithUsers.AuctionInterface;
import models.Auction;
import models.Category;
import models.User;
import views.AuctionView;
import views.CategoryView;

import java.io.Serializable;
import java.util.ArrayList;

//OSobny pakiet na przetrymywanie danych , controler - >
// dodac klase user controler met login i ona odwołuje sie do userdatabase

public class AuctionControler implements Serializable {

    public void addAuction(AuctionDataBase dataBase, Auction auction) {

        AuctionView auctionView = new AuctionView();
        if (dataBase.addAuction(auction))
            System.out.println(auctionView.showComunicatWhenAuctionAdded());
        else
            System.out.println(auctionView.showComunicatWhenAuctionNotAdded());
    }

    public void getAuctions(AuctionDataBase auctionDataBase) {
        CategoryController categoryController = new CategoryController();
        AuctionInterface auctionInterface = new AuctionInterface();
        CategoryView categoryView = new CategoryView();
        Category category = new Category();
        categoryView.viewAllCategories(category.mainCategory, " ");
        Integer catIdToPrintAuctions = auctionInterface.choseCategoryId("Write id of category to which would you like to show auctions (Write 0 to see all) : ", categoryController.getSetOfCategoryId());
        AuctionView.printAllAuctions(auctionDataBase.filterListToCategory(catIdToPrintAuctions));
    }

    public Integer choseCategoryForAddedAuctions(AuctionInterface auctionInterface) {
        CategoryController categoryController = new CategoryController();
        categoryController.showAllCategories();
        return auctionInterface.choseCategoryId("Chose id of category to which you would like to add auction: ", categoryController.getSetOfCategoriesAvailableToAdd());
    }


    public void removeAuction(AuctionDataBase auctionDataBase, Auction auction, User user) {
        AuctionView auctionView = new AuctionView();
        if (auction != null) {
            auctionView.printUserAuctions(auctionDataBase.getListOfAllAuctions(), user);
            if (auctionDataBase.removeAuction(auction))
                System.out.println(auctionView.showComunicatWhenAuctionRemoved());
        } else
            System.out.println(auctionView.showComunicatWhenAuctionNotRemoved());
    }

    public Auction checkingAccesToRemoveAuction(User user, AuctionDataBase auctionDataBase, int auctionId) {

        for (Auction auction : auctionDataBase.getListOfAllAuctions()) {
            if (auction.getAuctionIndex().equals(auctionId)
                    && auction.getUser().getUserName().equals(user.getUserName())
                    && auction.getUser().getPassword().equals(user.getPassword())
                    && auction.isActive())
                return auction;
        }
        throw new NullPointerException("There is no such auction to remove! ");
    }

    public Auction checkAccessToBidAuction(AuctionDataBase auctionDataBase, Integer auctionId, User user) {
        for (Auction auction : auctionDataBase.getListOfAllAuctions()) {
            if (auction.getAuctionIndex().equals(auctionId)
                    && (!auction.getUser().getUserName().equals(user.getUserName()))
                    && (!auction.getUser().getPassword().equals(user.getPassword()))
                    && (auction.isActive())
                    && ((auction.getBidCounter() == 0)
                    || ((!auction.getBuyer().getUserName().equals(user.getUserName())) && (!auction.getBuyer().getPassword().equals(user.getPassword())))))
                    return auction;

        }
        throw new NullPointerException("There is no such auction to bid! ");
    }

    public void getMessageWhenCannotRemoveAuction() {
        AuctionView auctionView = new AuctionView();
        System.out.println(auctionView.showComunicatWhenAuctionNotRemoved());
    }

    public ArrayList<Auction> getUserExpiredAuctions(User user, AuctionDataBase auctionDataBase) {
        ArrayList<Auction> expiredAuctions = new ArrayList<>();
        for (Auction auction : auctionDataBase.getListOfAllAuctions()) {
            if (auction.getUser().equals(user) && !auction.isActive()) expiredAuctions.add(auction);
        }
        return expiredAuctions;
    }
}
