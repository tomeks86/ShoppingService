package Controlers;

import Helper.FileOperations;
import Helper.Tools;
import models.Auction;
import models.User;
import views.AuctionViewer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class AuctionDataBase implements Serializable {
    private ArrayList<Auction> listOfAllAuction;
    private Scanner scanner = new Scanner(System.in);

    public ArrayList<Auction> getListOfAllAuction() {
        return listOfAllAuction;
    }


    private int indexAuction = 1;


    public AuctionDataBase() {
        listOfAllAuction = FileOperations.loadAuctionList();
        //listOfAllAuction = new ArrayList<Auction>();
    }

    public boolean addAuction(User user) {
        if (listOfAllAuction.get(listOfAllAuction.size() - 1).getAuctionIndex() > 1)
            indexAuction = listOfAllAuction.get(listOfAllAuction.size() - 1).getAuctionIndex() + 1;
        System.out.println("Write description: ");
        String description = scanner.nextLine();
        description = Tools.emptyStringBlocker(description, scanner);

        System.out.println("Write title: ");
        String title = scanner.nextLine();
        title = Tools.emptyStringBlocker(title, scanner);

        System.out.println("Write price: ");
        Tools.stringToDoubleBlocker(scanner);
        Double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Write id of category to which would you like add auction : ");


        Integer catId = categoryChoserForAddMethod();

        listOfAllAuction.add(new Auction(description, title, price, user, indexAuction, catId));
        indexAuction++;
        return true;
    }

    public boolean removeAuction(User user) {
        AuctionViewer.printUserAuctions(listOfAllAuction, user);
        System.out.println("Write id of auction which you would like to delete : ");
        Tools.stringToIntBlocker(scanner);
        Integer auctionId = scanner.nextInt();
        scanner.nextLine();

        for (Auction auction : listOfAllAuction) {
            if (auction.getAuctionIndex().equals(auctionId)
                    && auction.getUser().getUserName().equals(user.getUserName())
                    && auction.getUser().getPassword().equals(user.getPassword()))
                auction.setActive(false);
        }
        return true;
    }


    public void printAllAuctions() {
        CategoryControler categoryControler = new CategoryControler();
        System.out.println("Write id of category to which would you like to show auctions (Write 0 to see all) : ");
        Integer catIdToPrintAuctions = categoryChoserForPrinting();
        AuctionViewer.printAllAuctions(listOfAllAuction, catIdToPrintAuctions);
    }

    private Integer categoryChoserForPrinting() {
        CategoryControler categoryControler = new CategoryControler();
        categoryControler.showAllCategories();
        Tools.stringToIntBlocker(scanner);
        int idCategory = scanner.nextInt();
        scanner.nextLine();

        while (!categoryControler.getSetOfCategoryId().contains(idCategory) ^ idCategory == 0) {
            System.out.println("There is no such category! Try again: ");
            Tools.stringToIntBlocker(scanner);
            idCategory = scanner.nextInt();
            scanner.nextLine();
        }


        return idCategory;

    }

    private Integer categoryChoserForAddMethod() {
        CategoryControler categoryControler = new CategoryControler();
        categoryControler.showAllCategories();
        Tools.stringToIntBlocker(scanner);
        int idCategory = scanner.nextInt();
        scanner.nextLine();

        while (!categoryControler.getSetOfCategoriesAvileableToAdd().contains(idCategory)) {
            System.out.println("There is no such category or there is no allowed to add to the selected one! Try again: ");
            Tools.stringToIntBlocker(scanner);
            idCategory = scanner.nextInt();
            scanner.nextLine();
        }

        return idCategory;
    }

}
