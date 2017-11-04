package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Category implements Serializable {

    private Integer categoryId;

    private HashSet<Integer> setOfCategoryId = new HashSet<>();
    private HashSet<Integer> setOfCategoriesAvileableToAdd = new HashSet<>();

    public Integer getCategoryId() {
        return categoryId;
    }

    public Category getParent() {
        return parent;
    }

    private String categoryName;
    private ArrayList<Category> listOfChildrensCategory;
    private Category parent;

    public Category(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        listOfChildrensCategory = new ArrayList<>();
    }

    public Category(Integer categoryId, String categoryName, Category parent) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.parent = parent;
        listOfChildrensCategory = new ArrayList<>();
    }

    public boolean hasChildrenCategories(Category category) {
        return (!category.getListOfChildrensCategory().isEmpty());
    }

    public ArrayList<Category> getListOfChildrensCategory() {
        return listOfChildrensCategory;
    }

    public void addChildrenCategory(Category category) {
        listOfChildrensCategory.add(category);
    }


    public void addChildrensCategory(Category category, Category childrenCategory) {
        category.addChildrenCategory(childrenCategory);
    }

    public Category createCategory(Integer categoryId, String categoryName, Category parent) {
        setOfCategoryId.add(categoryId);
        return new Category(categoryId, categoryName, parent);
    }

    public void createHashSetOfIdAviliableToAddTo(Category category) {
        if (category.getListOfChildrensCategory().isEmpty()) {
            setOfCategoriesAvileableToAdd.add(category.getCategoryId());
        } else {
            for (Category category1 : category.getListOfChildrensCategory()) {
                createHashSetOfIdAviliableToAddTo(category1);
            }
        }
    }





}
