package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Borrowing {
    private int borrowingID;
    private LocalDate borrowingDate;
    private LocalDate dueDate;
    private int memberID;
    private int librarianID;

    private List<BorrowingDetails> borrowingDetailsList;

    private static final int MIN_VALID_ID = 1;
    public Borrowing(LocalDate borrowingdate,LocalDate duedate,int memberID,int librarianID){
        if (borrowingdate==null){
            throw new IllegalArgumentException("Borrowing date cannot be empty");
        }
        if (duedate==null){
            throw new IllegalArgumentException("Due date cannot be empty");
        }
        if (duedate.isBefore(borrowingdate)){
            throw new IllegalArgumentException("Due date cannot be before borrowing date");
        }

        if (memberID < MIN_VALID_ID) {
            throw new IllegalArgumentException("Member ID must be greater than 0");
        }

        if (librarianID < MIN_VALID_ID) {
            throw new IllegalArgumentException("Librarian ID must be greater than 0");
        }

        this.borrowingDate =borrowingdate;
        this.dueDate =duedate;
        this.memberID = memberID;
        this.librarianID = librarianID;

        this.borrowingDetailsList=new ArrayList<>();
    }

    public void addBook(BorrowingDetails details){
        if (details==null){
            throw new IllegalArgumentException("BorrowingDetails cannot be empty");
        }
        borrowingDetailsList.add(details);
    }

    public void returnBook(BorrowingDetails details){
        if (details==null){
            throw new IllegalArgumentException("BorrowingDetails cannot be empty");
        }
        if(!borrowingDetailsList.contains(details)){
            throw new IllegalArgumentException("This book is not part of this borrowing");
        }
        if (details.isReturned()){
            throw new IllegalArgumentException("This book has already been returned");
        }

        details.setReturnDate(LocalDate.now());
    }

    public int getBorrowingID() {
        return borrowingID;
    }

    public void setBorrowingID(int borrowingID) {
        if (borrowingID<MIN_VALID_ID){
            throw new IllegalArgumentException("Borrowing ID must be greater than 0");
        }
        this.borrowingID = borrowingID;
    }

    public LocalDate getBorrowingDate() {
        return borrowingDate;
    }

    public void setBorrowingDate(LocalDate borrowingDate) {
        if (borrowingDate ==null){
            throw new IllegalArgumentException("Borrowing date cannot be empty");
        }
        if (dueDate !=null && dueDate.isBefore(borrowingDate)){
            throw new IllegalArgumentException("Due date cannot be before borrowing date");
        }

        this.borrowingDate = borrowingDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate ==null){
            throw new IllegalArgumentException("Due date cannot be empty");
        }
        if (borrowingDate !=null && dueDate.isBefore(borrowingDate)){
            throw new IllegalArgumentException("Due date cannot be before borrowing date");
        }
        this.dueDate = dueDate;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {

        if (memberID < MIN_VALID_ID) {
            throw new IllegalArgumentException("Member ID must be greater than 0");
        }
        this.memberID = memberID;
    }

    public int getLibrarianID() {
        return librarianID;
    }

    public void setLibrarianID(int librarianID) {

        if (librarianID < MIN_VALID_ID) {
            throw new IllegalArgumentException("Librarian ID must be greater than 0");
        }

        this.librarianID = librarianID;
    }
    public List<BorrowingDetails> getBorrowingDetailsList(){
        return borrowingDetailsList;
    }


}
