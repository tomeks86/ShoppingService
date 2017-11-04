package tools;

import models.Category;
import views.ControlerViewer;

import java.util.ArrayList;
import java.util.HashSet;


public class CategoryControler {
    private HashSet<Integer> setOfCategoryId = new HashSet<>();
    private HashSet<Integer> setOfCategoriesAvileableToAdd = new HashSet<>();
    private static Category mainCategory = new Category(0, "CATEGORIES");

    public CategoryControler() {
        createCategoryTree();
    }

    public HashSet<Integer> getSetOfCategoryId() {
        return setOfCategoryId;
    }

    public HashSet<Integer> getSetOfCategoriesAvileableToAdd() {
        return setOfCategoriesAvileableToAdd;
    }

    public static Category getMainCategory() {
        return mainCategory;
    }

    private void addChildrensCategory(Category category, Category childrenCategory) {
        category.addChildrenCategory(childrenCategory);
    }

    private Category createCategory(Integer categoryId, String categoryName, Category parent) {
        setOfCategoryId.add(categoryId);
        return new Category(categoryId, categoryName, parent);
    }

    private void createHashSetOfIdAviliableToAddTo(Category category) {
        if (category.getListOfChildrensCategory().isEmpty()) {
            setOfCategoriesAvileableToAdd.add(category.getCategoryId());
        } else {
            for (Category category1 : category.getListOfChildrensCategory()) {
                createHashSetOfIdAviliableToAddTo(category1);
            }
        }
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

        createHashSetOfIdAviliableToAddTo(mainCategory);

    }

    public void showAllCategories() {
        // no i tu sie zaczyna zabawa
        ControlerViewer.viewAllCategories(mainCategory);
    }


    public static void main(String[] args) {
        CategoryControler categoryControler = new CategoryControler();
        categoryControler.showAllCategories();


    }


}
