package model;

public class BookShelf {

    private int bookShelfID;
    private Book book;
    private Shelf shelf;
    private int copies;

    private static final int MIN_VALID_ID = 1;

    public BookShelf( Book book, Shelf shelf, int copies) {

        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        if (shelf == null) {
            throw new IllegalArgumentException("Shelf cannot be null");
        }

        if (copies <= 0) {
            throw new IllegalArgumentException("Copies must be greater than 0");
        }

        this.book = book;
        this.shelf = shelf;
        this.copies = copies;
    }

    public int getBookShelfID() {
        return bookShelfID;
    }

    public void setBookShelfID(int bookShelfID) {

        if (bookShelfID <MIN_VALID_ID) {
            throw new IllegalArgumentException("BookShelf ID must be greater than 0");
        }

        this.bookShelfID = bookShelfID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {

        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        this.book = book;
    }

    public Shelf getShelf() {
        return shelf;
    }

    public void setShelf(Shelf shelf) {

        if (shelf == null) {
            throw new IllegalArgumentException("Shelf cannot be null");
        }

        this.shelf = shelf;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {

        if (copies <= 0) {
            throw new IllegalArgumentException("Copies must be greater than 0");
        }

        this.copies = copies;
    }
}