package Controlers;

import Databases.AuctionDataBase;
import interfaceWithUsers.AuctionInterface;
import models.Auction;
import models.Category;
import models.User;
import views.AuctionView;
import views.CategoryView;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.stream.Collectors;

//OSobny pakiet na przetrymywanie danych , controler - >
// dodac klase user controler met login i ona odwołuje sie do userdatabase

public class AuctionControler {



    public void addAuction(AuctionDataBase dataBase, Auction auction, Connection connection) {

        AuctionView auctionView = new AuctionView();
        if (dataBase.addAuction(auction, connection))
            System.out.println(auctionView.showComunicatWhenAuctionAdded());
        else
            System.out.println(auctionView.showComunicatWhenAuctionNotAdded());
    }

    public void getAuctions(AuctionDataBase auctionDataBase, Connection connection) {
        CategoryController categoryController = new CategoryController();
        AuctionInterface auctionInterface = new AuctionInterface();
        CategoryView categoryView = new CategoryView();
        Category category = new Category();
        categoryView.viewAllCategories(category.mainCategory, " ");
        Integer catIdToPrintAuctions = auctionInterface.choseCategoryId("Write id of category to which would you like to show auctions (Write 0 to see all) : ", categoryController.getSetOfCategoryId());
        AuctionView.printAllAuctions(auctionDataBase.filterListToCategory(catIdToPrintAuctions, connection));
    }

    public Integer choseCategoryForAddedAuctions(AuctionInterface auctionInterface) {
        CategoryController categoryController = new CategoryController();
        categoryController.showAllCategories();
        return auctionInterface.choseCategoryId("Chose id of category to which you would like to add auction: ", categoryController.getSetOfCategoriesAvailableToAdd());
    }

    public void removeAuction(int idAuctionToRemove, Connection connection,AuctionDataBase auctionDataBase) {
           auctionDataBase.removeAuction(connection,idAuctionToRemove);
    }

    public int checkingAccesToRemoveAuction(ArrayList<Auction> listOfValidAuctionsToRemove, int auctionId) {

        for (Auction auction : listOfValidAuctionsToRemove) {
            if (auction.getAuctionIndex().equals(auctionId)
                    && auction.isActive())
                return auction.getAuctionIndex();
        }
        throw new NullPointerException("There is no such auction to remove! ");
    }

    public Auction checkAccessToBidAuction(AuctionDataBase auctionDataBase, Integer auctionId, User user, Connection connection) {
        for (Auction auction : auctionDataBase.getListOfAllAuctions(connection)) {
            if (auction.getAuctionIndex().equals(auctionId)
                    && (auction.getOwnerid() != user.getUserId(connection))
                    && (auction.isActive())
                    && ((auction.getBuyerId() == null)
                    || (auction.getBuyerId() != user.getUserId(connection))))
                return auction;

        }
        throw new NullPointerException("There is no such auction to bid! ");
    }

    public void getMessageWhenCannotRemoveAuction() {
        AuctionView auctionView = new AuctionView();
        System.out.println(auctionView.showComunicatWhenAuctionNotRemoved());
    }

    public ArrayList<Auction> getUserExpiredAuctions(User user, AuctionDataBase auctionDataBase, Connection connection) {
        ArrayList<Auction> expiredAuctions = new ArrayList<>(auctionDataBase.getListOfAllAuctions(connection).stream()
                .filter(auction -> (auction.getOwnerid()== user.getUserId(connection))
                        && (!auction.isActive()))
                .collect(Collectors.toCollection(ArrayList::new)));

        return expiredAuctions;
    }
}
