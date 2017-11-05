package Controlers;

import models.Category;
import views.CategoryView;

import java.util.HashSet;


public class CategoryControler {
    Category category = new Category();
    CategoryView categoryView = new CategoryView();

    /*public CategoryControler(Integer categoryId, String categoryName) {
    }*/

    public void showAllCategories() {
        categoryView.viewAllCategories(Category.getMainCategory(), "");
    }

    public HashSet<Integer> getSetOfCategoriesAvailableToAdd() {
        return null;
    }

    public HashSet<Integer> getSetOfCategoryId() {
        return null;
    }
}
