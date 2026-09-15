package service;

import dao.*;
import model.Borrowing;
import model.BorrowingDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class BorrowingService {
    private BorrowingDAO borrowingDAO;
    private BorrowingDetailsDAO borrowingDetailsDAO;
    private LibrarianDAO librarianDAO;
    private MemberDAO memberDAO;
    private BookDAO bookDAO;
    private Connection connection;

    private static final int MIN_VALID_ID = 1;

    public BorrowingService(BorrowingDAO borrowingDAO, BorrowingDetailsDAO borrowingDetailsDAO, LibrarianDAO librarianDAO,
                            MemberDAO memberDAO, BookDAO bookDAO, Connection connection) {
        this.borrowingDAO = borrowingDAO;
        this.borrowingDetailsDAO = borrowingDetailsDAO;
        this.librarianDAO = librarianDAO;
        this.memberDAO = memberDAO;
        this.bookDAO = bookDAO;
        this.connection = connection;
    }


    public void createBorrowing(Borrowing borrowing){
        if (borrowing==null){
            throw new IllegalArgumentException("Borrowing cannot be null");
        }
        if (!memberDAO.memberExists(borrowing.getMemberID())){
            throw new IllegalArgumentException("Member does not exist");
        }
        if (!librarianDAO.librarianExists(borrowing.getLibrarianID())){
            throw new IllegalArgumentException("Librarian does not exist");
        }

        borrowingDAO.addBorrowing(borrowing);
    }

    public void addBookToBorrowing(BorrowingDetails details){
        if (details==null){
            throw new IllegalArgumentException("BorrowingDetails cannot be null");
        }
        if (!borrowingDAO.borrowingExists(details.getBorrowingID())){
            throw new IllegalArgumentException("Borrowing does not exist");
        }
        if (!bookDAO.bookExists(details.getISBN())){
            throw new IllegalArgumentException("Book does not exist");
        }
        if (!bookDAO.isBookAvailable(details.getISBN())){
            throw new IllegalArgumentException("Book is not available");
        }

        try {
            connection.setAutoCommit(false);
            borrowingDetailsDAO.addBorrowingDetails(details);
            bookDAO.decreaseAvailableCopies(details.getISBN());
            connection.commit();

            System.out.println("Book added to borrowing successfully!");

        } catch (Exception e) {
            try {
                connection.rollback();

            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            throw new RuntimeException("Failed to add book to borrowing", e);
        }finally {
            try {connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void returnBook(BorrowingDetails details) {

        if (details == null) {
            throw new IllegalArgumentException("BorrowingDetails cannot be null");
        }

        BorrowingDetails currentDetails = borrowingDetailsDAO.getBorrowingDetailsByID(details.getBorrowingDetailsID());

        if (currentDetails == null) {
            throw new IllegalArgumentException("BorrowingDetails does not exist");
        }

        if (currentDetails.isReturned()) {
            throw new IllegalArgumentException("This book has already been returned");
        }

        if (!bookDAO.bookExists(currentDetails.getISBN())) {
            throw new IllegalArgumentException("Book does not exist");
        }

        try {
            connection.setAutoCommit(false);

            LocalDate returnDate = LocalDate.now();

            borrowingDetailsDAO.updateReturnDate(currentDetails.getBorrowingDetailsID(), returnDate);

            bookDAO.increaseAvailableCopies(currentDetails.getISBN());

            connection.commit();

            // Update the object passed by the caller
            details.setReturnDate(returnDate);

            System.out.println("Book returned successfully!");

        } catch (Exception e) {

            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }

            throw new RuntimeException("Failed to return book", e);

        } finally {

            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void getAllBorrowings(){
        borrowingDAO.getAllBorrowings();
    }

    //current borrowing : لسة فى كتاب واحد على الاقل مرجعش
    public void getCurrentBorrowings() {
        borrowingDAO.getCurrentBorrowings();
    }

    //lma duedate y3dy w elktab lsa mrg3sh
    public void getOverdueBorrowings() {
        borrowingDAO.getOverdueBorrowings();
    }

    public boolean isOverdue(BorrowingDetails details){
        if (details==null){
            throw new IllegalArgumentException( "BorrowingDetails cannot be null");
        }
        if (!borrowingDetailsDAO.borrowingDetailsExists(details.getBorrowingDetailsID())){
            throw new IllegalArgumentException("BorrowingDetails does not exist");
        }

        if (details.isReturned()){
            return false;
        }

        Borrowing borrowing=borrowingDAO.getBorrowingByID(details.getBorrowingID());
        if (borrowing==null){
            throw new IllegalArgumentException("Borrowing does not exist");
        }
        return details.isOverdue(borrowing.getDueDate());
    }

    public int calculateOverdueDays(BorrowingDetails details) {
        if (details==null){
            throw new IllegalArgumentException( "BorrowingDetails cannot be null");
        }
        if (!borrowingDetailsDAO.borrowingDetailsExists(details.getBorrowingDetailsID())){
            throw new IllegalArgumentException("BorrowingDetails does not exist");
        }
        Borrowing borrowing=borrowingDAO.getBorrowingByID(details.getBorrowingID());
        if (borrowing==null){
            throw new IllegalArgumentException("Borrowing does not exist");
        }
        return details.calculateOverdueDays(borrowing.getDueDate());

    }

    public void deleteBorrowing(int borrowingID) {

        if (borrowingID <MIN_VALID_ID) {
            throw new IllegalArgumentException("Borrowing ID must be greater than 0");
        }

        if (!borrowingDAO.borrowingExists(borrowingID)) {
            throw new IllegalArgumentException("Borrowing does not exist");
        }

        if (borrowingDetailsDAO.hasBorrowing(borrowingID)) {
            throw new IllegalArgumentException("Cannot delete borrowing because it has borrowing details");
        }

        borrowingDAO.deleteBorrowing(borrowingID);
    }
}
