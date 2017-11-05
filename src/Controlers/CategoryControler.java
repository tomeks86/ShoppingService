package Controlers;

import models.Category;
import views.CategoryView;

import java.util.HashSet;


public class CategoryControler {
    CategoryView categoryView = new CategoryView();
    Category category = new Category();

    /*public CategoryControler(Integer categoryId, String categoryName) {
    }*/

    public void showAllCategories() {
        categoryView.viewAllCategories(category.getMainCategory(), "");
    }

    public HashSet<Integer> getSetOfCategoriesAvailableToAdd() {
        Category category = new Category();
        return category.getSetOfCategoriesAvileableToAdd();
    }

    public HashSet<Integer> getSetOfCategoryId() {
        Category category = new Category();
        return category.getSetOfCategoryId();
    }
}
