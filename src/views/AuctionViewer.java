package views;

import models.Auction;
import models.User;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AuctionViewer {


    public static void printUserAuctions(ArrayList<Auction> listOfuserAuctions, User user) {
        System.out.println("Active " + user.getUserName() + " auctions: ");
        for (Auction auction : listOfuserAuctions) {
            if (auction.getUser().getUserName().equals(user.getUserName())
                    && auction.getUser().getPassword().equals(user.getPassword())
                    && auction.isActive() == true)
                System.out.println("Index : " + auction.getAuctionIndex() + "\ttitle: " + auction.getTitle());
            System.out.println("----------------------------------");
        }
    }

    public static void printAllAuctions(ArrayList<Auction> listofAllAuction, Integer categoryToPrint) {
        for (Auction auction : filtListToCategory(listofAllAuction, categoryToPrint)) {
            if (auction.isActive()) {
                System.out.println("Tytle: " + auction.getTitle() + "\nDescription : " + auction.getDescription() + "\nPrice : " + auction.getPrice());
                System.out.println("----------------------");
            }
        }
    }

    private static ArrayList<Auction> filtListToCategory(ArrayList<Auction> listofAllAuction, Integer categoryId) {
        if (categoryId > 6 || categoryId < 1)
            System.out.println("You chose wrong cat, all auctions will print");
        if (categoryId == 1) {
            ArrayList<Auction> newList = listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(1) || a.getCategoryId().equals(5) || a.getCategoryId().equals(6)))
                    .collect(Collectors.toCollection(ArrayList::new));
            return newList;
        } else if (categoryId == 5) {
            ArrayList<Auction> newList = listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(5)))
                    .collect(Collectors.toCollection(ArrayList::new));
            return newList;
        } else if (categoryId == 6) {
            ArrayList<Auction> newList = listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(6)))
                    .collect(Collectors.toCollection(ArrayList::new));
            return newList;
        } else if (categoryId == 2) {
            ArrayList<Auction> newList = listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(2) || a.getCategoryId().equals(3) || a.getCategoryId().equals(4)))
                    .collect(Collectors.toCollection(ArrayList::new));
            return newList;
        } else if (categoryId == 3) {
            ArrayList<Auction> newList = listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(3)))
                    .collect(Collectors.toCollection(ArrayList::new));
            return newList;
        } else if (categoryId == 4) {
            ArrayList<Auction> newList = listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(4)))
                    .collect(Collectors.toCollection(ArrayList::new));
            return newList;
        }
        return listofAllAuction;
    }
}
