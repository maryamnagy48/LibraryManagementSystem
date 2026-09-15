package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BorrowingDetails {
    private int borrowingDetailsID;
    private LocalDate returnDate;
    private int borrowingID;
    private String ISBN;

    private static final int MIN_VALID_ID = 1;
    public BorrowingDetails(int borrowingID, String ISBN) {
        if (borrowingID <MIN_VALID_ID)
            throw new IllegalArgumentException("Borrowing ID must be greater than 0");

        if (ISBN == null || ISBN.trim().isEmpty())
            throw new IllegalArgumentException("ISBN cannot be empty");

        this.borrowingID = borrowingID;
        this.ISBN = ISBN;
        this.returnDate = null;
    }

    public boolean isReturned(){
        return returnDate!=null;
    }

    public boolean isOverdue(LocalDate dueDate){
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        return returnDate==null && LocalDate.now().isAfter(dueDate);
    }

    public int calculateOverdueDays(LocalDate dueDate){
        if (dueDate == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }

        if (!isOverdue(dueDate)){
            return 0;
        }

        return (int) ChronoUnit.DAYS.between( dueDate,LocalDate.now());
    }

    public int getBorrowingDetailsID() {
        return borrowingDetailsID;
    }

    public void setBorrowingDetailsID(int borrowingDetailsID) {
        if (borrowingDetailsID<MIN_VALID_ID){
            throw new IllegalArgumentException("Borrowing DetailsID must be greater than 0");
        }
        this.borrowingDetailsID = borrowingDetailsID;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public int getBorrowingID() {
        return borrowingID;
    }

    public void setBorrowingID(int borrowingID) {

        if (borrowingID < MIN_VALID_ID)
            throw new IllegalArgumentException("Borrowing ID must be greater than 0");

        this.borrowingID = borrowingID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {

        if (ISBN == null || ISBN.trim().isEmpty())
            throw new IllegalArgumentException("ISBN cannot be empty");

        this.ISBN = ISBN;
    }


}
