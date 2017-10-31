package tools;

import java.util.Scanner;

/**
 * Created by Kamil on 2017-10-30.
 */
public class Tools {

    public static void stringToIntBlocker(Scanner scanner){
        while(!scanner.hasNextInt()){
            System.out.println("Try with an integer");
            scanner.nextLine();
        }
    }

    public static void intToStrongBlocker(Scanner scanner) {
        while (!scanner.hasNext()) {
            System.out.println("Try with string");
            scanner.nextLine();
        }
    }
}
