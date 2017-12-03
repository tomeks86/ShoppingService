package views;

import models.Category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryView {

    public void showCategory(Category category) {
        System.out.println(category.toString());
    }
}
