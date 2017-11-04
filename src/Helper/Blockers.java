package Helper;

import java.io.*;
import java.util.Scanner;

/**
 * Created by Kamil on 2017-10-30.
 */
public class Blockers implements Serializable {

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

}
