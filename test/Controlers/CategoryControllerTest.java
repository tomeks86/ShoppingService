package Controlers;


import models.Category;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import org.junit.Assert.*;

public class CategoryControllerTest {

    private CategoryController testObject;
    private Category category;

    @Before
    public void setUp(){
        testObject = new CategoryController();
        category = new Category();
    }

    @Test
    public void shouldReturnTrueIfCreatedHashSetOfEmptyChildIsCorrect(){
        HashSet<Integer> pattern = new HashSet<>();
        HashSet<Integer> tested = testObject.getSetOfCategoriesAvailableToAdd();
        pattern.add(3);
        pattern.add(4);
        pattern.add(5);
        pattern.add(6);

        Assert.assertEquals(pattern,tested);
    }


    @Test
    public void shouldReturnTrueIfCreatedHashSetOfAllCategoriesIsCorrect(){
        HashSet<Integer> pattern = new HashSet<>();
        HashSet<Integer> tested = testObject.getSetOfCategoryId();
        pattern.add(0);
        pattern.add(1);
        pattern.add(2);
        pattern.add(3);
        pattern.add(4);
        pattern.add(5);
        pattern.add(6);

        Assert.assertEquals(pattern,tested);
    }







}