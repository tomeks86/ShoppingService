package tools;

import models.Auction;
import models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Kamil on 2017-10-30.
 */
public class Tools implements Serializable {

    public static void stringToIntBlocker(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Try with an integer");
            scanner.nextLine();
        }
    }

    public static void stringToDoubleBlocker(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Try with an double");
            scanner.nextLine();
        }
    }

    public static String emptyStringBlocker(String string, Scanner scanner) {
        while (string.trim().isEmpty()){
            System.out.println("The string is empty !");
            string=scanner.nextLine();
        }
        return string;
    }

    public static void saveUserList(ArrayList<User> user) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("User.bin"));
            out.writeObject(user);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> loadUserList() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("User.bin"));
            ArrayList<User> loadArray = (ArrayList<User>) in.readObject();
            in.close();
            return loadArray;
        } catch (IOException | ClassNotFoundException e) {

            return new ArrayList<User>();
        }
    }

    public static void saveAuctionList(ArrayList<Auction> auction) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Auction.bin"));
            out.writeObject(auction);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Auction> loadAuctionList() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Auction.bin"));
            ArrayList<Auction> loadArray = (ArrayList<Auction>) in.readObject();
            in.close();
            return loadArray;
        } catch (IOException e) {

            return new ArrayList<Auction>();
        } catch (ClassNotFoundException e) {

            return new ArrayList<Auction>();
        }

    }
}
