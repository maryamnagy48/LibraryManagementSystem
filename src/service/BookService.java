package service;

import dao.BookDAO;
import dao.BookShelfDAO;
import dao.BorrowingDetailsDAO;
import dao.ReadingSessionDetailsDAO;
import model.Book;

public class BookService {

    private BookDAO bookDAO;
    private BookShelfDAO bookShelfDAO;
    private BorrowingDetailsDAO borrowingDetailsDAO;
    private ReadingSessionDetailsDAO readingSessionDetailsDAO;


    public BookService(BookDAO bookDAO, BookShelfDAO bookShelfDAO, BorrowingDetailsDAO borrowingDetailsDAO, ReadingSessionDetailsDAO readingSessionDetailsDAO) {
        this.bookDAO = bookDAO;
        this.bookShelfDAO = bookShelfDAO;
        this.borrowingDetailsDAO = borrowingDetailsDAO;
        this.readingSessionDetailsDAO = readingSessionDetailsDAO;
    }


    public void addBook(Book book) {

        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        if (bookDAO.bookExists(book.getISBN())) {
            throw new IllegalArgumentException("A book with this ISBN already exists");
        }

        bookDAO.addBook(book);
    }

    public void updateBook(Book book) {

        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }

        if (!bookDAO.bookExists(book.getISBN())) {
            throw new IllegalArgumentException("Book does not exist");
        }
        Book currentBook = bookDAO.getBookByISBN(book.getISBN());

        int borrowedCopies = currentBook.getTotalCopies() - currentBook.getAvailableCopies();

        if (book.getTotalCopies() < borrowedCopies) {
            throw new IllegalArgumentException("Total copies cannot be less than borrowed copies");
        }

        int newAvailableCopies = book.getTotalCopies() - borrowedCopies;
        book.setAvailableCopies(newAvailableCopies);
        bookDAO.updateBook(book);
    }

    //Delete is only allowed for a book that has never been used or referenced anywhere in the system.
    public void deleteBook(String ISBN) {

        if (ISBN == null || ISBN.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }
        if (!bookDAO.bookExists(ISBN)) {
            throw new IllegalArgumentException("Book does not exist");
        }
        if (borrowingDetailsDAO.hasBook(ISBN)){
            throw new IllegalArgumentException("Cannot delete book because it has borrowing history");
        }
        if (readingSessionDetailsDAO.hasBook(ISBN)){
            throw new IllegalArgumentException("Cannot delete book because it has reading session history");
        }
        if (bookShelfDAO.hasBook(ISBN)){
            throw new IllegalArgumentException("Cannot delete book because it is assigned to a shelf");
        }


        bookDAO.deleteBook(ISBN);
    }

    public void getAllBooks() {
        bookDAO.getAllBooks();
    }

    public void searchByISBN(String ISBN) {
        if (ISBN == null || ISBN.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }
        bookDAO.searchByISBN(ISBN.trim());
    }

    public void searchByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        bookDAO.searchByTitle(title.trim());
    }

    public void searchByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        bookDAO.searchByAuthor(author.trim());
    }

    public boolean isBookAvailable(String ISBN) {
        return bookDAO.isBookAvailable(ISBN);
    }


}