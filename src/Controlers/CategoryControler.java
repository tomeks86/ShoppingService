package Controlers;

import models.Category;
import views.CategoryView;

import java.util.HashSet;


public class CategoryControler {
    CategoryView categoryView = new CategoryView();

    /*public CategoryControler(Integer categoryId, String categoryName) {
    }*/

    public void showAllCategories() {
        Category category = new Category();
        categoryView.viewAllCategories(category.getMainCategory());
    }

    public HashSet<Integer> getSetOfCategoriesAvailableToAdd() {
        return null;
    }

    public HashSet<Integer> getSetOfCategoryId() {
        return null;
    }
}
