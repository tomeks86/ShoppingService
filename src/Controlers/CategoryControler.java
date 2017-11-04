package Controlers;

import models.Category;
import views.CategoryView;

import java.util.HashSet;


public class CategoryControler {
    private static Category mainCategory = new Category(0, "CATEGORIES");

    public CategoryControler(Integer categoryId, String categoryName) {
        createCategoryTree(categoryId, categoryName);
    }


    public static Category getMainCategory() {
        return mainCategory;
    }





    private void createCategoryTree(Integer categoryId, String categoryName) {

        // Tutaj tak jak sie umawalismy tworzymy jedna klase

        Category category = new Category(categoryId,categoryName);

        Category vehicles = category.createCategory(1, "VEHICLES", mainCategory); // zeby od niej zaczynac wyswietlanie i tak dalej
        Category clothes = category.createCategory(2, "CLOTHES", mainCategory);
        Category underwear = category.createCategory(3, "UNDERWEAR", clothes);
        Category tshirts = category.createCategory(4, "T-SHIRTS", clothes);
        Category cars = category.createCategory(5, "CARS", vehicles);
        Category tires = category.createCategory(6, "TIRES", vehicles);
// FIXME root nazwa = null, nie potrzebuje zadnej nazwy

        category.addChildrensCategory(mainCategory, vehicles);
        category.addChildrensCategory(mainCategory, clothes);
        category.addChildrensCategory(vehicles, cars);
        category.addChildrensCategory(vehicles, tires);
        category.addChildrensCategory(clothes, underwear);
        category.addChildrensCategory(clothes, tshirts);

        category.createHashSetOfIdAviliableToAddTo(mainCategory); //FIXME komentarz na poczatku o co cho z ta metoda

    }

    public void showAllCategories() {
        CategoryView.viewAllCategories(mainCategory);
    }
}
