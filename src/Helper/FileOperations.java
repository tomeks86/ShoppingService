package Helper;

import models.Auction;
import models.User;

import java.io.*;
import java.util.ArrayList;

public class FileOperations implements Serializable {   //FIXME trudne do testu, sciezka w parametrze!!!!!
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