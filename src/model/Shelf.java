package model;

import java.util.ArrayList;
import java.util.List;

public class Shelf {
    private int shelfID;
    private int shelfNumber;
    private int capacity;
    private int bookcaseID;
    private int currentCopies;

    private List<BookShelf> bookShelfList;
    private static final int MIN_VALID_ID = 1;

    public Shelf(int shelfnumber,int capacity,int bookcaseID){

        if (shelfnumber<MIN_VALID_ID){
            throw new IllegalArgumentException("Shelf number must be greater than 0");
        }
        if (capacity<MIN_VALID_ID){
            throw new IllegalArgumentException("capacity must be greater than 0");
        }
        if (bookcaseID <MIN_VALID_ID) {
            throw new IllegalArgumentException("Bookcase ID must be greater than 0");
        }
        this.shelfNumber =shelfnumber;
        this.capacity=capacity;
        this.currentCopies =0;
        this.bookcaseID=bookcaseID;

        this.bookShelfList=new ArrayList<>();
    }

    public boolean isFull(){
        return currentCopies >=capacity;
    }

    public int getAvailableSpace(){
        return capacity- currentCopies;
    }
    public int getShelfID() {
        return shelfID;
    }

    public void setShelfID(int shelfID) {
        if (shelfID<MIN_VALID_ID){
            throw new IllegalArgumentException("Shelf ID must be greater than 0");
        }
        this.shelfID = shelfID;
    }

    public int getShelfNumber() {
        return shelfNumber;
    }

    public void setShelfNumber(int shelfNumber) {
        if (shelfNumber <MIN_VALID_ID){
            throw new IllegalArgumentException("Shelf number must be greater than 0");
        }
        this.shelfNumber = shelfNumber;
    }

    public int getCapacity() {

        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity<MIN_VALID_ID){
            throw new IllegalArgumentException("capacity must be greater than 0");
        }
        if (capacity< currentCopies){
            throw new IllegalArgumentException("Capacity can't be less than current copies");
        }
        this.capacity = capacity;
    }

    public int getBookcaseID() {
        return bookcaseID;
    }

    public void setBookcaseID(int bookcaseID) {
        if (bookcaseID <MIN_VALID_ID) {throw new IllegalArgumentException("Bookcase ID must be greater than 0");
        }
        this.bookcaseID = bookcaseID;
    }
    public int getCurrentCopies() {
        return currentCopies;
    }

    public List<BookShelf> getBookShelfList() {
        return bookShelfList;
    }
    public void addBookShelf(BookShelf bookShelf) {

        if (bookShelf == null) {
            throw new IllegalArgumentException("BookShelf cannot be null");
        }

        if (bookShelf.getCopies() > getAvailableSpace()) {
            throw new IllegalArgumentException("Not enough space on the shelf");
        }

        bookShelfList.add(bookShelf);
        currentCopies += bookShelf.getCopies();
    }

    public void removeBookShelf(BookShelf bookShelf) {

        if (bookShelf == null) {
            throw new IllegalArgumentException("BookShelf cannot be null");
        }

        if (!bookShelfList.contains(bookShelf)) {
            throw new IllegalArgumentException("BookShelf does not exist on this shelf");
        }

        bookShelfList.remove(bookShelf);
        currentCopies -= bookShelf.getCopies();
    }

}
