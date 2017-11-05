package Helper;

import models.Auction;
import models.User;

import java.io.*;
import java.util.ArrayList;

public class FileOperations implements Serializable {
    public static void saveUserList(ArrayList<User> user, String fileName) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(user);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> loadUserList(String fileName) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            ArrayList<User> loadArray = (ArrayList<User>) in.readObject();
            in.close();
            return loadArray;
        } catch (IOException | ClassNotFoundException e) {

            return new ArrayList<User>();
        }
    }

    public static void saveAuctionList(ArrayList<Auction> auction, String text) throws IOException {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(text));
            out.writeObject(auction);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Auction> loadAuctionList(String text) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(text));
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