package views;

import models.Category;

import javax.swing.*;

public class CategoryView {

    public void viewAllCategories(Category mainCategory, String tab) {
        System.out.println(tab + mainCategory.toString());
        tab += "\t";
        if (mainCategory.hasChildrenCategories(mainCategory)) {
            for (Category category1 : mainCategory.getListOfChildrensCategory()) {
                viewAllCategories(category1, tab);
            }
        }
    }

    public static void main(String[] args) {
        CategoryView categoryView = new CategoryView();
        Category category = new Category();
        categoryView.viewAllCategories(category.mainCategory, "");
    }
}
