package tools;

import models.Category;
import views.ControlerViewer;

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

    private void addChildrensCategory(Category category, Category childrenCategory) {
        category.addChildrenCategory(childrenCategory);
    }

    private Category createCategory(Integer categoryId, String categoryName, Category parent) {
        setOfCategoryId.add(categoryId);
        return new Category(categoryId, categoryName, parent);
    }

    private void createHashSetOfIdAviliableToAddTo(Category category){
        if (category.getListOfChildrensCategory().isEmpty()){
            setOfCategoriesAvileableToAdd.add(category.getCategoryId());
        }
        else{
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



    /*public boolean checkCategoryID(ArrayList<Category> categories, int id){
        for (Category category: categories) {
            if (category.getCategoryId() == id)
                return true;
        }                                                                                   //trzeba coś pokminić żeby się nie dało dodać id kategorii w kosmos przy dodawaniu aukcji
        for (Category category: categories) {
             if (category.hasChildrenCategories(category)){
                return  checkCategoryID(category.getListOfChildrensCategory(),id);
            }
        }


    }*/


    public static void main(String[] args) {
        CategoryControler categoryControler = new CategoryControler();
        categoryControler.showAllCategories();

        /*Category parent = new Category(1,"First parent" );
        Category child1 = new Category(101,"First parent" );
        Category child2 = new Category(102,"First parent" );
        Category childChild11 = new Category(10101,"First parent" );
        Category childChild12 = new Category(10102,"First parent" );
        Category childChild21 = new Category(10201,"First parent" );
        Category childChild22 = new Category(10202,"First parent" );
        Category childChildChild111 = new Category(1010101,"First parent" );
        Category childChildChild221 = new Category(1020201,"First parent" );

        ArrayList<Category> list = new ArrayList<>();
        parent.addChildrenCategory(child1);
        parent.addChildrenCategory(child2);
        child1.addChildrenCategory(childChild11);
        child1.addChildrenCategory(childChild12);
        child2.addChildrenCategory(childChild21);
        child2.addChildrenCategory(childChild22);
        childChild11.addChildrenCategory(childChildChild111);
        childChild22.addChildrenCategory(childChildChild221);

        CategoryControler categoryControler1 = new CategoryControler();
        System.out.println(categoryControler.checkCategoryID(parent.getListOfChildrensCategory(),101));*/

    }



}
