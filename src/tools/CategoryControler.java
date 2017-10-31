package tools;

import models.Category;

import java.util.ArrayList;
import java.util.Arrays;


public class CategoryControler {

    static Category mainCategory = new Category(0, "CATEGORIES");

    private void addChildrensCategory(Category category, Category childrenCategory) {
        category.addChildrenCategory(childrenCategory);
    }

    private Category createCategory(Integer categoryId, String categoryName) {
        return new Category(categoryId, categoryName);
    }

    private void createCategoryTree() {
        // Tutaj tak jak sie umawalismy tworzymy jedna klase
        Category vehicles = createCategory(1, "VEHICLES"); // zeby od niej zaczynac wyswietlanie i tak dalej
        Category clothes = createCategory(2, "CLOTHES");
        Category underwear = createCategory(3, "UNDERWEAR");
        Category tshirts = createCategory(4, "T-SHIRTS");
        Category cars = createCategory(5, "CARS");
        Category tires = createCategory(6, "TIRES");

        addChildrensCategory(mainCategory, vehicles);
        addChildrensCategory(mainCategory, clothes);
        addChildrensCategory(vehicles, cars);
        addChildrensCategory(vehicles, tires);
        addChildrensCategory(clothes, underwear);
        addChildrensCategory(clothes, tshirts);
    }

    public static void showAllCategories() {
// no i tu sie zaczyna zabawa
        Category category = mainCategory;

        do {
            category = category.getListOfChildrensCategory().get(0);
            System.out.println(category.getCategoryName());
        } while (category.getListOfChildrensCategory() == null);


    }

    public static void main(String[] args) {
        CategoryControler categoryControler = new CategoryControler();
        categoryControler.createCategoryTree();
        showAllCategories();
    }


}
