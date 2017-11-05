package views;

import models.Category;

import javax.swing.*;

public class CategoryView {

    public void viewAllCategories(Category mainCategory, String tab) {
        Category category = mainCategory;
        System.out.println(tab + category.toString());
        tab += "\t";
        if (category.hasChildrenCategories(category)) {
            for (Category category1 : category.getListOfChildrensCategory()) {
                viewAllCategories(category1, tab);
            }
        } else return;

    /*public static void main(String[] args) {
        CategoryView categoryView = new CategoryView();
        Category category = new Category();
        categoryView.viewAllCategories(category.mainCategory, "");
    }*/
}
