package Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectToDatabase {

    private static ConectToDatabase instance = null;
    private static Connection connection = null;

    private ConectToDatabase(String dbName, String ownerName, String password) {

        System.out.println("POSTGRESQL CONNECTION TESTING...");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your PostgreSQL driver, add dependency to maven !!");
            e.printStackTrace();
        }

        System.out.println("Postgre JDBC Driver Registred!");

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/" + dbName, ownerName, password);
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("You've made it, take control your database now");
        } else
            System.out.println("Failed to make connection with database");
    }

    public static ConectToDatabase getInstance(String dbName, String ownerName, String password) {
        if (instance == null)
            instance = new ConectToDatabase(dbName, ownerName, password);
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }
}
