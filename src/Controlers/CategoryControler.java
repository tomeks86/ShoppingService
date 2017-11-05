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
                idCategoriesAvailableToAdd.add(idOfEmptyChild(cat));
            }
        }
        return idCategoriesAvailableToAdd;
    }

    private Integer idOfEmptyChild(Category cat) {
        if (cat.getListOfChildrensCategory().isEmpty()) return cat.getCategoryId();
        else {
            for (Category cat1 : cat.getListOfChildrensCategory()) {
                return idOfEmptyChild(cat1);
            }
        }
        return null;
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
