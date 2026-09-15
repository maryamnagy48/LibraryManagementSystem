package model;

public class Bookcase {
    private int bookcaseID;
    private String name;
    private int categoryID;

    private static final int MIN_VALID_ID = 1;

    public Bookcase(String name,int categoryID){
        if (name==null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Bookcase name cannot be empty");
        }
        if (categoryID<MIN_VALID_ID){
            throw new IllegalArgumentException("Category ID must be greater than 0");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Bookcase name must contain letters and spaces only");
        }
        this.name=name;
        this.categoryID=categoryID;
    }

    public int getBookcaseID() {
        return bookcaseID;
    }

    public void setBookcaseID(int bookcaseID){
        if(bookcaseID<MIN_VALID_ID){
            throw new IllegalArgumentException("bookcase ID must be greater than 0");
        }

        this.bookcaseID=bookcaseID;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        if (name==null || name.trim().isEmpty()){
            throw new IllegalArgumentException("bookcase name cannot be empty");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Bookcase name must contain letters and spaces only");
        }
        this.name=name;
    }

    public int getCategoryID(){
        return categoryID;
    }

    public void setCategoryID(int categoryID){
        if (categoryID<MIN_VALID_ID){
            throw new IllegalArgumentException("Category ID must be greater than 0");
        }
        this.categoryID=categoryID;
    }
}
