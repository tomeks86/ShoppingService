package Controlers;

import models.Category;
import views.CategoryView;

import java.util.HashSet;


public class CategoryControler {
    CategoryView categoryView = new CategoryView();
    Category category = new Category();

    public void showAllCategories() {
        categoryView.viewAllCategories(category.mainCategory, "");
    }

    public HashSet<Integer> getSetOfCategoriesAvailableToAdd() {
        Category mainCategory = category.mainCategory;
        HashSet<Integer> idCategoriesAvailableToAdd = new HashSet<>();
        if (mainCategory.getListOfChildrensCategory().isEmpty()) idCategoriesAvailableToAdd.add(mainCategory.getCategoryId());
        else {
            for (Category cat : mainCategory.getListOfChildrensCategory()){
                idCategoriesAvailableToAdd.addAll(idOfEmptyChild(cat));
            }
        }
        return idCategoriesAvailableToAdd;
    }

    private HashSet<Integer> idOfEmptyChild(Category cat) {
        HashSet<Integer> ids = new HashSet<>();
        if (cat.getListOfChildrensCategory().isEmpty()) ids.add(cat.getCategoryId());
        else {
            for (Category cat1 : cat.getListOfChildrensCategory()) {
                ids.addAll(idOfEmptyChild(cat1));
            }
        }
        return ids;
    }

    public HashSet<Integer> getSetOfCategoryId() {
        Category mainCategory = category.mainCategory;
        HashSet<Integer> IDOfCategories = new HashSet<>();
        IDOfCategories.add(mainCategory.getCategoryId());
        if (!mainCategory.getListOfChildrensCategory().isEmpty()) {
            for (Category cat1 : mainCategory.getListOfChildrensCategory()) {
                IDOfCategories.addAll(idOfChildren(cat1));
            }
        }
        return IDOfCategories;
    }

    private HashSet<Integer> idOfChildren(Category category) {
        HashSet<Integer> childIDs = new HashSet<>();
        childIDs.add(category.getCategoryId());
        if (!category.getListOfChildrensCategory().isEmpty()) {
            for (Category cat1 : category.getListOfChildrensCategory()) {
                childIDs.addAll(idOfChildren(cat1));
            }
        }
        return childIDs;
    }
}
