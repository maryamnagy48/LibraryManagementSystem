package model;

public class Category {
    private int categoryID;
    private String name;

    private static final int MIN_VALID_ID = 1;
    public Category(String name){

        if(name==null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Category name must contain letters and spaces only");
        }

        this.name=name;
    }

    public int getCategoryId(){
        return categoryID;
    }

    public void setCategoryID(int categoryID){
        if(categoryID<MIN_VALID_ID){
            throw new IllegalArgumentException("Category ID must be greater than 0");
        }
        this.categoryID=categoryID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        if(name==null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Category name must contain letters and spaces only");
        }
        this.name=name;
    }

}
