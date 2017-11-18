package views;

import models.Category;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CategoryViewTest {
    @Test
    public void viewAllCategories() throws Exception {
    }

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    CategoryView categoryView;
    Category category;

    @Before
    public void setUp() {
        categoryView = new CategoryView();
        category = new Category();
    }

    @Test
    public void shouldShowAllCategories() throws Exception {
        categoryView.viewAllCategories(category.mainCategory, "");
        assertTrue(systemOutRule.getLog().matches("0 CATEGORIES\\n(\\t*\\d+ \\D+\n)*"));
    }

}