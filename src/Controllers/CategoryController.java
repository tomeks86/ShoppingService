package Controllers;

import models.Category;
import views.CategoryView;
import views.Starter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CategoryController {
    private CategoryView categoryView = new CategoryView();
    private ArrayList<Category> mainCategories;
    private ArrayList<Category> subCategories;
    private ArrayList<Category> categories;
    private ArrayList<Integer> categoriesIDtoAdd;

    public ArrayList<Integer> getCategoriesIDtoAdd() {
        return categoriesIDtoAdd;
    }


    public CategoryController() {
        categories = new ArrayList<>();
        mainCategories = new ArrayList<>();
        subCategories = new ArrayList<>();
        categoriesIDtoAdd = new ArrayList<>();
        getMainCategories();
        getSubCategories();
        reorderCategories();
    }

    private void reorderCategories() {
        for (Category mainCategory : mainCategories) {
            categories.add(mainCategory);
            for (Category subCategory : subCategories) {
                if (subCategory.getParentCategoryID() == mainCategory.getID()) {
                    categories.add(subCategory);
                }
            }
        }
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void showAllCategories() {
        for (Category category : categories) {
            categoryView.showCategory(category);
        }
    }

    private void getMainCategories() {
        try {
            ResultSet rs = Starter.getConnection().createStatement().executeQuery("SELECT * FROM category WHERE parentcategoryid IS NULL ORDER BY ID");
            while (rs.next()) {
                mainCategories.add(new Category(rs.getInt("id"), rs.getInt("parentcategoryid"), rs.getString("categoryname")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getSubCategories() {
        try {
            ResultSet rs = Starter.getConnection().createStatement().executeQuery("SELECT * from CATEGORY ct1 WHERE parentcategoryid IN (SELECT id FROM category ct2 WHERE parentcategoryid is null) ORDER BY parentcategoryid");
            while (rs.next()) {
                subCategories.add(new Category(rs.getInt("id"), rs.getInt("parentcategoryid"), rs.getString("categoryname")));
                categoriesIDtoAdd.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
