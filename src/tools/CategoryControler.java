package tools;

import models.Category;
import views.ControlerViewer;

import java.util.ArrayList;
import java.util.Arrays;


public class CategoryControler {

    static Category mainCategory = new Category(0, "CATEGORIES");

    public CategoryControler() {
        createCategoryTree();
    }

    private void addChildrensCategory(Category category, Category childrenCategory) {
        category.addChildrenCategory(childrenCategory);
    }

    private Category createCategory(Integer categoryId, String categoryName, Category parent) {
        return new Category(categoryId, categoryName, parent);
    }

    private void createCategoryTree() {
        // Tutaj tak jak sie umawalismy tworzymy jedna klase
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

    }

    public static void showAllCategories() {
        // no i tu sie zaczyna zabawa
        ControlerViewer.viewAllCategories(mainCategory);
    }

    public static void main(String[] args) {
        CategoryControler categoryControler = new CategoryControler();
        categoryControler.showAllCategories();
    }

}
