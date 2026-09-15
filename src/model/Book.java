package model;

import java.time.Year;

public class Book {

    private String ISBN;
    private String title;
    private String author;
    private String publisher;
    private int publicationYear;
    private int totalCopies;
    private int availableCopies;
    private static final int MIN_YEAR = 1;
    public Book(String ISBN,String title,String author,String publisher,int publicationYear,int totalCopies,int availableCopies) {

        if (ISBN==null||ISBN.trim().isEmpty()){
            throw new IllegalArgumentException("ISBN cannot be empty");
        }
        if (title==null||title.trim().isEmpty()){
            throw new IllegalArgumentException("title cannot be empty");
        }
        if (author==null||author.trim().isEmpty()){
            throw new IllegalArgumentException("author cannot be empty");
        }
        if (publisher==null||publisher.trim().isEmpty()){
            throw new IllegalArgumentException("publisher cannot be empty");
        }
        if (publicationYear<MIN_YEAR || publicationYear> Year.now().getValue()){
            throw new IllegalArgumentException("Invalid publication year");
        }
        if (totalCopies<0){
            throw new IllegalArgumentException("totalCopies can't be negative");
        }
        if (availableCopies<0||availableCopies>totalCopies){
            throw new IllegalArgumentException("availableCopies must be between 0 and total copies");
        }
        if (!title.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Title must contain letters and spaces only");
        }

        this.ISBN=ISBN;
        this.title=title;
        this.author=author;
        this.publisher=publisher;
        this.publicationYear=publicationYear;
        this.totalCopies=totalCopies;
        this.availableCopies=availableCopies;

    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        if (ISBN==null||ISBN.trim().isEmpty()){
            throw new IllegalArgumentException("ISBN cannot be empty");
        }
        this.ISBN = ISBN;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
      if (title==null||title.trim().isEmpty()){
        throw new IllegalArgumentException("title cannot be empty");
      }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author==null||author.trim().isEmpty()){
            throw new IllegalArgumentException("author cannot be empty");
        }
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        if (publisher==null||publisher.trim().isEmpty()){
            throw new IllegalArgumentException("publisher cannot be empty");
        }
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        if (publicationYear<=0 || publicationYear> Year.now().getValue()){
            throw new IllegalArgumentException("Invalid publication year");
        }
        this.publicationYear = publicationYear;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        if (totalCopies < 0) {
            throw new IllegalArgumentException("totalCopies can't be negative");
        }

        if (totalCopies < availableCopies) {
            throw new IllegalArgumentException("totalCopies can't be less than available copies");
        }

        this.totalCopies = totalCopies;
    }
    public int getAvailableCopies() {
        return availableCopies;
    }
    public void setAvailableCopies(int availableCopies) {
        if (availableCopies < 0 || availableCopies > totalCopies) {
            throw new IllegalArgumentException("availableCopies must be between 0 and total copies");
        }

        this.availableCopies = availableCopies;
    }

    public boolean isAvailable(){
        return availableCopies>0;
    }

    public void addCopies(int count){
        if (count<=0){
            throw new IllegalArgumentException("Number of copies must be greater than 0");
        }
        availableCopies+=count;
        totalCopies+=count;
    }

    public void removeCopies(int count){
        if (count<=0){
            throw new IllegalArgumentException("Number of copies must be greater than 0");
        }
        if (count>availableCopies){
            throw new IllegalArgumentException("Cannot remove more copies than available copies");
        }
        availableCopies-=count;
        totalCopies-=count;
    }
}
