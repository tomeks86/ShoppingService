package TestHelpers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseCheckout {

    public static boolean assertAuctionAddedToDatabase(Connection connection, String title, Integer id) {
        try {
            String checkTitle = null;
            Statement statement = connection.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("SELECT * FROM auctions WHERE id = "+id);
            while (resultSet.next()) {
                checkTitle = resultSet.getString("title");
            }

            if (checkTitle != null && checkTitle.equals(title))
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
