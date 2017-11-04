package models;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

    private Integer categoryId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public Category getParent() {
        return parent;
    }

    private String categoryName;
    private ArrayList<Category> listOfChildrensCategory;
    Category parent;

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

    public boolean hasChildrenCategories(Category category){
        return (!category.getListOfChildrensCategory().isEmpty());
    }

    public ArrayList<Category> getListOfChildrensCategory() {
        return listOfChildrensCategory;
    }

    public void addChildrenCategory(Category category) {
        listOfChildrensCategory.add(category);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
