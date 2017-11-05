package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Category implements Serializable {
    private Integer categoryId;
    private String categoryName;
    private ArrayList<Category> listOfChildrensCategory;
    private Category parent;

    public static Category mainCategory = new Category(0, "CATEGORIES");

    private HashSet<Integer> setOfCategoryId = new HashSet<>();

    private HashSet<Integer> setOfCategoriesAvileableToAdd = new HashSet<>();

    public HashSet<Integer> getSetOfCategoryId() {
        return setOfCategoryId;
    }

    public HashSet<Integer> getSetOfCategoriesAvileableToAdd() {
        return setOfCategoriesAvileableToAdd;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Category getMainCategory() {
        return mainCategory;
    }

    public Category getParent() {
        return parent;
    }

    public String getCategoryName() {
        return categoryName;
    }

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

    public Category() {
        if (mainCategory.getListOfChildrensCategory().isEmpty())
            mainCategory = createCategoryTree(mainCategory);
    }

    public boolean hasChildrenCategories(Category category) {
        return (category.getListOfChildrensCategory().isEmpty());
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

    @Override
    public String toString() {
        return this.getCategoryId() + " " + this.getCategoryName();
    }

    private Category createCategoryTree(Category mainCategory) {

        Category vehicles = createCategory(1, "VEHICLES", mainCategory); // zeby od niej zaczynac wyswietlanie i tak dalej
        Category clothes = createCategory(2, "CLOTHES", mainCategory);
        Category underwear = createCategory(3, "UNDERWEAR", clothes);
        Category tshirts = createCategory(4, "T-SHIRTS", clothes);
        Category cars = createCategory(5, "CARS", vehicles);
        Category tires = createCategory(6, "TIRES", vehicles);

        addChildrensCategory(mainCategory, vehicles);
        addChildrensCategory(mainCategory, clothes);
        addChildrensCategory(vehicles, cars);
        addChildrensCategory(vehicles, tires);
        addChildrensCategory(clothes, underwear);
        addChildrensCategory(clothes, tshirts);

        createHashSetOfIdAviliableToAddTo(mainCategory);
        setOfCategoryId.add(0);
        return mainCategory;
    }


}
