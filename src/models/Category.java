package models;

import java.util.ArrayList;

public class Category {

    private Integer categoryId;
    private String categoryName;
    private ArrayList<Category> listOfChildrensCategory;

    public Category(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        listOfChildrensCategory = new ArrayList<>();
    }

    public ArrayList<Category> getListOfChildrensCategory() {
        return listOfChildrensCategory;
    }

    public void addChildrenCategory(Category category) {
        listOfChildrensCategory.add(category);
    }

}
