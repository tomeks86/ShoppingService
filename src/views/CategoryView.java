package views;

import models.Category;

public class CategoryView {

    public void viewAllCategories(Category mainCategory, String tab) {
        System.out.println(tab + mainCategory.toString());
        tab += "\t";
        if (!mainCategory.getListOfChildrensCategory().isEmpty()) {
            for (Category category1 : mainCategory.getListOfChildrensCategory()) {
                viewAllCategories(category1, tab);
            }
        }
    }

    public static void main(String[] args) {
        CategoryView categoryView = new CategoryView();
        categoryView.viewAllCategories(Category.mainCategory, "");
    }
}
