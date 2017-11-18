package Databases;

import Helper.FileOperations;

import models.Auction;
import models.Category;


import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class AuctionDataBase implements Serializable {

    private int indexAuction = 1;
    private ArrayList<Auction> listOfAllAuctions;

    public int getIndexAuction() {
        if (!listOfAllAuctions.isEmpty())
            return (listOfAllAuctions.get(listOfAllAuctions.size() - 1).getAuctionIndex() + 1);
        else
            return indexAuction;
    }

    public AuctionDataBase() {
        listOfAllAuctions = FileOperations.loadAuctionList("Auction.bin");
    }


    public ArrayList<Auction> getListOfAllAuctions() {
        return listOfAllAuctions;
    }


    public boolean addAuction(Auction auction) {
        try {
            listOfAllAuctions.add(auction);
            indexAuction++;
            FileOperations.saveAuctionList(listOfAllAuctions, "Auction.bin");
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    public boolean removeAuction(Auction auction) {
        try {
            auction.setActive(false);
            FileOperations.saveAuctionList(listOfAllAuctions, "Auction.bin");
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public ArrayList<Auction> filterListToCategory(Integer categoryId) {
        Category category = new Category();
        ArrayList<Integer> ids = category.getSubCategoriesIds(categoryId);
        return listOfAllAuctions.stream()
                .filter(a -> ids.contains(a.getCategoryId()))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
