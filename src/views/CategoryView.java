package views;

import models.Category;

import javax.swing.*;

public class CategoryView {

    public void viewAllCategories(Category mainCategory) {
        Category category = mainCategory;
        System.out.println(category.toString());
        if (!category.hasChildrenCategories(category)) {
            for (Category category1 : category.getListOfChildrensCategory()) {
                viewAllCategories(category1);
            }
        } else return;

/*        if (!mainCategory.getListOfChildrensCategory().isEmpty()) { //TODO serio kurwa sout ?!
            for (int i = 0; i < mainCategory.getListOfChildrensCategory().size(); i++) {

                while (!category.getListOfChildrensCategory().isEmpty()) {
                    category = category.getListOfChildrensCategory().get(i);
                }

                category = category.getParent();

                System.out.println("\t" + category.getCategoryId() + " " + category.getCategoryName());

                for (Category cat : category.getListOfChildrensCategory()) {
                    System.out.println(+cat.getCategoryId() + " " + cat.getCategoryName());
                }
                category = category.getParent();
            }
        }*/
        /*System.out.println("\t1 VEHICLES");
        System.out.println("5 CARS");
        System.out.println("6 TIRES");
        System.out.println("\t2 CLOTHES");
        System.out.println("3 UNDERWEAR");
        System.out.println("4 T-SHIRTS");*/
    }

    public static void main(String[] args) {
        CategoryView categoryView = new CategoryView();
        Category category = new Category();
        categoryView.viewAllCategories(category);
    }
}
