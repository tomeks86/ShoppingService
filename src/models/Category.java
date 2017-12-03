package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Category {
    private Integer id;
    private Integer parentCategoryID;
    String categoryName;

    public Category(Integer id, Integer parentCategoryID, String categoryName) {
        this.id = id;
        this.parentCategoryID = parentCategoryID;
        this.categoryName = categoryName;
    }

    public Integer getParentCategoryID() {
        return parentCategoryID;
    }

    public Integer getID() {
        return id;
    }

    @Override
    public String toString() {
        //return super.toString();
        if (this.parentCategoryID == 0 || this.parentCategoryID == null) return String.format("Main category: %s", this.categoryName, this.id);
        else return String.format("    category: %s (id: %d)", this.categoryName, this.id);
    }
}
