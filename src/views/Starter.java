package views;

import models.User;
import tools.Tools;
import tools.UserDataBase;

import java.security.AccessControlException;
import java.util.Scanner;

public class Starter {
    private Scanner scanner = new Scanner(System.in);
    private UserDataBase userDataBase = new UserDataBase();
    private User user = null;

    public Starter() {
        run();
    }

    private void run() {

        Integer end = -1;
        while(end !=0) {
            int value = mainMenu();
            if (value > 0)
                switcherMainMenu(value);
            else                        //<-ta konstrukcja mi się nie dokońca podoba, jak chcesz to zmień to trochę
                end = value;            //<- mam na myśli sam if else, wygląda średnio.
        }
    }


    private int mainMenu() {
        System.out.println("To log in choose 1\nTo register new User choose 2\nTo exit program choose 0");
        Tools.stringToIntBlocker(scanner);
        int value = scanner.nextInt();
        scanner.nextLine();
        while (value != 0 && value != 1 && value != 2) {
            System.out.println("Did you try to enter 0, 1 or 2?");
            Tools.stringToIntBlocker(scanner);
            value = scanner.nextInt();
            scanner.nextLine();
        }
        return value;
    }


    private void switcherMainMenu(int value) {
        switch (value) {
            case 1:
                try {
                    user = logIntoSystem(scanner, userDataBase);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (NullPointerException n) {
                    System.out.println(n.getMessage());     //<- mordo wydaje mi się, że lepiej od razu rzucić kulturalnym
                }                                           //<- nullPointerem na poziomie metody sprawdzającej dane
                break;                                      //<- do logowania, a nie tak jak było w wersji bardzo 1.0 czyli po
                                                            //<- prostu zwracać lamersko nulla i czekać aż się wypierdoli
            case 2:                                         // Tak na marginesie to ciekawi mnie troszeczkę czy to jest bad practice jak walisz nullem w takiej sytuacji
                try {
                    registerNewUser(scanner, userDataBase);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }catch (AccessControlException a){
                    System.out.println(a.getMessage());
                }
                break;

        }
    }


    private User logIntoSystem(Scanner scanner, UserDataBase userDataBase) {
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        User user = new User(login, password);

        if (userDataBase.isUserPresentInDataBase(user)) {
            System.out.println("Logged in!");
            return user;
        } else {
            throw new NullPointerException("Login error !");
        }

    }


    private void registerNewUser(Scanner scanner, UserDataBase userDataBase) {
        System.out.print("Create new login: ");
        String login = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (!userDataBase.isLoginAlreadyInUse(login)) {
            userDataBase.addNewUser(new User(login,password));                  //<- Wydaje mi się że w naszej wersji bardzo 1.0 dało się
            System.out.println("Register successful!");                         //<- dodać dwa nicki mariusz ale o różnych hasłach
        } else {                                                                //<- bo porównywaliśmy obiekty a nie loginy
            throw new AccessControlException("Login is not available!");        //<- jak możesz to "sprawdź to gówno!"
        }
    }


}
