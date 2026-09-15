package service;

import dao.BookcaseDAO;
import dao.CategoryDAO;
import model.Bookcase;


public class BookCaseService {
    private BookcaseDAO bookcaseDAO;
    private CategoryDAO categoryDAO;

    private static final int MIN_VALID_ID = 1;

    public BookCaseService(BookcaseDAO bookcaseDAO,CategoryDAO categoryDAO) {
        this.bookcaseDAO = bookcaseDAO;
        this.categoryDAO=categoryDAO;
    }

    public void addBookCase(Bookcase bookcase){
        if (bookcase==null){
            throw new IllegalArgumentException("Bookcase cannot be null");
        }

        if (!categoryDAO.categoryExists(bookcase.getCategoryID())){
            throw new IllegalArgumentException("Category does not exist");
        }

        bookcaseDAO.addBookCase(bookcase);
    }

    public void updateBookCase(Bookcase bookcase){
        if (bookcase==null){
            throw new IllegalArgumentException("Bookcase cannot be null");
        }
        if (!bookcaseDAO.bookcaseExists(bookcase.getBookcaseID())){
            throw new IllegalArgumentException("Bookcase does not exist");
        }

        //ممكن اكون بحاول اربطها بcategory مش موجودة
        if (!categoryDAO.categoryExists(bookcase.getCategoryID())){
            throw new IllegalArgumentException("Category does not exist");
        }

        bookcaseDAO.updateBookCase(bookcase);
    }

    public void deleteBookCase(int bookCaseID){
        if (bookCaseID<MIN_VALID_ID){
            throw new IllegalArgumentException("Bookcase ID must be greater than 0");
        }
        if (!bookcaseDAO.bookcaseExists(bookCaseID)){
            throw new IllegalArgumentException("Bookcase does not exist");
        }

        if (bookcaseDAO.hasShelves(bookCaseID)){
            throw new IllegalArgumentException("Cannot delete bookcase because it has shelves");
        }

        bookcaseDAO.deleteBookCase(bookCaseID);
    }

    public void getAllBookCases(){
        bookcaseDAO.getAllBookcases();
    }
}
