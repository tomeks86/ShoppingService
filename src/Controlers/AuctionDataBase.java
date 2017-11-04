package Controlers;

import Helper.FileOperations;
import Helper.Blockers;
import models.Auction;
import models.User;
import views.AuctionView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

//OSobny pakiet na przetrymywanie danych , controler - >
// dodac klase user controler met login i ona odwołuje sie do userdatabase

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
        description = Blockers.emptyStringBlocker(description, scanner);

        System.out.println("Write title: ");
        String title = scanner.nextLine();
        title = Blockers.emptyStringBlocker(title, scanner);

        System.out.println("Write price: ");
        Blockers.stringToDoubleBlocker(scanner);
        Double price = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Write id of category to which would you like add auction : ");


        Integer catId = categoryChoserForAddMethod();

        listOfAllAuction.add(new Auction(description, title, price, user, indexAuction, catId));
        indexAuction++; //FIXME zapisywanie listy aukcji do pamieci
        return true;
    }

    public boolean removeAuction(User user) {
        AuctionView.printUserAuctions(listOfAllAuction, user);
        System.out.println("Write id of auction which you would like to delete : ");
        Blockers.stringToIntBlocker(scanner);
        Integer auctionId = scanner.nextInt();
        scanner.nextLine();

        for (Auction auction : listOfAllAuction) { // FIXME to do modelu auction do metody osobnej (user)
            if (auction.getAuctionIndex().equals(auctionId)
                    && auction.getUser().getUserName().equals(user.getUserName())
                    && auction.getUser().getPassword().equals(user.getPassword()))
                auction.setActive(false);
        }
        return true;
    }


    public void printAllAuctions() { //FIXME zla nazwa nie all -- > wybrane
        CategoryControler categoryControler = new CategoryControler();
        System.out.println("Write id of category to which would you like to show auctions (Write 0 to see all) : ");
        Integer catIdToPrintAuctions = categoryChoserForPrinting();

        AuctionView.printAllAuctions(filtrListToCategory(listOfAllAuction, catIdToPrintAuctions));
    }

    private Integer categoryChoserForPrinting() {
        CategoryControler categoryControler = new CategoryControler();
        categoryControler.showAllCategories();
        Blockers.stringToIntBlocker(scanner);
        int idCategory = scanner.nextInt();
        scanner.nextLine();

        while (!categoryControler.getSetOfCategoryId().contains(idCategory) ^ idCategory == 0) {
            System.out.println("There is no such category! Try again: ");
            Blockers.stringToIntBlocker(scanner);
            idCategory = scanner.nextInt();
            scanner.nextLine();
        }


        return idCategory;

    }

    private Integer categoryChoserForAddMethod() { //FIXME Zla nazwa , metody -- > czasowniki
        CategoryControler categoryControler = new CategoryControler();
        categoryControler.showAllCategories();
        Blockers.stringToIntBlocker(scanner);
        int idCategory = scanner.nextInt();
        scanner.nextLine();

        while (!categoryControler.getSetOfCategoriesAvileableToAdd().contains(idCategory)) {
            System.out.println("There is no such category or there is no allowed to add to the selected one! Try again: ");
            Blockers.stringToIntBlocker(scanner);
            idCategory = scanner.nextInt();
            scanner.nextLine();
        }

        return idCategory;
    }
    public static ArrayList<Auction> filtrListToCategory(ArrayList<Auction> listofAllAuction, Integer categoryId) {
        if (categoryId == 1) {
            return listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(1) || a.getCategoryId().equals(5) || a.getCategoryId().equals(6)))
                    .collect(Collectors.toCollection(ArrayList::new));
        } else if (categoryId == 5) {
            return listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(5)))
                    .collect(Collectors.toCollection(ArrayList::new));
        } else if (categoryId == 6) {
            return listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(6)))
                    .collect(Collectors.toCollection(ArrayList::new));
        } else if (categoryId == 2) {
            return listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(2) || a.getCategoryId().equals(3) || a.getCategoryId().equals(4)))
                    .collect(Collectors.toCollection(ArrayList::new));
        } else if (categoryId == 3) {
            return listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(3)))
                    .collect(Collectors.toCollection(ArrayList::new));
        } else if (categoryId == 4) {
            return listofAllAuction.stream()
                    .filter(a -> (a.getCategoryId().equals(4)))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return listofAllAuction;
    }


}
