package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectToDatabase {

    private static ConectToDatabase instance = null;
    private static Connection connection = null;

    private ConectToDatabase() {

        System.out.println("POSTGRESQL CONNECTION TESTING...");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL driver, add dependency to maven !!");
            e.printStackTrace();
        }

        System.out.println("Postgre JDBC Driver Registred!");

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/zenek", "zenek", "123");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("You've made it, take control your database now");
        } else
            System.out.println("Failed to make connection with database");
    }

    public static ConectToDatabase getInstance() {
        if (instance == null)
            instance = new ConectToDatabase();
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }
}
