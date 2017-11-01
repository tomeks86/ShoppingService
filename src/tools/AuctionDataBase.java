package tools;

import models.Auction;
import models.Category;
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

    private int indexAuction = 1;


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

        System.out.println("Write id of category to which would you like add auction : ");
        Integer catId = categoryChoser();

        listOfAllAuction.add(new Auction(description, title, price, user, indexAuction, catId));
        indexAuction++;
        return true;
    }

    public boolean removeAuction(User user) {
        AuctionViewer.printUserAuctions(listOfAllAuction, user);
        System.out.println("Write id of auction which you would like to delete : ");
        Tools.stringToIntBlocker(scanner);
        Integer auctionId = scanner.nextInt();

        for (Auction auction : listOfAllAuction) {
            if (auction.getAuctionIndex().equals(auctionId) && auction.getUser().equals(user))
                auction.setActive(false);
        }
        return true;
    }


    public void printAllAuctions() {
        CategoryControler categoryControler = new CategoryControler();
        System.out.println("Write id of category to which would you like to show auctions : ");
        Integer catIdToPrintAuctions = categoryChoser();
        AuctionViewer.printAllAuctions(listOfAllAuction, catIdToPrintAuctions);
    }

    private Integer categoryChoser() {
        CategoryControler categoryControler = new CategoryControler();
        categoryControler.showAllCategories();
        Tools.stringToIntBlocker(scanner);
        int idCategory = scanner.nextInt();
        scanner.nextLine();
        switch (idCategory) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 4;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                return -1;
        }

    }

}
