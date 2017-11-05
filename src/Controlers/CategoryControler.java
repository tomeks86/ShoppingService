package Controlers;

import models.Category;
import views.CategoryView;

import java.util.HashSet;


public class CategoryControler {

    public CategoryControler(Integer categoryId, String categoryName) {
    }


    public void showAllCategories() {
        CategoryView.viewAllCategories(Category.getMainCategory());
    }
}
