package tools;

import models.Auction;
import models.User;
import views.AuctionViewer;

import java.util.ArrayList;
import java.util.Scanner;

public class AuctionDataBase {
    private ArrayList<Auction> listOfAllAuction;
    private Scanner scanner = new Scanner(System.in);

    public ArrayList<Auction> getListOfAllAuction() {
        return listOfAllAuction;
    }

    private int index = 1;


    public AuctionDataBase() {
        listOfAllAuction = new ArrayList<>();
    }

    public boolean addAuction(User user) {
        System.out.println("Write description: ");
        Tools.intToStrongBlocker(scanner);
        String description = scanner.nextLine();

        System.out.println("Write title: ");
        Tools.intToStrongBlocker(scanner);
        String title = scanner.nextLine();

        System.out.println("Write price: ");
        Tools.stringToDoubleBlocker(scanner);
        Double price = scanner.nextDouble();
        scanner.nextLine();

        listOfAllAuction.add(new Auction(description,title,price,user,index));
        index++;
        return true;
    }

    public boolean removeAuction(User user) {
        AuctionViewer.printUserAuctions(listOfAllAuction,user);
        System.out.println("Write id of auction which you would like to delete : ");
        Tools.stringToIntBlocker(scanner);
        Integer auctionId = scanner.nextInt();

        for (Auction auction:listOfAllAuction) {
            if(auction.getAuctionIndex().equals(auctionId) && auction.getUser().equals(user))
                auction.setActive(false);
        }
        return true;
    }


    public void printAllAuctions(){
        AuctionViewer.printAllAuctions(listOfAllAuction);
    }

}
