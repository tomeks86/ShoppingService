package Helper;

import java.util.Scanner;

public class Inputors {
    public static String creatingString(Scanner scanner, String order) {

        System.out.println(order);
        return scanner.nextLine();
    }

    public static Double creatingDuble(Scanner scanner, String order) {
        System.out.println(order);
        Blockers.stringToDoubleBlocker(scanner);
        Double doubleValue = scanner.nextDouble();
        scanner.nextLine();
        return doubleValue;
    }

    public static Integer creatingInteger(Scanner scanner, String order) {
        System.out.println(order);
        Blockers.stringToIntBlocker(scanner);
        Integer integerValue = scanner.nextInt();
        scanner.nextLine();
        return integerValue;
    }
}
